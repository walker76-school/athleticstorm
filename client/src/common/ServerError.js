/*
*   Filename: ServerError.js
*   Author: Andrew Walker
*   Date Last Modified: 4/13/2019
*/
import React, { Component } from 'react';
import './ServerError.css';
import { Link } from 'react-router-dom';
import { Button } from 'antd';

class ServerError extends Component {
    render() {
        //Server Error Page Creation
        return (
            <div className="server-error-page">
                <h1 className="server-error-title">
                    500
                </h1>
                <div className="server-error-desc">
                    Oops! Something went wrong at our Server. Why don't you go back?
                </div>
                <Link to="/"><Button className="server-error-go-back-btn" type="primary" size="large">Go Back</Button></Link>
            </div>
        );
    }
}

export default ServerError;