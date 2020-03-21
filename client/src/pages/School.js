import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import '../common/AppHeader.css';
import logo from './football.jpeg'
import axios from 'axios';
import { Link } from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";

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

class School extends Component {

    constructor(props){
        super(props);
        this.state = {
            teamId: this.props.location.state.teamId,
            coaches: [],
            players: [],
            schoolName: '',
            logo: '',
            primaryColor: '',
            year: '2019'
        };

        this.loadCoaches = this.loadCoaches.bind(this);
        this.loadPlayers = this.loadPlayers.bind(this);
        this.coachSort = this.coachSort.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/teams/' + this.props.location.state.teamId)
        .then(result => {
            console.log(result);
            this.setState({
                teamId: result.data.id,
                schoolName: result.data.school,
                logo: result.data.logos[0],
                primaryColor: result.data.color,
                secondaryColor: result.data.alt_color
            }, () => {
                this.loadCoaches();
                this.loadPlayers(this.state.year);
            });
        });
    }

    loadCoaches(){
        // Get List Of Coaches From API
        axios.get('http://localhost:8080/api/coaches/byTeamId/' + this.state.teamId)
        .then(result => {
            console.log(result);
            this.setState({
                coaches: result.data
            });
        });
    }

    loadPlayers(newYear){
        this.setState({
            year: newYear
        }, () => {
            // Get List Of Players From API
            axios.get('http://localhost:8080/api/roster/' + this.state.teamId + '/' + this.state.year)
            .then(result => {
                this.setState({
                    players: result.data
                });
            })
        });
    }

    coachSort(event){
        let sortBy = event.target.value;
        console.log(event.target.value);
        if("Descending" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name > b.last_name ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Ascending" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name  < b.last_name ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Most Recent" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10)  < parseInt(b.seasons[0].year, 10) ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Oldest" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10) > parseInt(b.seasons[0].year, 10) ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Best Score" === sortBy){

        }else if("Worst Score" === sortBy){

        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    render() {

        const {classes} = this.props;

        return (
            <div>
                <br/>
                <div>
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }} >&nbsp;{this.state.schoolName}</h1>
                    <img src={this.state.logo} width="100" height="100" alt="Logo" />
                </div>

                <div>
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Coaches
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={this.coachSort}>
                            <option value="Ascending">Ascending</option>
                            <option value="Descending">Descending</option>
                            <option value="Most Recent">Most Recent</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Best Score">Best Score</option>
                            <option value="Worst Score">Worst Score</option>
                        </select>
                    </h1>
                    <Grid container align="center" justify="left" spacing={3} className={classes.list}>
                        {
                            this.state.coaches.map((coach, ndx) => {
                                return (
                                    <Grid item xs={3}>
                                        <Link
                                            to={{
                                                pathname: "/coach/" + coach.first_name + " " + coach.last_name,
                                                state: {
                                                    first_name: coach.first_name,
                                                    last_name: coach.last_name,
                                                }
                                            }}
                                            style={{ color: this.state.primaryColor }}
                                        >
                                            <StyledPaper classes={classes}>
                                                <Avatar className={classes.logo} src={logo}/>
                                                <Typography>
                                                    {coach.first_name + " " + coach.last_name} <br/>
                                                    {coach.seasons.length === 1 ? coach.seasons[0].year : coach.seasons[0].year + "-" + coach.seasons[coach.seasons.length - 1].year}
                                                </Typography>
                                            </StyledPaper>
                                        </Link>
                                    </Grid>
                                );
                            })
                        }
                    </Grid>
                </div>
                <br/>
                <div>
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Players
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={(event) => {this.loadPlayers(event.target.value)}}>
                            <option value="2019">2019</option>
                            <option value="2018">2018</option>
                            <option value="2017">2017</option>
                            <option value="2016">2016</option>
                        </select>
                    </h1>
                </div>
                <Grid container align="center" justify="left" spacing={3} className={classes.list}>
                    {
                        this.state.players.map((player, ndx) => {
                            return (
                                <Grid item xs={3}>
                                    <Link
                                        to={{
                                            pathname: `/player/${player.first_name} ${player.last_name}`,
                                            state: {
                                                teamdId: this.state.teamId,
                                                playerId: player.id,
                                                first_name: player.first_name,
                                                last_name: player.last_name,
                                                year: this.state.year,
                                            }
                                        }}
                                        style={{ color: this.state.primaryColor }} >
                                        <StyledPaper classes={classes}>
                                            <Avatar className={classes.logo} src={logo}/>
                                            <Typography>
                                                {player.first_name + " " + player.last_name} <br/>
                                                {player.position ? player.position + " " + this.state.year : this.state.year}
                                            </Typography>
                                        </StyledPaper>
                                    </Link>
                                </Grid>
                            );
                        })
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(withRouter(School));

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