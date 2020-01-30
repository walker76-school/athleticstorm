import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';

class Home extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <h1>Home</h1>
        );
    }
}

export default withRouter(Home);