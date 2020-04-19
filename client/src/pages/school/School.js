import React, { Component } from 'react';
import '../../common/AppHeader.css';
import logo from './football.jpeg'
import axios from 'axios';
import {Link, withRouter} from "react-router-dom";
import Grid from "@material-ui/core/Grid";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Popup from "../player/Popup";
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

class School extends Component {

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
            schoolName: '',
            logo: '',
            primaryColor: '',
            year: '2019',
            selectedPlayer: null,
            loadedCoaches: false,
            loadedPlayers: false,
            loadedCoordinators: false
        };

        this.loadCoaches = this.loadCoaches.bind(this);
        this.loadCoordinators = this.loadCoordinators.bind(this);
        this.loadPlayers = this.loadPlayers.bind(this);
        this.headCoachSort = this.headCoachSort.bind(this);
        this.OCSort = this.OCSort.bind(this);
        this.DCSort = this.DCSort.bind(this);
        this.onModalClose = this.onModalClose.bind(this);
        this.setSelectedPlayer = this.setSelectedPlayer.bind(this);
    }

    componentDidMount() {
        axios.get('http://localhost:8080/api/teams/byName/' + this.props.match.params.schoolName)
        .then(result => {
            this.setState({
                teamId: result.data.id,
                schoolName: result.data.school,
                logo: result.data.logos[0],
                primaryColor: result.data.color,
                secondaryColor: result.data.alt_color
            }, () => {
                this.loadCoaches();
                this.loadCoordinators();
                this.loadPlayers(this.state.year);
            });
        });
    }

    loadCoaches(){
        // Get List Of Coaches From API
        axios.get('http://localhost:8080/api/coaches/byTeamId/' + this.state.teamId)
            .then(result => {
                this.setState({
                    coaches: result.data,
                    allCoaches: result.data,
                    loadedCoaches: true
                }, () => {
                    this.headCoachSort('Most Recent');
                });
            });
    }

    loadCoordinators(){
        // Get List Of Coaches From API
        axios.get('http://localhost:8080/api/coordinators/byTeamId/' + this.state.teamId)
            .then(result => {
                let oc = result.data.filter(x => x.position === "OC");
                let dc = result.data.filter(x => x.position === "DC");
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
        this.setState({
            year: newYear
        }, () => {
            // Get List Of Players From API
            axios.get('http://localhost:8080/api/roster/' + this.state.teamId + '/' + this.state.year)
            .then(result => {
                let tempPlayers = [];
                for(var x = 0; x < result.data.length; x++){
                    if(result.data[x].first_name !== null && result.data[x].last_name !== null && result.data[x].position !== null){
                        tempPlayers.push(result.data[x]);
                    }
                }
                this.setState({
                    players: tempPlayers,
                    allPlayers: tempPlayers,
                    loadedPlayers: true
                });
            })
        });
    }

    onModalClose(){
        this.setState({
            selectedPlayer: null
        });
    }

    setSelectedPlayer(player){
        this.setState({
            selectedPlayer: player
        })
    }

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

        }else if("Worst Score" === sortBy){

        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    OCSort(sortBy){
        if("Descending" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.name < b.name ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Ascending" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.name > b.name ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Most Recent" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.startYear < b.startYear ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Oldest" === sortBy){
            const sortedOC = [].concat(this.state.OC).sort((a, b) => a.startYear > b.startYear ? 1 : -1);
            this.setState({ OC: sortedOC });
        }else if("Best Score" === sortBy){

        }else if("Worst Score" === sortBy){

        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    DCSort(sortBy){
        if("Descending" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.name < b.name ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Ascending" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.name > b.name ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Most Recent" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.startYear < b.startYear ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Oldest" === sortBy){
            const sortedDC = [].concat(this.state.DC).sort((a, b) => a.startYear > b.startYear ? 1 : -1);
            this.setState({ DC: sortedDC });
        }else if("Best Score" === sortBy){

        }else if("Worst Score" === sortBy){

        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

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

        if(!this.state.loadedCoaches || !this.state.loadedPlayers || !this.state.loadedCoordinators){
            return <LoadingIndicator/>;
        }

        return (
            <div>
                <br/>
                <input type="text" placeholder="Search" onChange={(event) => {this.filter(event.target.value)}}/>
                <br/>
                <br/>
                <div>
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }} >&nbsp;{this.state.schoolName}</h1>
                    <img src={this.state.logo} width="100" height="100" alt="Logo" />
                </div>

                <div>
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Head Coaches
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={(event) => {this.headCoachSort(event.target.value)}}>
                            <option value="Most Recent">Most Recent</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Descending">Descending</option>
                            <option value="Ascending">Ascending</option>
                            <option value="Best Score">Best Score</option>
                            <option value="Worst Score">Worst Score</option>
                        </select>
                    </h1>
                    <Grid container align="center" spacing={3}>
                        {
                            this.state.coaches.map((coach, ndx) => {

                                let validSeasons = [];
                                for(let i = 0; i < coach.seasons.length; i++) {
                                    if(coach.seasons[i].school === this.state.schoolName){
                                        validSeasons.push(coach.seasons[i]);
                                    }
                                }

                                let yearRange = "";
                                if(validSeasons.length === 1){
                                    yearRange = validSeasons[0].year;
                                } else {
                                    let minYear = validSeasons[0].year;
                                    let maxYear = validSeasons[0].year;
                                    for(let i = 0; i < validSeasons.length; i++){
                                        if(validSeasons[i].school === this.state.schoolName && validSeasons[i].year < minYear){
                                            minYear = validSeasons[i].year;
                                        } else if (validSeasons[i].school === this.state.schoolName && validSeasons[i].year > maxYear){
                                            maxYear = validSeasons[i].year;
                                        }
                                    }
                                    yearRange = minYear + "-" + maxYear;
                                }
                                return (
                                    <Grid item xs={3} key={ndx}>
                                        <Link
                                            to={"/coach/" + coach.first_name + " " + coach.last_name}
                                            style={{ color: this.state.primaryColor }}
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
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Offensive Coordinators
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={(event) => {this.OCSort(event.target.value)}}>
                            <option value="Most Recent">Most Recent</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Descending">Descending</option>
                            <option value="Ascending">Ascending</option>
                            <option value="Best Score">Best Score</option>
                            <option value="Worst Score">Worst Score</option>
                        </select>
                    </h1>
                    <Grid container align="center" spacing={3}>
                        {
                            this.state.OC.map((oc, ndx) => {
                                return (
                                    <Grid item xs={3} key={ndx}>
                                        <a style={{ color: this.state.primaryColor }}>
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
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Defensive Coordinators
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={(event) => {this.DCSort(event.target.value)}}>
                            <option value="Most Recent">Most Recent</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Descending">Descending</option>
                            <option value="Ascending">Ascending</option>
                            <option value="Best Score">Best Score</option>
                            <option value="Worst Score">Worst Score</option>
                        </select>
                    </h1>
                    <Grid container align="center" spacing={3}>
                        {
                            this.state.DC.map((dc, ndx) => {
                                return (
                                    <Grid item xs={3} key={ndx}>
                                        <a style={{ color: this.state.primaryColor }}>
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
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Players
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={(event) => {this.loadPlayers(event.target.value)}}>
                            <option value="2019">2019</option>
                            <option value="2018">2018</option>
                            <option value="2017">2017</option>
                            <option value="2016">2016</option>
                        </select>
                    </h1>
                </div>
                <Grid container align="center" spacing={3}>
                    {
                        this.state.players.map((player, ndx) => {
                            return (
                                <Grid item xs={3} key={ndx}>
                                    <a
                                        onClick={() => {
                                            this.setSelectedPlayer({
                                                teamdId: this.state.teamId,
                                                playerId: player.id,
                                                first_name: player.first_name,
                                                last_name: player.last_name,
                                                year: this.state.year,
                                            })
                                        }}
                                        style={{ color: this.state.primaryColor }} >
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
                <Popup handleClose={this.onModalClose} open={this.state.selectedPlayer !== null} selectedPlayer={this.state.selectedPlayer}/>
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