import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import withStyles from "@material-ui/core/styles/withStyles";
import {Avatar} from "@material-ui/core";

const styles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    list: {
        marginTop: theme.spacing(2),
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        width: 80,
        height: 80,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    logo: {
        marginTop: theme.spacing(2),
        paddingTop: theme.spacing(2)

    }
}));

class SchoolList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            teams: []
        };
    }

    componentDidMount() {
        // Get List Of FBS Teams From API
        axios.get('http://localhost:8080/api/teams/fbs')
        .then(res => {
            this.setState({ teams: res.data });
        });
    }

    render() {
        const { classes } = this.props;

        return (
            <div className={classes.root} >
                <br/>
                <Grid container align="center" justify="center" alignItems="center" spacing={3} className={classes.list}>
                    {
                        this.state.teams.length > 0 ?
                            this.state.teams.map((team, ndx) => {
                                return (
                                    <Grid item xs={3}>
                                        <Link
                                            to={{
                                                pathname: '/school/' + team.school,
                                                state: {
                                                    teamId: team.id
                                                }
                                            }}
                                            style={{ color: this.state.primaryColor }}
                                        >
                                            <StyledPaper classes={classes}>
                                                <Avatar className={classes.logo} src={team.logos[0]}/>
                                                <Typography>{team.school}</Typography>
                                            </StyledPaper>
                                        </Link>
                                    </Grid>
                                );
                            })
                        :
                            <Typography>
                                No teams are available at this time. Please check in later.
                            </Typography>
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(SchoolList);

class StyledPaper extends Component {
    constructor(props){
        super(props);
        this.state = {
            elevation: 1
        };

        this.onMouseOver = this.onMouseOver.bind(this);
        this.onMouseOut = this.onMouseOut.bind(this);
    }

    onMouseOver(){
        this.setState({ elevation: 5 });
    }

    onMouseOut(){
        this.setState({ elevation: 1 });
    }

    render() {
        return (
            <Paper
                onMouseOver={this.onMouseOver}
                onMouseOut={this.onMouseOut}
                elevation={this.state.elevation}
                square={true}
                className={this.props.classes.paper}
            >
                {this.props.children}
            </Paper>
        );
    }
}