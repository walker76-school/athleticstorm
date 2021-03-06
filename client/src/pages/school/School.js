/*
*   Filename: API.js
*   Author: Joshua Pane
*   Date Last Modified: 4/26/2019
*/

import React, { Component } from 'react';
import '../../common/AppHeader.css';
import logo from './football.jpeg'
import {Link, withRouter} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import LoadingIndicator from "../../common/LoadingIndicator";
import Cookies from 'universal-cookie';
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";
import {notification} from 'antd';
import Redirect from "react-router-dom/Redirect";
import YouTube from 'react-youtube';
import {getCoachesByTeamId, getCoordinatorsByTeamId, getRoster, getTeamByName, getVideosByTeamName} from "./API";
import PlayerPopup from "../player/PlayerPopup";
import CoordinatorPopup from "../coordinator/CoordinatorPopup";

//Formatting for schools
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

let unlocked = true;

const cookies = new Cookies();

class School extends Component {
    //Declare needed state variables
    constructor(props){
        super(props);
        this.state = {
            teamId: -1,
            coaches: [],
            allCoaches: [],
            players: [],
            allPlayers: [],
            OC: [],
            allOC: [],
            DC: [],
            allDC: [],
            videos: [],
            schoolName: '',
            logo: '',
            primaryColor: '',
            year: '2019',
            selectedPlayer: null,
            selectedCoordinator: null,
            loadedCoaches: false,
            loadedPlayers: false,
            loadedCoordinators: false,
            loadedVideos: false
        };

        this.loadCoaches = this.loadCoaches.bind(this);
        this.loadCoordinators = this.loadCoordinators.bind(this);
        this.loadPlayers = this.loadPlayers.bind(this);
        this.loadVideos = this.loadVideos.bind(this);
        this.headCoachSort = this.headCoachSort.bind(this);
        this.OCSort = this.OCSort.bind(this);
        this.DCSort = this.DCSort.bind(this);
        this.onModalClose = this.onModalClose.bind(this);
        this.setSelectedPlayer = this.setSelectedPlayer.bind(this);
    }

    componentDidMount() {
        unlocked = true;

        getTeamByName(this.props.match.params.schoolName)
        .then(result => {
            this.setState({
                teamId: result.id,
                schoolName: result.school,
                logo: result.logos[0],
                primaryColor: result.color,
                secondaryColor: result.alt_color
            }, () => {
                this.loadCoaches();
                this.loadVideos();
                this.loadCoordinators();
                this.loadPlayers(this.state.year);
            });
            // Add team to teams_visited if it's not already there and decrease teams available to visit.
            // If team has already been visited by user, no change is necessary
            if(!cookies.get('Teams_visited').find(element => element === result.school)) {
                if(cookies.get('Num_teams') > 0) {
                    cookies.set('Num_teams', cookies.get('Num_teams') - 1,{path: '/'});
                    let teamsVisited = cookies.get('Teams_visited');
                    teamsVisited.push(result.school);
                    cookies.set('Teams_visited', teamsVisited, {path: '/'});

                    if(cookies.get('Role') !== 'ROLE_MVP'){
                        // Show notification for how many teams remain in subscription package
                        notification.info({
                            message: 'Athletic Storm',
                            description: 'Your subscription allows you to view ' + cookies.get('Num_teams') + ' more teams.'
                        });
                    }

                } else{
                    unlocked = false;
                }
            }
        });
    }

    loadVideos(){
        // Get List Of Coaches From API
        getVideosByTeamName(this.state.schoolName)
        .then(result => {
            console.log(result);
            this.setState({
                videos: result,
                loadedVideos: true
            });
        })
        .catch(error => {
            this.setState({
                loadedVideos: true
            });
        });
    }

    loadCoaches(){
        // Get List Of Coaches From API
        getCoachesByTeamId(this.state.teamId)
        .then(result => {
            console.log(result);
            this.setState({
                coaches: result,
                allCoaches: result,
                loadedCoaches: true
            }, () => {
                this.headCoachSort('Most Recent');
            });
        });
    }

    loadCoordinators(){
        // Get List Of Coaches From API
        getCoordinatorsByTeamId(this.state.teamId)
        .then(result => {
            let oc = result.filter(x => x.position === "OC");
            let dc = result.filter(x => x.position === "DC");
            this.setState({
                OC: oc,
                allOC: oc,
                DC: dc,
                allDC: dc,
                loadedCoordinators: true
            }, () => {
                this.OCSort('Most Recent');
                this.DCSort('Most Recent');
            });
        });
    }

