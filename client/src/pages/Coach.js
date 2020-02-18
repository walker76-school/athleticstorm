import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';

class Coach extends Component {

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

    allTimeWins() {
        let wins = 0;
        for (let i = 0; i < this.state.seasonList.length; i++) {
            wins += this.state.seasonList[i].wins;
        }
        return wins;
    }

    allTimeLosses() {
        let losses = 0;
        for (let i = 0; i < this.state.seasonList.length; i++) {
            losses += this.state.seasonList[i].losses;
        }
        return losses;
    }

    componentDidMount() {
        // Get coach name from url
        let names = this.coachUrlExtractor();
        axios.get('https://api.collegefootballdata.com/coaches?firstName=' +
            names[0] + '&lastName=' + names[1] + '&minYear=2000')
            .then(res => {
                const coaches = res.data;
                console.log(coaches[0]);
                if (coaches.length !== 0) {
                    this.setState({
                        first_name: coaches[0].first_name,
                        last_name: coaches[0].last_name,
                        seasonList: coaches[0].seasons
                    });
                }
            })
    }

    render() {
        if (this.state.first_name.length !== 0) {

            // Name successfully found
            return (
                <div>
                    <div className="Coach_Info">
                        <h1>{this.state.first_name} {this.state.last_name}</h1>
                        <h2>All time record: {this.allTimeWins()}-{this.allTimeLosses()}</h2>
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
        } else {

            // Error: name not found
            return (
                <div>
                    <div className="Error_Header">
                        <h1>Coach Not Found</h1>
                    </div>
                </div>
            )
        }
    }
}

export default withRouter(Coach);