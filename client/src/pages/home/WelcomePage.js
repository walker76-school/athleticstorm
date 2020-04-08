import React, {Component} from "react";
import {Link} from "react-router-dom";
import BleachersPic from "../bleachers.jpg";
import Button from "@material-ui/core/Button";

export default class WelcomePage extends Component {

    render() {
        return (
            <div>
                <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <div style={{ marginTop: '50px', backgroundImage: `url(${BleachersPic})`, height: '500px', width: '100%' }}>
                        <h1 style={{ marginTop: '100px', display: 'flex', justifyContent: 'center', color: 'white', fontSize: '80pt' }}>Athletic Storm</h1>
                        <h2 style={{ display: 'flex', justifyContent: 'center', color: 'white' }}>Always make the best hire</h2>
                        <h3 style={{ display: 'flex', justifyContent: 'center', color: 'white' }}>Filter through all of college football to find the right coach for you</h3>
                    </div>
                </div>
                <h3 style={{ marginTop: '50px' }}>Athletic Storm is a college football coach rating system that uses our [name of rating system] algorithm to objectively determine the best coaches in the FBS.</h3>
                <div style={{ margin: '20px', display: 'flex', justifyContent: 'center' }}>
                    <Link to={'/login'} style={{ margin: '20px', display: 'flex', justifyContent: 'center' }}>
                        <Button variant='contained' color='primary'>Log In</Button>
                    </Link>
                    <Link to={'/signup'} style={{ margin: '20px', display: 'flex', justifyContent: 'center' }}>
                        <Button variant='contained' color='primary'>Sign Up</Button>
                    </Link>
                </div>
                <div>
                    <h1 style={{ display: 'flex', justifyContent: 'center' }}>Coach rating system</h1>
                    <h3>(Michael/Shaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h3>
                </div>
            </div>
        );
    }
}
