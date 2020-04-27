/*
*   Filename: AdminRefresh.js
*   Author: Andrew Walker
*   Date Last Modified: 4/26/2019
*/
import React, {Component} from 'react';
import { withRouter} from 'react-router-dom';
import LoadingIndicator from "../../common/LoadingIndicator";
import {notification} from "antd";
import { refresh} from "./API";

class AdminRefresh extends Component {

    componentDidMount() {
        // Get coach name from url
        refresh()
        .then(result => {
            console.log(result);
            notification.info({
                message: 'Athletic Storm',
                description: "Refreshed data."
            });
            this.props.history.push('/');
        })
        .catch(error => {
            notification.error({
                message: 'Athletic Storm',
                description: "Couldn't refresh data."
            });
            this.props.history.push('/');
        })
    }

    render() {
        return (
            <LoadingIndicator />
        );
    }
}

export default withRouter(AdminRefresh);