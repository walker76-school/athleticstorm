import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import axios from 'axios';
import { Link } from "react-router-dom";

class SchoolList extends Component {

    constructor(props){
        super(props);
        this.state = {
            teams: [],
            numOfTeamsDiv5: 0
        }
    }

    componentDidMount() {
        // Get List Of Coaches From API
        axios.get('https://api.collegefootballdata.com/teams/fbs')
        .then(res => {
            this.setState({
                teams: res.data,
                numOfTeamsDiv5: res.data.length / 5
            }, () => {console.log(this.state)});
        });
    }

    render() {
        var sortedTeams = [];
        var singleValueOfTeams = [];
        var count = 0;
        for (var x = 0; x < this.state.teams.length / 5; x++) {
            for (var y = 0; y < 5; y++) {
                singleValueOfTeams.push(this.state.teams[count]);
                count = count + 1;
            }
            sortedTeams.push(singleValueOfTeams);
            singleValueOfTeams = [];
        }
        // Return Images & Team Names
        return (sortedTeams.map((oneVal, i) => (
                <tr height="150" key={i}>
                    {
                        oneVal.map((team, index) => {
                            return (<SingleSchoolTile key={index} team={team} onSchoolSelected={this.props.onSchoolSelected}/>);
                        })
                    }
                </tr>
            ))
        );
    }
}

class SingleSchoolTile extends Component {

    render() {
        let { team } = this.props;

        return  (
            <th width="150" >
                <Link to={"/school/" + team.school} style={{ color: team.color }} onClick={() => this.props.onSchoolSelected(team)}>
                    <div style={{textAlign: "center" }}>
                        <img src={team.logos[0]} width="100" height="100" alt="school logo" />
                    </div>
                    <div style={{textAlign: "center" }}>
                        {team.school}
                    </div>
                </Link>
            </th>
        )
    }
}

export default withRouter(SchoolList);