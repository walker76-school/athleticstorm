import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import '../../common/AppHeader.css';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import {Avatar} from "@material-ui/core";
import LoadingIndicator from "../../common/LoadingIndicator";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";

const styles = makeStyles(theme => ({
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    }
}));

class SchoolList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            teams: [],
            allTeams: [],
            loaded: false
        };
        this.filterTeams = this.filterTeams.bind(this);
    }

    componentDidMount() {
        // Get List Of FBS Teams From API
        axios.get('http://localhost:8080/api/teams/fbs')
        .then(res => {
            this.setState({
                teams: res.data,
                allTeams: res.data,
                loaded: true
            });
        })
        .catch(error => {
            this.setState({
                loaded: true
            });
        });
    }

    filterTeams(filter){
        var tempTeams = [];
        for( var x = 0; x < this.state.allTeams.length; x++){
            if(this.state.allTeams[x].school.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
             tempTeams.push(this.state.allTeams[x]);
            }
        }
        this.setState({ teams: tempTeams });
    }

    render() {

        if(!this.state.loaded){
            return <LoadingIndicator/>
        }

        return (
            <div style={{flexGrow: 1}} >
                <br/>
                <input type="text" placeholder="Search" onChange={(event) => {this.filterTeams(event.target.value)}}/>
                <br/>
                <br/>
                <Grid container align="center" justify="center" alignItems="center" spacing={3} >
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
                                            <StyledPaper classes={this.props.classes}>
                                                <Avatar src={team.logos[0]}/>
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

        const {classes} = this.props;

        return (
            <Paper
                onMouseOver={this.onMouseOver}
                onMouseOut={this.onMouseOut}
                elevation={this.state.elevation}
                square={true}
                className={classes.paper}
            >
                {this.props.children}
            </Paper>
        );
    }
}