import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';

class Coach extends Component {
    constructor(props) {
        super(props);
        this.state = {
            coach_first_name: [],
            coach_last_name: []
        }
    }

    componentDidMount() {
        fetch('https://api.collegefootballdata.com/coaches?minYear=2000')
            .then(res => res.json())
            .then(result => this.setState({ coach_first_name: result[0].first_name, coach_last_name: result[0].last_name }))
        console.log("state", this.state.coach_first_name);
    }

    render() {
        return (
            <div>
                <center><h1>Coaches</h1></center>
                    <div className="card">
                        <div className="card-body">
                        <h2>{this.state.coach_first_name} {this.state.coach_last_name}</h2>
                        <h5></h5>
                        </div>
                    </div>
            </div>
        )
    }
}

export default withRouter(Coach);