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
            teams: [],
            allCoaches: [],
            coachList: [],
            tempCoachDataStorage: []
        };
        this.customCoachData = this.customCoachData.bind(this);
        this.headcoachSort = this.headcoachSort.bind(this);
    }

    headcoachSort(event){
        let sortBy = event.target.value;
        console.log(event.target.value);
        if("Alphabetical" === sortBy){
            const sortedCoaches = [].concat(this.state.tempCoachDataStorage).sort((a, b) => a.value.last_name > b.value.last_name ? 1 : -1);
            this.setState({ tempCoachDataStorage: sortedCoaches });
        }else if("Win %" === sortBy){
            const sortedCoaches = [].concat(this.state.tempCoachDataStorage).sort((a, b) => a.value.winPerc < b.value.winPerc ? 1 : -1);
            this.setState({ tempCoachDataStorage: sortedCoaches });
        }else if("Most Wins" === sortBy){
            const sortedCoaches = [].concat(this.state.tempCoachDataStorage).sort((a, b) => parseInt(a.value.wins,10) < parseInt(b.value.wins,10) ? 1 : -1);
            this.setState({ tempCoachDataStorage: sortedCoaches });
        }else if("Coach Grade" === sortBy){
         
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    customCoachData() {
        var coachFinal = [];
        //for (var i = 0; i < 10 && i < this.state.allCoaches.length; i++) {
        for (var i = 0; i < this.state.allCoaches.length; i++) {
            axios.get('http://localhost:8080/api/coaches/record/byName/' + this.state.allCoaches[i].first_name + '-' + this.state.allCoaches[i].last_name)
                .then(result => {
                    // Create a new array based on current state:
                    var tempCoaches = this.state.tempCoachDataStorage;
                    // Get win percentage of the coach
                    var editedCoach = result.data;
                    editedCoach.winPerc = 100 * result.data.wins / (result.data.losses + result.data.wins + result.data.ties);
                    // Add item to it
                    tempCoaches.push({ value: editedCoach });

                    // Set state
                    this.setState({ tempCoachDataStorage: tempCoaches });
                })
        }
        console.log(this.state.tempCoachDataStorage[0]);
    }

    componentDidMount() {
        
        axios.get('http://localhost:8080/api/coaches/all')
            .then(res => {
                this.setState({ allCoaches: res.data });
                this.customCoachData();
            });
    }

    render() {
        const { classes } = this.props;
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
                        this.state.tempCoachDataStorage.length > 0 ?
                            this.state.tempCoachDataStorage.map((coach, ndx) => {
                                return (
                                    <Grid item xs={3} key={ndx}>
                                            <StyledPaper classes={classes}>
                                                {/* <Avatar className={classes.logo} src={team.logos[0]}/> */}
                                                <Typography>#{ndx+1}</Typography>
                                                <Typography><b>{coach.value.first_name} {coach.value.last_name}</b></Typography>
                                                <Typography><b>Wins:</b> {coach.value.wins} <b>Losses:</b> {coach.value.losses}</Typography>
                                            </StyledPaper>
                                    </Grid>
                                );
                            })
                            :
                            <Typography>
                               Please wait while we load the coach data.
                            </Typography>
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(withRouter(Ranking));

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