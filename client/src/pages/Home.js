import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';
import { Link } from "react-router-dom";
class Home extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        teams: [],
        numOfTeamsDiv5: 0
    }
    componentDidMount() {
        // Get List Of Coaches From API
        axios.get('https://api.collegefootballdata.com/teams/fbs')
            .then(res => {
                const teamList = res.data;
                this.setState({ teams: teamList });
                this.setState({ numOfTeamsDiv5: teamList.length / 5 });
            });
    }

    getTeams() {
        var sortedTeams = [];
        var singleValueOfTeams = [];
        var count = 0;
        for (var x = 0; x < this.state.teams.length / 5; x++) {
            for (var y = 0; y < 5; y++) {
                singleValueOfTeams.push(this.state.teams[count])
                count = count + 1;
            }
            sortedTeams.push(singleValueOfTeams);
            singleValueOfTeams = [];
        }
        // Return Images & Team Names
        return (sortedTeams.map((oneVal) => (
            <tr height="150">
                {oneVal.map((singleTeam) =>
                    <SingleSchool changeSchool={this.props.changeSchool}singleTeam={singleTeam}></SingleSchool>
                )
                }
            </tr>

        ))
        )
    }
    render() {
        return (
            <div className="Home_Page" >
                <h1>Home</h1>
                <table>
                    <tbody>
                        {
                            this.getTeams()
                        }
                    </tbody>
                </table>
            </div>
        );
    }
}

class SingleSchool extends Component {

    constructor(props){
        super(props);
        
        this.sendData = this.sendData.bind(this);
    }

    sendData(){
        if(this.props.changeSchool){
            <Link></Link>
            this.props.changeSchool(this.props.singleTeam);

        }

    }

    render() {
        let { singleTeam } = this.props;

        return  (
            <th width="150" ><Link to={"/School/"+this.props.singleTeam.school} style={{ color: singleTeam.color }}  onClick={this.sendData}>
                 <center><img src={singleTeam.logos[0]} width="100" height="100" /></center>
                 <center>{singleTeam.school}</center>
             </Link></th>
        )
    }
}
//href={'School/' + this.props.school}
export default withRouter(Home);