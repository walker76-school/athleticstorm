import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';

class Coach extends Component {
    constructor(props) {
        super(props);
    }

    state = {
        first_name: "",
        last_name: "",
        seasonList: [{
            school: "",
            year: "",
            wins: 0,
            losses: 0,
            preseason_rank: null,
            postseason_rank: null
        }]
};


    coachUrlExtractor() {
        let name = window.location.pathname.substr(7);
        return name.split('-');
    }

    componentDidMount() {
        // Get coach name from url
        let names = this.coachUrlExtractor();
        axios.get('https://api.collegefootballdata.com/coaches?firstName=' +
            names[0] + '&lastName=' + names[1] + '&minYear=2000')
            .then(res => {
                const coaches = res.data;
                console.log(coaches[0]);
                this.setState({
                    first_name: coaches[0].first_name,
                    last_name: coaches[0].last_name,
                    seasonList: coaches[0].seasons
                });
            })
    }

    render() {
        return (
            <div>
                <div className="Coach_Info">
                    <h1>{this.state.first_name} {this.state.last_name}</h1>
                </div>
                <div className="Seasons">
                    <h3>
                    {this.state.seasonList.map((season) => (
                        <p>{season.school} ({season.year}): {season.wins}-{season.losses}</p>
                    ))}
                    </h3>
                </div>
            </div>
        )
    }
}

export default withRouter(Coach);