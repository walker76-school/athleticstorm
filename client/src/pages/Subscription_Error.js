import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import '../common/AppHeader.css';


class SubscriptionError extends Component {

    constructor(props){
        super(props);

    }

    componentDidMount() {

    }

    render() {

        return(
        //Render error message
        <div>Error Message</div>
        );
    }
}

export default SubscriptionError;
