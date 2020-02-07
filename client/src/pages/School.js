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
                this.setState({coaches: coachlist});
                console.log(this.state.coaches);
            })
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
                    {/* <table className="table">
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
                                <td>Coach1</td>
                                <td>Coach2</td>
                            </tr>
                        </tbody>
                    </table> */}

           <table>
                    {this.state.coaches.map((coach) => (
                        <div>
               <thead>
                            <tr>
                                <th><a href="/">
                                    <img src={logo} width="100" height="50" />
                                </a></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{coach.first_name + " " + coach.last_name}</td>
                            </tr>
                            <tr>
                                <td>{}</td>
                            </tr>
                        </tbody>
                        </div>
                ))}

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