/*
*   Filename: Coach.js
*   Author: John Eyre
*   Date Last Modified: 4/23/2019
*/
import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import Grid from "@material-ui/core/Grid";
import {Paper} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import ReactMinimalPieChart from "react-minimal-pie-chart";
import LoadingIndicator from "../../common/LoadingIndicator";
import LockIcon from "../../common/LockIcon.png";
import Cookies from 'universal-cookie';
import {notification} from "antd";
import {coachByName} from "./API";
import Button from "@material-ui/core/Button";

const cookies = new Cookies();

class Coach extends Component {

    constructor(props){
        super(props);
        this.state = {
            loading: true,
            name: "",
            last_name: "",
            record: null
        };
    }

    componentDidMount() {
        // Get coach name from url
        coachByName(this.props.match.params.coachName)
        .then(result => {
            console.log(result);
            this.setState({
                loading: false,
                name: result.name,
                record: result
            });
        })
            .catch(error => {
                notification.error({
                    message: 'Athletic Storm',
                    description: "Couldn't load data for " + this.props.match.params.coachName  + "."
                });

                this.setState({
                    loading: false,
                });
            })
    }
    //Conversion to get hash of color
    perc2color(perc) {
        var r, g, b = 0;
        if(perc < 50) {
            r = 255;
            g = Math.round(5.1 * perc);
        }
        else {
            g = 255;
            r = Math.round(510 - 5.10 * perc);
        }
        var h = r * 0x10000 + g * 0x100 + b;
        return '#' + ('000000' + h.toString(16)).slice(-6);
    }

    render() {
        if (!this.state.loading) {
            //Error Page Creation
            if(this.state.record === null){
                return (
                  <div style={{"text-align": "center"}}>
                      <h1>Couldn't load data for {this.props.match.params.coachName}.</h1>
                      <Link to='/'>
                          <Button size="medium" variant="contained" color="secondary">
                              Return to Teams Page
                          </Button>
                      </Link>
                  </div>
                );
            }
            //Handling insufficient subscription
            let ratingContent = <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.name} (<span style={{color: this.state.record.rating === -1 ? "#000000" : this.perc2color(this.state.record.rating)}}>{this.state.record.rating === -1 ? "--" : this.state.record.rating.toFixed(2)}</span>)</h1>;
            if(cookies.get('Role') === 'ROLE_REDSHIRT') {
                ratingContent = <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.name} (<img alt="Lock" style={{maxHeight: "75px"}} src={LockIcon} onClick={() => {
                    notification.error({
                        message: 'Athletic Storm',
                        description: 'Upgrade your subscription to see ratings.'
                    });
                }}/>)</h1>
            }

            // Name successfully found
            return (
                <div>
                    {/* Get overall wins and losses for coaches */}
                    <div style={{"text-align": "center"}}>
                        {ratingContent}
                        <h2>All time record: {this.state.record.wins}-{this.state.record.losses}</h2>
                    </div>
                    <br/>
                    {/* Get all season records with their teams */}
                    {this.state.record.terms.map(term => (
                        <div>
                            <Paper style={{paddingRight: '10px', marginBottom: '15px'}}>
                                <Grid container align="center" justify="left" spacing={3}>
                                    <Grid item xs={2}>
                                        <Link
                                            to={{
                                                pathname: "/school/" + term.team.school,
                                                state: {
                                                    teamId: term.team.id
                                                }
                                            }}
                                            style={{"text-align": "center"}}
                                        >
                                            <img style={{ marginLeft: 10 }} src={term.team.logos[0]} height="100" width="100" alt={term.team.school}/>
                                        </Link>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <span style={{ marginLeft: 30, color: term.team.color}}>
                                            {term.team.school} ({term.start_year}{term.end_year !== -1 && term.end_year !== term.start_year ? "-" + term.end_year : ""})
                                        </span>
                                    </Grid>
                                    <Grid item xs={7}>
                                        <Grid container align="center" justify="left" spacing={3}>
                                            {term.seasons.map(season => (
                                                <Grid item xs={3}>
                                                    <Paper>
                                                        <ReactMinimalPieChart
                                                            animate={true}
                                                            animationDuration={500}
                                                            animationEasing="ease-out"
                                                            cx={50}
                                                            cy={50}
                                                            data={[
                                                                {
                                                                    color: '#009900',
                                                                    title: 'Wins',
                                                                    value: season.wins
                                                                },
                                                                {
                                                                    color: '#CC0000',
                                                                    title: 'Losses',
                                                                    value: season.losses
                                                                }
                                                            ]}
                                                            label={false}
                                                            radius={25}
                                                            viewBoxSize={[
                                                                30,
                                                                30
                                                            ]}
                                                        />
                                                        <Typography>{season.year}: {season.wins}-{season.losses}</Typography>
                                                    </Paper>
                                                </Grid>
                                            ))}
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Paper>
                            <br/>
                        </div>
                    ))}
                </div>
            )
        } else {
            // Error: name not found
            return (
                <LoadingIndicator />
            )
        }
    }
}

export default withRouter(Coach);