import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import logo from './football.jpeg'
import axios from 'axios';
import { Link } from "react-router-dom";
class School extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        coaches: [],
        players: [],
        logo: '',
        primaryColor: '',
        year: '2019'
    }

    descSort() {
        const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name > b.last_name ? 1 : -1);
        this.setState({ coaches: sortedCoaches });
    }

    ascSort() {
        const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => a.last_name < b.last_name ? 1 : -1);
        this.setState({ coaches: sortedCoaches });
    }

    mostRecentSort() {
        const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10) < parseInt(b.seasons[0].year, 10) ? 1 : -1);
        this.setState({ coaches: sortedCoaches });
    }

    oldestSort() {
        const sortedCoaches = [].concat(this.state.coaches).sort((a, b) => parseInt(a.seasons[0].year, 10) > parseInt(b.seasons[0].year, 10) ? 1 : -1);
        this.setState({ coaches: sortedCoaches });
    }

    bestScoreSort() {

    }

    worstScoreSort() {

    }

    coachSort = (event) => {
        let sortBy = event.target.value;
        console.log(event.target.value);
        if ("Descending" === sortBy) {
            this.descSort();
        } else if ("Ascending" === sortBy) {
            this.ascSort();
        } else if ("Most Recent" === sortBy) {
            this.mostRecentSort();
        } else if ("Oldest" === sortBy) {
            this.oldestSort();
        } else if ("Best Score" === sortBy) {
            this.bestScoreSort();
        } else if ("Worst Score" === sortBy) {
            this.worstScoreSort();
        } else {
            console.log("Invalid Option " + sortBy);
        }
    }

    playerYearChanged = (event) => {
        this.getPlayers(event.target.value);
    }
    componentDidMount() {
        // Get Team Color Scheme And Name From API
        if (this.props.school) {
            this.setState({ logo: this.props.school.logos[0], primaryColor: this.props.school.color, secondaryColor: this.props.school.alt_color })
        } else {
                 // Get List Of Coaches From API
            axios.get('https://api.collegefootballdata.com/teams/fbs')
            .then(res => {
                const teamList = res.data;
                for(var x = 0; x < teamList.length; x++){
                    const teamName = window.location.pathname.replace('%20', ' ').substr(8).toUpperCase();
                    if(teamName === teamList[x].school.toUpperCase()){
                        this.setState({ logo: teamList[x].logos[0], primaryColor: teamList[x].color, secondaryColor: teamList[x].alt_color })
                    }
                }
            });
        }
        // Get List Of Coaches From API
        axios.get('https://api.collegefootballdata.com/coaches?team=' + window.location.pathname.substr(8) + '&minYear=2017')
            .then(res => {
                const coachlist = res.data;
                this.setState({ coaches: coachlist });
            });
        
        this.getPlayers(2019);
    }


    getCoachYear(coach) {
        if (coach.seasons.length === 1) {
            return coach.seasons[0].year;
        } else {
            return coach.seasons[0].year + "-" + coach.seasons[coach.seasons.length - 1].year;
        }
    }

    getCoaches() {
        return (this.state.coaches.map((coach, i) => (
            <td width="150" key={i}>
                <Link to={"/coach/" + coach.first_name + "-" + coach.last_name} style={{ color: this.state.primaryColor }} onClick={this.sendData}>
                    <center >{coach.first_name + " " + coach.last_name}
                        <br></br>
                        {this.getCoachYear(coach)}</center>
                </Link>

            </td>
        )));
    }

    getPlayers(playerYear) {
        this.setState({ year: playerYear });
        // Get List Of Players From API
        axios.get('https://api.collegefootballdata.com/player/usage?year=' + this.state.year + '&team=' + window.location.pathname.substr(8) + '&excludeGarbageTime=true')
            .then(res => {
                const allplayerlist = res.data;
                var playerlist = [];
                if (allplayerlist.length <= 9) {
                    this.setState({ players: res.data });
                } else {
                    for (var y = 0; y < allplayerlist.length/7; y++) {
                        var tempList = [];
                        for (var x = 0; x < 7 && y*7+x < allplayerlist.length; x++) {
                            if (allplayerlist[y*7+x].first_name !== null) {
                                tempList[x] = allplayerlist[y*7+x];
                            }
                        }
                        playerlist[y] = tempList;
                    }
                    this.setState({ players: playerlist });
                }
            })
    }

    render() {
        // if (!this.props.school && this.state.logo === '') {
        //     return (<Redirect to='/' />)
        // }
        return (
            <div>
                <br></br>
                <div className="School_Info">
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }} >&nbsp;{window.location.pathname.replace('%20', ' ').substr(8).toUpperCase()}</h1>
                    <img src={this.state.logo} width="100" height="100" alt="Logo" />
                </div>

                <div className="Coach_Table" >
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
                    <table>
                        <tbody >
                            <tr>
                                {/* Get Coaches Images */}
                                {this.state.coaches.map((coach, i) => (
                                    <th key={i} width="150">
                                        <Link to={"/coach/" + coach.first_name + "-" + coach.last_name} style={{ color: this.state.primaryColor }} >
                                            <center><img src={logo} width="100" height="50" alt={`coach${coach.first_name} ${coach.last_name}`} /></center>
                                        </Link></th>
                                ))}
                            </tr>
                            <tr>
                                {/* Get Coach Name & Year */}
                                {this.getCoaches()}
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div className="Player_Table">
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }}>&nbsp;Players
                        <select style={{ float: 'right', color: this.state.primaryColor }} onChange={this.playerYearChanged}>
                            <option value="2019">2019</option>
                            <option value="2018">2018</option>
                            <option value="2017">2017</option>
                        </select>
                    </h1>
                    <table className="table">


                        {/* Get Player Images */}
                        {this.state.players.map((playery, x) => (
                            <tbody key={x}>
                                <tr>
                                    {playery.map((player, i) => (
                                        <td width="150" key={i}>
                                            <Link to={`/player/${player.first_name}_${player.last_name}`} style={{ color: this.state.primaryColor }} >
                                                <center><img src={logo} width="100" height="50" alt={`${player.first_name} ${player.last_name}`} /></center>
                                            </Link>
                                        </td>
                                    ))}
                                </tr>
                                <tr>
                                    {playery.map((player, i) => (
                                        <td width="150" key={i}>
                                            <Link to={`/player/${player.name.substr(0, player.name.indexOf(' '))}_${player.name.substr(player.name.indexOf(' ') + 1)}`} style={{ color: this.state.primaryColor }} >
                                                <center >{player.name}
                                                    <br></br>
                                                    {player.position ? player.position + " " + this.state.year : this.state.year}
                                                </center>
                                            </Link>
                                        </td>
                                    ))}
                                </tr>

                            </tbody>
                        ))}
                    </table>
                </div>
            </div>
        );
    }
}

export default withRouter(School);