import React, {Component} from "react";
import {Link} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import {makeStyles} from "@material-ui/core/styles";
import BleachersPic from "../bleachers.jpg";
import CoachPic from "../coachpic.jpg";
import Button from "@material-ui/core/Button";

const styles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    list: {
        marginTop: theme.spacing(2),
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        width: 80,
        height: 80,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    logo: {
        marginTop: theme.spacing(2),
        paddingTop: theme.spacing(2)

    }
}));

class WelcomePage extends Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount() {
    }

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

export default withStyles(styles)(WelcomePage);
