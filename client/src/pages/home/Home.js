import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import AuthenticatedHome from "./AuthenticatedHome";
import UnauthenticatedHome from "./UnauthenticatedHome";

class Home extends Component {

    render() {
        if(this.props.isAuthenticated){
            return <AuthenticatedHome setSchool={this.props.setSchool}/>
        } else {
            return <UnauthenticatedHome />
        }
    }
}

export default withRouter(Home);