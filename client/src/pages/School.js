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
        players: []
    }

    componentDidMount() {
        axios.get('https://api.collegefootballdata.com/coaches?team=' + window.location.pathname.substr(8) + '&minYear=2000')
            .then(res => {
                const coachlist = res.data;
                this.setState({ coaches: coachlist });
                console.log(this.state.coaches);
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
                    <h1>{window.location.pathname.substr(8)}</h1>
                    <body>Their Computer Science Department Sucks</body>
                </div>
                <div className="Coach_Table">
                    <h1>Coaches</h1>
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
                                            <center>{coach.first_name + " " + coach.last_name}
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
                    <h1>Players</h1>
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
                                <td>Player1</td>
                                <td>Player2</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default withRouter(School);