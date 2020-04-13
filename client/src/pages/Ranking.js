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
import { Avatar } from "@material-ui/core";
import LoadingIndicator from "../common/LoadingIndicator";

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
    setFontSizeThree: {
        fontSize: 25 
    },
    bold:{
        fontWeight: 'fontWeightBold'
    },
    logo: {
        marginTop: theme.spacing(2),
        paddingTop: theme.spacing(2)

    }
}));

class Ranking extends Component {

    constructor(props) {
        super(props);
        this.state = {
            allRecords: [],
            loaded: false
        };
        this.headcoachSort = this.headcoachSort.bind(this);
    }

    headcoachSort(event){
        let sortBy = event.target.value;
        console.log(event.target.value);
        if("Alphabetical" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => a.lastName > b.lastName ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Win %" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => a.winPercentage < b.winPercentage ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Most Wins" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => parseInt(a.wins,10) < parseInt(b.wins,10) ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Coach Grade" === sortBy){
         
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    componentDidMount() {
        
        axios.get('http://localhost:8080/api/coaches/allStats')
        .then(res => {
            this.setState({
                allRecords: res.data,
                loaded: true
            });
        })
        .catch(error => {
            this.setState({
                allRecords: [],
                loaded: true
            });
        });
    }

    render() {
        const { classes } = this.props;

        if(!this.state.loaded){
            return <LoadingIndicator/>
        }

        return (
            <div className={classes.root} >
                <br />
                <h1 style={{ backgroundColor: "#3773B0", color: "#ffffff" }}>&nbsp;Rankings
                        <select style={{ float: 'right', color: "#3773B0" }} onChange={this.headcoachSort}>
                            <option value="Alphabetical">Alphabetical</option>
                            <option value="Win %">Win %</option>
                            <option value="Most Wins">Most Wins</option>
                            <option value="Coach Grade">Coach Grade</option>
                        </select>
                    </h1>
                <Grid container align="center" justify="center" alignItems="center" spacing={3} className={classes.list}>
                    {
                        this.state.allRecords.length > 0 ?
                            this.state.allRecords.map((record, ndx) => {
                                return (
                                    <Grid item xs={3} key={ndx}>
                                            <StyledPaper classes={classes}>
                                                {/* <Avatar className={classes.logo} src={team.logos[0]}/> */}
                                                <Typography>#{ndx+1}</Typography>
                                                <Typography><b>{record.firstName} {record.lastName}</b></Typography>
                                                <Typography><b>Wins:</b> {record.wins} <b>Losses:</b> {record.losses}</Typography>
                                            </StyledPaper>
                                    </Grid>
                                );
                            })
                            :
                            <Typography>
                               No coaches loaded.
                            </Typography>
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(Ranking);

class StyledPaper extends Component {
    constructor(props) {
        super(props);
        this.state = {
            elevation: 1
        };

        this.onMouseOver = this.onMouseOver.bind(this);
        this.onMouseOut = this.onMouseOut.bind(this);
    }

    onMouseOver() {
        this.setState({ elevation: 5 });
    }

    onMouseOut() {
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