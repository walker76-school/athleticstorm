import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';
import { ACCESS_TOKEN } from '../constants';


const coachInfoStyle = {"text-align": "center"};;

class Coach extends Component {

    constructor(props){
        super(props);
        this.state = {
            loading: true,
            first_name: "",
            last_name: "",
            seasonList: [{
                school: "",
                year: "",
                wins: 0,
                losses: 0,
                preseason_rank: null,
                postseason_rank: null
            }],
            termList: [{
                school: "",
                schoolPrimaryColor: "",
                schoolSecondaryColor: "",
                schoolLogo: "",
                seasonList: [{
                    year: "",
                    wins: 0,
                    losses: 0
                }]
            }]
        };
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
        axios.get('https://api.collegefootballdata.com/coaches?firstName=' + this.props.location.state.first_name + '&lastName=' + this.props.location.state.last_name)
            .then(res => {
                let terms = [{
                    school: "",
                    schoolPrimaryColor: "",
                    schoolSecondaryColor: "",
                    schoolLogo: "",
                    seasonList: [{
                        year: "",
                        wins: 0,
                        losses: 0
                    }]
                }];

                const coachData = res.data[0];
                if (coachData.length !== 0) {
                    this.setState({
                        first_name: coachData.first_name,
                        last_name: coachData.last_name,
                        seasonList: coachData.seasons
                    });

                    let urls = new Set()

                    // get the teams
                    for (let i = 0; i < this.state.seasonList.length; i++) {
                        urls.add(this.state.seasonList[i].school)
                    }

                    let urlList = [...urls];

                    // generate the axios get requests
                    const promises = urlList.map(x => {
                        return axios.get("http://localhost:8080/team/color", {headers: {Authorization: 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)},"params" : {"team" : x}});
                    });

                    console.log(promises);
                    Promise.all(promises).then(d => {
                        const mapping = new Map();
                        console.log(d);
                        for(let i = 0; i < urlList.length; i++){
                            mapping.set(urlList[i], d[i].data);
                        }

                        terms[0].school = this.state.seasonList[0].school;
                        terms[0].schoolPrimaryColor = d[0].data.color;
                        terms[0].schoolSecondaryColor = d[0].data.alt_color;
                        terms[0].schoolLogo = d[0].data.logo;
                        terms[0].seasonList[0].year = this.state.seasonList[0].year;
                        terms[0].seasonList[0].wins = this.state.seasonList[0].wins;
                        terms[0].seasonList[0].losses = this.state.seasonList[0].losses;
                        for (let i = 1; i < this.state.seasonList.length; i++) {
                            if (this.state.seasonList[i].school !== terms[terms.length - 1].school) {
                                const color = mapping.get(this.state.seasonList[i].school);
                                terms.push({
                                    school: this.state.seasonList[i].school,
                                    schoolPrimaryColor: color.color,
                                    schoolSecondaryColor: color.alt_color,
                                    schoolLogo: color.logo,
                                    seasonList: [{
                                        year: this.state.seasonList[i].year,
                                        wins: this.state.seasonList[i].wins,
                                        losses: this.state.seasonList[i].losses
                                    }]
                                });
                            } else {
                                terms[terms.length - 1].seasonList.push({
                                    year: this.state.seasonList[i].year,
                                    wins: this.state.seasonList[i].wins,
                                    losses: this.state.seasonList[i].losses
                                })
                            }
                        }
                        console.log(terms);
                        this.setState({
                            termList: terms
                        })
                })
                }
            })
    }

    render() {
        if (this.state.first_name.length !== 0) {

            // Name successfully found
            return (
                <div className="container">
                    <div className="Coach_Info" style={coachInfoStyle}>
                        <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.first_name} {this.state.last_name}</h1>
                        <h2>All time record: {this.allTimeWins()}-{this.allTimeLosses()}</h2>
                    </div>
                    <div className="Seasons">
                        <div className="Term_School_Name">
                            {this.state.termList.map(term => (
                                <div>
                                    <Link to={"/school/" + term.school} style={coachInfoStyle}>
                                        <h1 style={{  }}>
                                            <img style={{ marginLeft: 10 }} src={term.schoolLogo} height="100" width="100" alt={term.school}/>
                                            <span style={{ marginLeft: 30, color: term.schoolPrimaryColor}}>
                                                {term.school} ({term.seasonList[0].year}{term.seasonList.length > 1 && "-" + term.seasonList[0].year - term.seasonList.length+1})
                                                {term.seasonList.map(season => (
                                                    <p>
                                                        {season.year}: {season.wins}-{season.losses}
                                                    </p>
                                                ))}
                                            </span>
                                        </h1>
                                    </Link>
                                </div>
                            ))}
                            {/*{this.state.seasonList.map((season) => (*/}
                            {/*    <p>*/}
                            {/*        <Link to={"/school/" + season.school}>{season.school}</Link> ({season.year}): {season.wins}-{season.losses}*/}
                            {/*    </p>*/}
                            {/*))}*/}
                        </div>
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