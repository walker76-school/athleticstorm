import React, { Component } from 'react';
import '../../common/AppHeader.css';
import Button from '@material-ui/core/Button';
import {Link} from "react-router-dom";

class SubscriptionError extends Component {

    render() {
        return(
            //Render error message
            <div>
                <br/>
                <div style={{
                    textAlign: 'center',
                    paddingTop: '150px',
                    paddingBottom: '150px',
                }}>
                    <div style={{fontSize: '40px', paddingBottom: '30px'}}>Access more content by upgrading your subscription!
                    </div>
                    <Link to='/changeSubscription'>
                        <Button style={{ backgroundColor: '#3773B0', color: "#ffffff"}} type="primary" htmlType="submit" size="large">
                            Update Your Subscription Plan
                        </Button>
                    </Link>
                    <br/><br/><br/>
                    <Link to='/'>
                        <Button style={{ backgroundColor: '#3773B0', color: "#ffffff"}} type="primary" size="large">
                            Return to Teams Page
                        </Button>
                    </Link>
                </div>
            </div>
        );
    }
}

export default SubscriptionError;
