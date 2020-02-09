import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import logo from './football.jpeg'
import axios from 'axios';
class School extends Component {
    constructor(props) {
        super(props);
    }
    state = {
        coaches: [],
        players: [],
        logo: '',
        conference: 'B12',
        primaryColor: '',
        secondaryColor: '',
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
            const playerlist = res.data;
            this.setState({ players: playerlist });
            console.log(this.state.players);
        })
        // Get Team Color Scheme And Name From API
        axios.get('https://api.collegefootballdata.com/teams?conference=' + this.state.conference)
            .then(res => {
                const teams = res.data;
                for (var x = 0; x < teams.length; x++) {
                    var team = teams[x];
                    if (team.school.toUpperCase() === window.location.pathname.substr(8).toUpperCase()) {
                        this.setState({ logo: team.logos[0] });
                        this.setState({ primaryColor: team.color });
                        this.setState({ secondaryColor: team.alt_color })
                        console.log("background-color:" + this.state.primaryColor + ";")
                    }
                }
            })
    }


    getCoachYear(coach) {
        if (coach.seasons.length == 1) {
            return coach.seasons[0].year;
        } else {
            return coach.seasons[0].year + "-" + coach.seasons[coach.seasons.length - 1].year;
        }
    }

    render() {

        return (
            <div>
                <div className="School_Info">
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: this.state.secondaryColor }} >{window.location.pathname.substr(8).toUpperCase()}</h1>
                    <img src={this.state.logo} width="100" height="100" />
                </div>
                <div className="Coach_Table" >
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: this.state.secondaryColor }}>Coaches</h1>
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
                                {this.state.coaches.map((coach) => (
                                    <td width="150">
                                        <a href="/">
                                            <center >{coach.first_name + " " + coach.last_name}
                                                <br></br>
                                                {this.getCoachYear(coach)}</center>
                                        </a>
                                    </td>
                                ))}
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div className="Player_Table">
                    <h1 style={{ backgroundColor: this.state.primaryColor, color: this.state.secondaryColor }}>Players</h1>
                    <table className="table">
                        <thead>
                            <tr>
                                <th><a href="/">
                                    <img src={logo} width="100" height="50" />
                                </a></th>
                                <th><a href="/">
                                    <img src={logo} width="100" height="50" />
                                </a></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                {/* Get Coach Name & Year */}
                                {this.state.players.map((player) => (
                                    <td width="150">
                                        <a href="/">
                                            <center >{player.first_name + " " + player.last_name}
                                                <br></br>
                                                {this.state.year}</center>
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