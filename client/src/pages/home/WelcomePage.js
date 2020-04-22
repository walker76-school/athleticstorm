import React, {Component} from "react";
import {Link} from "react-router-dom";
import BleachersPic from "./bleachers.jpg";
import CoachPic from "./coachpicture.jpg";
import Button from "@material-ui/core/Button";

export default class WelcomePage extends Component {

    render() {
        return (
            <div>
                <div style={{ display: 'flex', justifyContent: 'center' }}>
                    <div style={{ backgroundImage: `url(${BleachersPic})`, backgroundPosition: 'center center', backgroundAttachment: 'fixed', backgroundSize: 'auto', backgroundRepeat: 'no-repeat', width: '100%' }}>
                        <h1 style={{ marginTop: '200px', display: 'flex', justifyContent: 'center', color: 'white', fontSize: '80pt' }}>Athletic Storm</h1>
                        <h2 style={{ display: 'flex', justifyContent: 'center', color: 'white' }}>Always make the best hire</h2>
                        <h3 style={{ marginBottom: '200px', display: 'flex', justifyContent: 'center', color: 'white' }}>Filter through all of college football to find the right coach for you</h3>
                    </div>
                </div>

                <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'center' }}>
                    <h5>Sign up for a subscription tier to view coach ratings!</h5>
                </div>
                <Link to={'/signup'} style={{ margin: '20px', display: 'flex', justifyContent: 'center' }}>
                    <Button size='large' variant='contained' color='primary'>Sign Up</Button>
                </Link>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>Athletic Storm is a college football coach rating system that uses our [name of rating system] algorithm to objectively determine the best coaches in the FBS.</h5>

                <div style={{ marginTop: '25px', backgroundImage: `url(${CoachPic})`, backgroundPosition: 'center center', backgroundAttachment: 'fixed', backgroundSize: 'auto', backgroundRepeat: 'no-repeat', width: '100%' }}>
                    <h1 style={{ paddingLeft: '50px', paddingTop: '200px', paddingBottom: '200px' }}>Coach rating system</h1>
                </div>

                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
                <h5 style={{ display: 'flex', justifyContent: 'center' }}>(Michael/Schaeffer can fill this part out more specifically than I can) - Make it sensational and buzzwordy</h5>
            </div>
        );
    }
}