    loadPlayers(newYear){
        if(cookies.get('Role') !== 'ROLE_REDSHIRT' || newYear === this.state.year) {
            this.setState({
                year: newYear
            }, () => {
                // Get List Of Players From API
                getRoster(this.state.teamId, this.state.year)
                .then(result => {
                    console.log(result);
                    let tempPlayers = [];
                    for (var x = 0; x < result.length; x++) {
                        if (result[x].first_name !== null && result[x].last_name !== null && result[x].position !== null) {
                            tempPlayers.push(result[x]);
                        }
                    }
                    this.setState({
                        players: tempPlayers,
                        allPlayers: tempPlayers,
                        loadedPlayers: true
                    });
                })
                .catch(error => {
                    notification.error({
                        message: 'Athletic Storm',
                        description: 'Error fetching players.'
                    });
                })
            });
        } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to access other years.'
                });
            }
    }

    onModalClose(){
        this.setState({
            selectedPlayer: null,
            selectedCoordinator: null,
        });
    }

    setSelectedPlayer(player){
        this.setState({
            selectedPlayer: player
        })
    }

    setSelectedCoordinator(coordinator){
        console.log("setting ", coordinator);
        this.setState({
            selectedCoordinator: coordinator
        })
    }

    //Sorting function for coaches
    headCoachSort(sortBy){
        if("Descending" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name < b.last_name ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Ascending" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name  > b.last_name ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Most Recent" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10)  < parseInt(b.seasons[0].year, 10) ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Oldest" === sortBy){
            const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10) > parseInt(b.seasons[0].year, 10) ? 1 : -1);
            this.setState({ coaches: sortedCoaches });
        }else if("Best Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.rating < b.rating ? 1 : -1);
                this.setState({ coaches: sortedCoaches });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }

        }else if("Worst Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.rating > b.rating ? 1 : -1 ? 1 : -1);
                this.setState({ coaches: sortedCoaches });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }

        }else{
            console.log("Invalid Option " + sortBy);
        }
    }
    //Sorting algorithms for Offensive Coordinators
    OCSort(sortBy){
        if("Descending" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.name < b.name ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Ascending" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.name > b.name ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Most Recent" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => parseInt(a.startYear, 10) < parseInt(b.startYear, 10) ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Oldest" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => parseInt(a.startYear, 10) > parseInt(b.startYear, 10) ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Best Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedOC = [].concat(this.state.OC).sort((a, b) => a.rating < b.rating ? 1 : -1);
                this.setState({ OC: sortedOC });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }
        }else if("Worst Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedOC = [].concat(this.state.OC).sort((a, b) => a.rating > b.rating ? 1 : -1);
                this.setState({ OC: sortedOC });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    //Defensive coordinator sorting algorithms
    DCSort(sortBy){
        if("Descending" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.name < b.name ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Ascending" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.name > b.name ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Most Recent" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => parseInt(a.startYear, 10) < parseInt(b.startYear, 10) ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Oldest" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => parseInt(a.startYear, 10) > parseInt(b.startYear, 10) ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Best Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedDC = [].concat(this.state.DC).sort((a, b) => a.rating < b.rating ? 1 : -1);
                this.setState({ DC: sortedDC });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }

        }else if("Worst Score" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedDC = [].concat(this.state.DC).sort((a, b) => a.rating > b.rating ? 1 : -1);
                this.setState({ DC: sortedDC });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    //Search bar functionality
    filter(filter){
        let tempCoaches = [];
        for( let x = 0; x < this.state.allCoaches.length; x++){
            if(this.state.allCoaches[x].first_name.toLocaleLowerCase().includes(filter.toLocaleLowerCase()) || this.state.allCoaches[x].last_name.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
                tempCoaches.push(this.state.allCoaches[x]);
            }
        }

        let tempOC = [];
        for( let y = 0; y < this.state.allOC.length; y++){
            if(this.state.allOC[y].name.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
                tempOC.push(this.state.allOC[y]);
            }
        }

        let tempDC = [];
        for( let z = 0; z < this.state.allDC.length; z++){
            if(this.state.allDC[z].name.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
                tempDC.push(this.state.allDC[z]);
            }
        }

        let tempPlayers = [];
        for( let i = 0; i < this.state.allPlayers.length; i++){
            if(this.state.allPlayers[i].first_name.toLocaleLowerCase().includes(filter.toLocaleLowerCase()) ||
                this.state.allPlayers[i].last_name.toLocaleLowerCase().includes(filter.toLocaleLowerCase()) ||
                this.state.allPlayers[i].position.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
                tempPlayers.push(this.state.allPlayers[i]);
            }
        }

        this.setState({
            coaches: tempCoaches,
            OC: tempOC,
            DC: tempDC,
            players: tempPlayers
        });
    }

    render() {

        if(!this.state.loadedCoaches || !this.state.loadedPlayers || !this.state.loadedCoordinators || !this.state.loadedVideos){
            return <LoadingIndicator/>;
        }

        return (
            <div>
                {unlocked &&
                <div>
                    <br/>
                    <input type="text" placeholder="Search" onChange={(event) => {
                        this.filter(event.target.value)
                    }}/>
                    <br/>
                    <br/>
                    <div>
                        <h1 style={{
                            backgroundColor: this.state.primaryColor,
                            color: "#ffffff"
                        }}>&nbsp;{this.state.schoolName}</h1>
                    </div>
                    <Grid container>
                        <Grid item md={2} xs={6}>
                            <img src={this.state.logo} width="100" height="100" alt="Logo"/>
                        </Grid>
                        {
                            this.state.videos.map(videoId => {
                                return (
                                    <Grid item md={3} xs={6}>
                                        <YouTube videoId={videoId} opts={{
                                            height: "125px",
                                            width: "200px"
                                        }}/>
                                    </Grid>
                                );
                            })
                        }
                    </Grid>
                    <br/>

                    <div>
                        <h1 style={{backgroundColor: this.state.primaryColor, color: "#ffffff"}}>&nbsp;Head Coaches
                            <select style={{float: 'right', color: this.state.primaryColor}} onChange={(event) => {
                                this.headCoachSort(event.target.value)
                            }}>
                                <option value="Most Recent">Most Recent</option>
                                <option value="Oldest">Oldest</option>
                                <option value="Best Score">Best Score</option>
                                <option value="Worst Score">Worst Score</option>
                                <option value="Ascending">Alphabetical (A-Z)</option>
                                <option value="Descending">Alphabetical (Z-A)</option>
                            </select>
                        </h1>
                        <Grid container align="center" spacing={3}>
                            {
                                this.state.coaches.map((coach, ndx) => {

                                    let validSeasons = [];
                                    for (let i = 0; i < coach.seasons.length; i++) {
                                        if (coach.seasons[i].school === this.state.schoolName) {
                                            validSeasons.push(coach.seasons[i]);
                                        }
                                    }

                                    let yearRange = "";
                                    if (validSeasons.length === 1) {
                                        yearRange = validSeasons[0].year;
                                    } else {
                                        let minYear = validSeasons[0].year;
                                        let maxYear = validSeasons[0].year;
                                        for (let i = 0; i < validSeasons.length; i++) {
                                            if (validSeasons[i].school === this.state.schoolName && validSeasons[i].year < minYear) {
                                                minYear = validSeasons[i].year;
                                            } else if (validSeasons[i].school === this.state.schoolName && validSeasons[i].year > maxYear) {
                                                maxYear = validSeasons[i].year;
                                            }
                                        }
                                        yearRange = minYear + "-" + maxYear;
                                    }
                                    return (
                                        <Grid item md={3} xs={12} key={ndx}>
                                            <Link
                                                to={"/coach/" + coach.first_name + " " + coach.last_name}
                                                style={{color: this.state.primaryColor}}
                                            >
                                                <StyledPaper classes={this.props.classes}>
                                                    <Avatar src={logo}/>
                                                    <Typography>
                                                        {coach.first_name + " " + coach.last_name} <br/>
                                                        {yearRange}
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
                        <h1 style={{backgroundColor: this.state.primaryColor, color: "#ffffff"}}>&nbsp;Offensive
                            Coordinators
                            <select style={{float: 'right', color: this.state.primaryColor}} onChange={(event) => {
                                this.OCSort(event.target.value)
                            }}>
                                <option value="Most Recent">Most Recent</option>
                                <option value="Oldest">Oldest</option>
                                <option value="Best Score">Best Score</option>
                                <option value="Worst Score">Worst Score</option>
                                <option value="Ascending">Alphabetical (A-Z)</option>
                                <option value="Descending">Alphabetical (Z-A)</option>
                            </select>
                        </h1>
                        <Grid container align="center" spacing={3}>
                            {
                                this.state.OC.map((oc, ndx) => {
                                    return (
                                        <Grid item md={3} xs={12} key={ndx}>
                                            <a style={{color: this.state.primaryColor}}
                                               onClick={() => {
                                                   this.setSelectedCoordinator({
                                                       name: oc.name,
                                                       teamId: this.state.teamId
                                                   });
                                               }}>
                                                <StyledPaper classes={this.props.classes}>
                                                    <Avatar src={logo}/>
                                                    <Typography>
                                                        {oc.name} <br/>
                                                        {oc.startYear === oc.endYear ? oc.startYear : oc.startYear + "-" + oc.endYear}
                                                    </Typography>
                                                </StyledPaper>
                                            </a>
                                        </Grid>
                                    );
                                })
                            }
                        </Grid>
                    </div>
                    <br/>
                    <div>
                        <h1 style={{backgroundColor: this.state.primaryColor, color: "#ffffff"}}>&nbsp;Defensive
                            Coordinators
                            <select style={{float: 'right', color: this.state.primaryColor}} onChange={(event) => {
                                this.DCSort(event.target.value)
                            }}>
                                <option value="Most Recent">Most Recent</option>
                                <option value="Oldest">Oldest</option>
                                <option value="Best Score">Best Score</option>
                                <option value="Worst Score">Worst Score</option>
                                <option value="Ascending">Alphabetical (A-Z)</option>
                                <option value="Descending">Alphabetical (Z-A)</option>
                            </select>
                        </h1>
                        <Grid container align="center" spacing={3}>
                            {
                                this.state.DC.map((dc, ndx) => {
                                    return (
                                        <Grid item md={3} xs={12} key={ndx}>
                                            <a style={{color: this.state.primaryColor}}
                                               onClick={() => {
                                                   this.setSelectedCoordinator({
                                                       name: dc.name,
                                                       teamId: this.state.teamId
                                                   });
                                               }}>
                                                <StyledPaper classes={this.props.classes}>
                                                    <Avatar src={logo}/>
                                                    <Typography>
                                                        {dc.name} <br/>
                                                        {dc.startYear === dc.endYear ? dc.startYear : dc.startYear + "-" + dc.endYear}
                                                    </Typography>
                                                </StyledPaper>
                                            </a>
                                        </Grid>
                                    );
                                })
                            }
                        </Grid>
                    </div>
                    <br/>
                    <div>
                        <h1 style={{backgroundColor: this.state.primaryColor, color: "#ffffff"}}>&nbsp;Players
                            <select style={{float: 'right', color: this.state.primaryColor}} onChange={(event) => {
                                this.loadPlayers(event.target.value)
                            }}>
                                <option value="2019">2019</option>
                                <option value="2018">2018</option>
                                <option value="2017">2017</option>
                            </select>
                        </h1>
                    </div>
                    <Grid container align="center" spacing={3}>
                        {
                            this.state.players.slice(0, cookies.get('Num_players')).map((player, ndx) => {
                                return (
                                    <Grid item md={3} xs={12} key={ndx}>
                                        <a
                                            onClick={() => {
                                                this.setSelectedPlayer({
                                                    team: this.state.schoolName,
                                                    playerId: player.id,
                                                    first_name: player.first_name,
                                                    last_name: player.last_name,
                                                    year: this.state.year,
                                                })
                                            }}
                                            style={{color: this.state.primaryColor}}>
                                            <StyledPaper classes={this.props.classes}>
                                                <Avatar src={logo}/>
                                                <Typography>
                                                    {player.first_name + " " + player.last_name} <br/>
                                                    {player.position ? player.position + " " + this.state.year : this.state.year}
                                                </Typography>
                                            </StyledPaper>
                                        </a>
                                    </Grid>
                                );
                            })
                        }
                    </Grid>
                    <PlayerPopup handleClose={this.onModalClose} open={this.state.selectedPlayer !== null} selectedPlayer={this.state.selectedPlayer}/>
                    <CoordinatorPopup handleClose={this.onModalClose} open={this.state.selectedCoordinator !== null} selectedCoordinator={this.state.selectedCoordinator}/>
                </div>
                }
                {!unlocked && <Redirect to={{pathname: "/error", state: {sub: false} }} />}
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