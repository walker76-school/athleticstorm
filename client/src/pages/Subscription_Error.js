import React, { Component } from 'react';
import '../common/AppHeader.css';
import Button from '@material-ui/core/Button';
import { styled } from '@material-ui/core/styles';
import {Link} from "react-router-dom";

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

class SubscriptionError extends Component {

    componentDidMount() {

    }

    render() {

        return(
        //Render error message
        <div>
            <br/>
            <div style={{textAlign: 'center', paddingTop: '150px', paddingBottom: '150px', border: '5px solid blue'}}>
                <div style={{fontSize: '40px', paddingBottom: '30px'}}>You must upgrade your subscription plan to view this team.</div>
                <SubscriptionButton size="medium" variant="contained" color="secondary">
                    Upgrade your subscription Plan
                </SubscriptionButton>
                <br/><br/><br/>
                <Link to='/'>
                    <TeamsButton size="medium" variant="contained" color="secondary">
                        Return to Teams Page
                    </TeamsButton>
                </Link>
            </div>
        </div>

        );
    }
}

export default SubscriptionError;
