import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import '../common/AppHeader.css';
import axios from 'axios';

class Coach extends Component {

    constructor(props){
        super(props);
        this.state = {
            loading: true,
            first_name: "",
            last_name: "",
            record: null
        };
    }

    componentDidMount() {
        // Get coach name from url
        axios.get('http://localhost:8080/api/coaches/record/byName/' + this.props.location.state.first_name + '-' + this.props.location.state.last_name)
        .then(result => {
            console.log(result);
            this.setState({
                loading: false,
                first_name: result.data.first_name,
                last_name: result.data.last_name,
                record: result.data
            });
        })
    }

    render() {
        if (!this.state.loading) {

            // Name successfully found
            return (
                <div className="container">
                    <div className="Coach_Info" style={{"text-align": "center"}}>
                        <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.first_name} {this.state.last_name}</h1>
                        <h2>All time record: {this.state.record.wins}-{this.state.record.losses}</h2>
                    </div>
                    <div className="Seasons">
                        <div className="Term_School_Name">
                            {this.state.record.terms.map(term => (
                                <div>
                                    <Link
                                        to={{
                                            pathname: "/school/" + term.team.school,
                                            state: {
                                                teamId: term.team.id
                                            }
                                        }}
                                        style={{"text-align": "center"}}
                                    >
                                        <h1 style={{  }}>
                                            <img style={{ marginLeft: 10 }} src={term.team.logos[0]} height="100" width="100" alt={term.team.school}/>
                                            <span style={{ marginLeft: 30, color: term.team.color}}>
                                                {term.team.school} ({term.start_year}{term.end_year !== -1 ? "-" + term.end_year : ""})

                                                {term.seasons.map(season => (
                                                    <p>
                                                        {season.year}: {season.wins}-{season.losses}
                                                    </p>
                                                ))}
                                            </span>
                                        </h1>
                                    </Link>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            )
        } else {
            // Error: name not found
            return (
                <div>
                    <div className="Error_Header">
                        <h1>Loading Coach Info ... </h1>
                    </div>
                </div>
            )
        }
    }
}

export default withRouter(Coach);