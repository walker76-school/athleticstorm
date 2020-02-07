import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';

class Coach extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <h1>Coach</h1>
                <p>This page gives info about a coach in specific.</p>
            </div>
        );
    }
}

export default withRouter(Coach);