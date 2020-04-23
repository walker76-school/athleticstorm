import React, { Component } from 'react';
import '../common/AppHeader.css';
import Button from '@material-ui/core/Button';
import { styled } from '@material-ui/core/styles';
import {Link} from "react-router-dom";
import SubscriptionList from "../user/signup/subscriptions/SubscriptionList";
import {changeSubscription} from '../util/APIUtils';
import Cookies from 'universal-cookie';
import {notification} from "antd";
import {SUBSCRIPTION_PLAYER_MAPPING, SUBSCRIPTION_TEAM_MAPPING} from "../constants";

const SubscriptionButton = styled(Button)({
    background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',

});

const TeamsButton = styled(Button)({
    background: 'linear-gradient(45deg, #0099ff 30%, #0033cc 90%)',
    border: 0,
    borderRadius: 3,
    boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
    color: 'white',
    height: 48,
    padding: '0 30px',
    // paddingTop: '100px'
});

const cookies = new Cookies();

class SubscriptionError extends Component {

    constructor(props) {
        super(props);
        this.state = {sub: false};
        this.loadChangeSubscription = this.loadChangeSubscription.bind(this);
        this.handleChangeSubscription = this.handleChangeSubscription.bind(this);
    }

    loadChangeSubscription() {
        this.setState({sub: true});
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
                    if(SUBSCRIPTION_TEAM_MAPPING.get(cookies.get('Role')) < SUBSCRIPTION_TEAM_MAPPING.get(roleVal)) {
                        let num_teams_difference = SUBSCRIPTION_TEAM_MAPPING.get(roleVal) - SUBSCRIPTION_TEAM_MAPPING.get(cookies.get('Role'));
                        let curr_num_teams = parseInt(cookies.get('Num_teams'));
                        cookies.set('Num_teams', (curr_num_teams + num_teams_difference), {path: '/'});
                    } else{
                        cookies.set('Num_teams', SUBSCRIPTION_TEAM_MAPPING.get(roleVal), {path: '/'});
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
        //Render error message
        <div>
            {!this.state.sub &&
            <div>
                <br/>
                <div style={{
                    textAlign: 'center',
                    paddingTop: '150px',
                    paddingBottom: '150px',
                    border: '5px solid blue'
                }}>
                    <div style={{fontSize: '40px', paddingBottom: '30px'}}>Access more content by upgrading your subscription!
                    </div>
                    <SubscriptionButton onClick={this.loadChangeSubscription} size="medium" variant="contained"
                                        color="secondary">
                        Update Your Subscription Plan
                    </SubscriptionButton>
                    <br/><br/><br/>
                    <Link to='/'>
                        <TeamsButton size="medium" variant="contained" color="secondary">
                            Return to Teams Page
                        </TeamsButton>
                    </Link>
                </div>
            </div>
            }
            {this.state.sub &&
                <SubscriptionList handleSubmit={this.handleChangeSubscription} tier={cookies.get('Role')}/>
            }
        </div>
        );
    }
}

export default SubscriptionError;
