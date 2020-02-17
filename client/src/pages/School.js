import React, { Component } from 'react';
import {
    withRouter, Route, Redirect
} from 'react-router-dom';
import '../common/AppHeader.css';
import logo from './football.jpeg'
import axios from 'axios';
class School extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    state = {
        coaches: [],
        players: [],
        logo: '',
        primaryColor: '',
        year: '2019'
    }

    componentDidMount() {
        // Get List Of Coaches From API
        axios.get('https://api.collegefootballdata.com/coaches?team=' + window.location.pathname.substr(8) + '&minYear=2000')
            .then(res => {
                const coachlist = res.data;
                this.setState({ coaches: coachlist });
            });
        // Get List Of Players From API
        axios.get('https://api.collegefootballdata.com/roster?team=' + window.location.pathname.substr(8) + '&year=' + this.state.year)
            .then(res => {
                const allplayerlist = res.data;
                var playerlist = [];
                if (allplayerlist.length <= 9) {
                    this.setState({ players: res.data });

                } else {
                    for (var x = 0; x < 7; x++) {
                        if (allplayerlist[x].first_name !== null) {
                            playerlist[x] = allplayerlist[x];
                        }
                    }
                    this.setState({ players: playerlist });
                }
            })
        // Get Team Color Scheme And Name From API
        if(this.props.school){
            this.setState({ logo: this.props.school.logos[0], primaryColor: this.props.school.color, secondaryColor: this.props.school.alt_color})
        }
    }


    getCoachYear(coach) {
        if (coach.seasons.length == 1) {
            return coach.seasons[0].year;
        } else {
            return coach.seasons[0].year + "-" + coach.seasons[coach.seasons.length - 1].year;
        }
    }

    getCoaches() {
        return (this.state.coaches.map((coach) => (
            <td width="150">
                <a style={{ color: this.state.primaryColor }} href="/">
                    <center >{coach.first_name + " " + coach.last_name}
                        <br></br>
                        {this.getCoachYear(coach)}</center>
                </a>
            </td>
        )));
    }

    getPlayers(playerYear) {
        // Get List Of Players From API
        axios.get('https://api.collegefootballdata.com/roster?team=' + window.location.pathname.substr(8) + '&year=' + playerYear)
            .then(res => {
                const allplayerlist = res.data;
                var playerlist = [];
                if (allplayerlist.length <= 9) {
                    this.setState({ players: res.data });
                } else {
                    for (var x = 0; x < 7; x++) {
                        if (allplayerlist[x].first_name !== null) {
                            playerlist[x] = allplayerlist[x];
                        }
                    }
                    this.setState({ players: playerlist });
                    this.setState({ year: playerYear });
                }
            })
    }

    render() {
        if(!this.props.school || window.location.pathname.replace('%20', ' ').substr(8) !== this.props.school.school){
            {this.props.school}
            //return(<Redirect to='/'/>)
        }
        return (
            <div>
                <div className="School_Info">
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff" }} >&nbsp;{window.location.pathname.replace('%20', ' ').substr(8).toUpperCase()}</h1>
                    <img src={this.state.logo} width="100" height="100" />
                </div>

                <div className="Coach_Table" >
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff"  }}>&nbsp;Coaches</h1>
                    <table>
                        <tbody >
                            <tr>
                                {/* Get Coaches Images */}
                                {this.state.coaches.map((coach) => (
                                    <th width="150"><a href="/">
                                        <center><img src={logo} width="100" height="50" /></center>
                                    </a></th>
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
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: "#ffffff"  }}>&nbsp;Players</h1>
                    <button onClick={() => this.getPlayers(2019)}>2019</button>
                    <button onClick={() => this.getPlayers(2018)}>2018</button>
                    <button onClick={() => this.getPlayers(2017)}>2017</button>
                    <button onClick={() => this.getPlayers(2016)}> 2016</button>
                    <table className="table">
                        <thead>
                            <tr>
                                {/* Player Images Here */}
                                {this.state.players.map((player) => (
                                    <th width="150"><a href="/">
                                        <center><img src={logo} width="100" height="50" /></center>
                                    </a></th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                {/* Get Coach Name & Year */}
                                {this.state.players.map((player) => (
                                    <td width="150">
                                        <a style={{ color: this.state.primaryColor }} href="/">
                                            <center >{player.first_name + " " + player.last_name}
                                                <br></br>
                                                {player.position ? player.position + " " + this.state.year : this.state.year}</center>
                                        </a>
                                    </td>
                                ))}
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default withRouter(School);