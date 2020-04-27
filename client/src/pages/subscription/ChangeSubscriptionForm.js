/*
*   Filename: ChangeSubscriptionForm.js
*   Author: Andrew Walker
*   Date Last Modified: 4/26/2019
*/

import React, { Component } from 'react';
import '../../common/AppHeader.css';
import SubscriptionList from "../../user/signup/subscriptions/SubscriptionList";
import {changeSubscription} from '../../util/APIUtils';
import Cookies from 'universal-cookie';
import {notification} from "antd";
import {SUBSCRIPTION_PLAYER_MAPPING, SUBSCRIPTION_TEAM_MAPPING} from "../../constants";
import {withRouter} from "react-router-dom";

const cookies = new Cookies();

class ChangeSubscriptionForm extends Component {

    constructor(props) {
        super(props);
        this.handleChangeSubscription = this.handleChangeSubscription.bind(this);
    }

    handleChangeSubscription(roleVal){
        if(roleVal === cookies.get('Role')) {
            notification.success({
                message: 'Athletic Storm',
                description: "Your subscription role has not been changed.",
            });
            this.props.history.push("/");
        } else{
            let username = cookies.get('Username');
            const subChangeRequest = {
                username: username,
                password: 'password',
                roleName: roleVal
            };

            changeSubscription(subChangeRequest)
                .then(response => {
                    notification.success({
                        message: 'Athletic Storm',
                        description: "Your subscription tier has been changed!",
                    });
                    // If my current plan has less than my new plan
                    if(SUBSCRIPTION_TEAM_MAPPING.get(cookies.get('Role')) < SUBSCRIPTION_TEAM_MAPPING.get(roleVal)) {
                        // Take my new max and remove what I already have viewed
                        let numTeams = SUBSCRIPTION_TEAM_MAPPING.get(roleVal) - cookies.get('Teams_visited').length;
                        cookies.set('Num_teams', numTeams, {path: '/'});

                    } else { // If my current plan has more than my new plan

                        // Find out how many teams I have seen. Subtract from new allowed. Cap at 0
                        let numTeams = SUBSCRIPTION_TEAM_MAPPING.get(roleVal) - cookies.get('Teams_visited').length;
                        //Set subscription values
                        if(numTeams < 0){
                            numTeams = 0;
                        }
                        cookies.set('Num_teams', numTeams, {path: '/'});

                        // Cut off number of allowed teams
                        cookies.set('Teams_visited', cookies.get('Teams_visited').splice(0, SUBSCRIPTION_TEAM_MAPPING.get(roleVal)), {path: '/'});

                    }
                    cookies.set('Role', roleVal, {path: '/'});
                    cookies.set('Num_players', SUBSCRIPTION_PLAYER_MAPPING.get(roleVal), {path: '/'});
                    this.props.history.push("/");

                }).catch(error => {
                notification.error({
                    message: 'Athletic Storm',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
                this.props.history.push("/");
            });
        }
    }

    render() {

        return(
            <SubscriptionList handleSubmit={this.handleChangeSubscription} tier={cookies.get('Role')}/>
        );
    }
}

export default withRouter(ChangeSubscriptionForm);
