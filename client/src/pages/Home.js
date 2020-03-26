import React, {Component} from "react";
import axios from "axios";
import Grid from "@material-ui/core/Grid";
import {Link, withRouter} from "react-router-dom";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import withStyles from "@material-ui/core/styles/withStyles";
import {makeStyles} from "@material-ui/core/styles";

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

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    componentDidMount() {
    }

    render() {
        return (
            <h1>Hi</h1>
        );
    }
}

export default withStyles(styles)(withRouter(Home));
