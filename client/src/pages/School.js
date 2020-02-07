import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';

class School extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <h1>School</h1>
        );
    }
}

export default withRouter(School);