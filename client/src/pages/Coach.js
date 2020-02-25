import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
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
        }],
        termList: [{
            school: "",
            schoolPrimaryColor: "",
            schoolSecondaryColor: "",
            schoolLogo: "",
            startYear: "",
            numYears: 0,
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
        axios.all([
            axios.get('https://api.collegefootballdata.com/coaches?firstName=' + names[0] + '&lastName=' + names[1]),
            axios.get('https://api.collegefootballdata.com/teams')
           ])
            .then(res => {
                console.log(res[0].data[0]);
                if (res[0].data[0].length !== 0) {
                    this.setState({
                        first_name: res[0].data[0].first_name,
                        last_name: res[0].data[0].last_name,
                        seasonList: res[0].data[0].seasons
                    });
                }

                let terms = [{
                    school: "",
                    schoolPrimaryColor: "",
                    schoolSecondaryColor: "",
                    startYear: "",
                    numYears: 0
                }];

                let schoolPrimaryColorList = [];
                let schoolSecondaryColorList = [];
                let schoolLogoList = [];
                for (let i = 0; i < this.state.seasonList.length; i++) {
                    for (let j = 0; j < res[1].data.length; j++) {
                        if (res[1].data[j].school === this.state.seasonList[i].school) {
                            schoolPrimaryColorList.push(res[1].data[j].color);
                            schoolSecondaryColorList.push(res[1].data[j].alt_color);
                            schoolLogoList.push(res[1].data[j].logos[0]);
                        }
                    }
                }
                terms[0].school = this.state.seasonList[0].school;
                terms[0].schoolPrimaryColor = schoolPrimaryColorList[0];
                terms[0].schoolSecondaryColor = schoolSecondaryColorList[0];
                terms[0].schoolLogo = schoolLogoList[0];
                terms[0].startYear = this.state.seasonList[0].year;
                terms[0].numYears = 1;
                for (let i = 1; i < this.state.seasonList.length; i++) {
                    if (this.state.seasonList[i].school === terms[terms.length-1].school) {
                        terms[terms.length-1].numYears++;
                    } else {
                        terms.push({
                            school: this.state.seasonList[i].school,
                            schoolPrimaryColor: schoolPrimaryColorList[i],
                            schoolSecondaryColor: schoolSecondaryColorList[i],
                            schoolLogo: schoolLogoList[i],
                            startYear: this.state.seasonList[i].year,
                            numYears: 1
                        });
                    }
                }
                this.setState({
                    termList: terms
                });
                console.log(terms);

            });
    }

    // calculateTerms() {
    //     return this.terms;
    // }
    //
    // getSchoolColor(schoolName) {
    //     axios.get('https://api.collegefootballdata.com/teams')
    //         .then(res => {
    //             const teams = res.data;
    //             let ret = teams.filter(function (x) {return x.school === schoolName;});
    //             // console.log(ret[0].color);
    //             ret[0].color;
    //         });
    // }

    render() {
        if (this.state.first_name.length !== 0) {

            // Name successfully found
            return (
                <div className="container">
                    <div className="Coach_Info">
                        <center>
                            <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.first_name} {this.state.last_name}</h1>
                            <h2>All time record: {this.allTimeWins()}-{this.allTimeLosses()}</h2>
                        </center>
                    </div>
                    <div className="Seasons">
                        <div className="Term_School_Name">
                            {this.state.termList.map((term) => (
                                <div>
                                    <Link to={"/school/" + term.school}>
                                        <center>
                                            <h1 style={{  }}>
                                                <img style={{ marginLeft: 10 }} src={term.schoolLogo} height="100" width="100" alt={term.school}/>
                                                <span style={{ marginLeft: 30, color: term.schoolPrimaryColor}}>
                                                    {term.school} ({term.startYear}{term.numYears > 1 && "-" + term.startYear - term.numYears+1})
                                                </span>
                                            </h1>
                                        </center>
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