/*
*   Filename: WelcomePage.js
*   Author: John Eyre
*   Date Last Modified: 4/23/2019
*/
import React, {Component} from "react";
import {Link} from "react-router-dom";
import BleachersPic from "./bleachers.jpg";
import CoachPic from "./coachpicture.jpg";
import Button from "@material-ui/core/Button";

export default class WelcomePage extends Component {
// Formatting and HTML for Athletic Storm information page
    render() {
        return (
            <div>
                <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <div style={{ backgroundImage: `url(${BleachersPic})`, backgroundPosition: 'center center', backgroundAttachment: 'fixed', backgroundSize: 'auto', backgroundRepeat: 'no-repeat', width: '100%' }}>
                        <h1 style={{ marginTop: '200px', display: 'flex', justifyContent: 'center', color: 'white', fontSize: '80pt' }}>Athletic Storm</h1>
                        <h2 style={{ display: 'flex', justifyContent: 'center', color: 'white' }}>Always make the best hire</h2>
                        <h3 style={{ marginBottom: '200px', display: 'flex', justifyContent: 'center', color: 'white' }}>Filter through all of college football in an instant</h3>
                    </div>
                </div>

                <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'center' }}>
                    <h5>Sign up for a subscription tier to view coach ratings!</h5>
                </div>
                <Link to={'/signup'} style={{ margin: '20px', display: 'flex', justifyContent: 'center' }}>
                    <Button size='large' variant='contained' color='primary'>Sign Up</Button>
                </Link>
                <h6 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>Athletic Storm is a college football coach rating system that uses our Comprehensive Review algorithms to objectively and accurately determine the best coaches in the Football Bowl Subdivision.</h6>

                <div style={{ marginTop: '25px', backgroundImage: `url(${CoachPic})`, backgroundPosition: 'center center', backgroundAttachment: 'fixed', backgroundSize: 'auto', backgroundRepeat: 'no-repeat', width: '100%' }}>
                    <h1 style={{ paddingLeft: '50px', paddingTop: '200px', paddingBottom: '200px' }}>Coach Rating System</h1>
                </div>

                <h5 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>The very basis of Athletic Storm is to provide an easier way for anyone to determine the effectiveness of college football coaches based on their rating. This rating process is done by a series of algorithms we call:</h5>
                <br/>
                <h2 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>Comprehensive Review</h2>
                <h5 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>a set of finely-tuned algorithms that quantify the progress (or lack thereof) of a certain coach or coordinator. Comprehensive Review is a two-fold system.</h5>

                <br/>
                <h6 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>Part 1: Analysis of weekly games</h6>
                <p style={{ display: 'flex', justifyContent: 'center' }}>This weekly rating takes 2 major data elements into account: a team's S&P+ rating and a team's PPA. S&P+ rating uses 4 factors to assess a team including efficiency (rate of success), explosiveness, field position and completion of drives. PPA (Predicted Points Added) uses 3 factors to measure the value of individual plays including the EP (Expected Points) of distance, down, and field positions at the beginning, during, and end of a play.</p>
                <p style={{ display: 'flex', justifyContent: 'center' }}>Using S&P+ ratings in tandem with specific PPA averages for the offensive and defensive aspects of the team allow a unique perspective of progress throughout each game. This approach allows us to see progress such that each down of each game counts. Mistakes made during certain situations are shown yet corrected mistakes are displayed when comparing PPA of future downs and games. The combination of both S&P+ ratings and PPA takes the team's opponent into account allowing for a more fair and less bias result. Additionally, due to the variablity of data, both S&P+ ratings and PPA are normalized weekly against the rest of the FBS when using Comprehensive Review.</p>
                <p style={{ display: 'flex', justifyContent: 'center' }}>When concerning the assessment of coordinators, data concerning the offensive coordinator (and team) will not have any impact on the rating of the defensive coordinator. Likewise, any data concerning the defensive coordinator (and team) will not affect the rating of the offensive coordinator. For the weekly rating, the PPA used for rating the offensive coordinator is the overall average of the offensive team. In the same way, the PPA used for rating the defensive coordinator is the overall average of the defensive team. When concerning the assessment of assistant coaches and head coaches, we deliberated over it for quite a while. We came to the conclusion that a good coach is reflected by the progress his/her subordinates.</p>
                <p style={{ display: 'flex', justifyContent: 'center' }}>The weekly rating for assistant coaches uses 2 main factors: the average rating of all offensive coordinators and the average rating of all defensive coordinators. The weekly rating for head coaches uses 3 main factors: average assistant coach ratings, home game win percentages and away game win percentages. While this may seem too simple to be effective, it is quite the opposite. The analysis of both the offensive and defensive teams is represented in the rating of the assistant coaches. The factoring in of the win percentages across both home games and away games removes some bias and allows an equal emphasis on every game.</p>

                <br/>
                <h6 style={{ textAlign: 'center', display: 'flex', justifyContent: 'center' }}>Part 2: Improvement over time of the General Rating</h6>
                <p style={{ display: 'flex', justifyContent: 'center' }}>Every weekly rating of every coach and coordinator is then factored into the calculation of the General Rating for that specific coach. Our algorithm allows for a coach's score to rapidly improve (if success is sustained) or slowly decline (if failure persists). During the calculation of each coach's General Rating, more recent games have a higher impact than games when they first started. This General Rating is the final product of our Comprehensive Review.</p>
                <p style={{ display: 'flex', justifyContent: 'center' }}>To reduce bias, we decided that each coach start with a clean slate before the year of 2017. This not only allows each coach to have a uniform starting point but ensures that our database contains enough data to calculate an accurate rating.</p>
                <p style={{ display: 'flex', justifyContent: 'center' }}>To allow for easier understanding of the ratings, each rating is from 0 (lowest rating) to 100 (higest rating). All coaches start with a default rating of 50. Coaches with a poor rating are those whose ratings are somewhere between 0 to 20. Coaches with an average rating are those whose ratings are somewhere between 45 and 55. Coaches who are at the top of their game are those whose ratings are above 60.</p>
            </div>
        );
    }
}
