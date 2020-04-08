import React, {Component} from "react";
import {Link, withRouter} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import {makeStyles} from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import {Avatar} from "@material-ui/core";
import Andrew from "./profile pictures/andrew.jpg"
import Josh from "./profile pictures/josh.jpg"
import Evan from "./profile pictures/evan.jpg"
import Michael from "./profile pictures/michael.jpg"
import Schaeffer from "./profile pictures/schaeffer.jpg"
import John from "./profile pictures/john.jpg"
import Ian from "./profile pictures/ian.jpg"

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

class Team extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const { classes } = this.props;

        return (
            <div>
                <div style={{ textAlign: 'center' }}>
                    <h1 style={{ marginTop: '14px', fontSize: '80px' }}>Meet the Team</h1>
                </div>
                <div className={classes.root}>
                    <Grid container align="center" justify="center" alignItems="center" spacing={3} className={classes.list}>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Andrew} style={{ width: 100, height: 100 }}/>
                                <h3>Andrew Walker</h3>
                                <h4>Liaison</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Josh} style={{ width: 100, height: 100 }}/>
                                <h3>Josh Pane</h3>
                                <h4>Deputy</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Evan} style={{ width: 100, height: 100 }}/>
                                <h3>Evan Baker</h3>
                                <h4>Member</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Michael} style={{ width: 100, height: 100 }}/>
                                <h3>Michael Coffey</h3>
                                <h4>Member</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Schaeffer} style={{ width: 100, height: 100 }}/>
                                <h3>Schaeffer Duncan</h3>
                                <h4>Member</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={John} style={{ width: 100, height: 100 }}/>
                                <h3>John Eyre</h3>
                                <h4>Member</h4>
                            </Paper>
                        </Grid>
                        <Grid item xs={3}>
                            <Paper className={classes.paper}>
                                <Avatar src={Ian} style={{ width: 100, height: 100 }}/>
                                <h3>Ian Laird</h3>
                                <h4>Member</h4>
                            </Paper>
                        </Grid>
                    </Grid>
                </div>

            </div>
        );
    }
}

export default withStyles(styles)(withRouter(Team));
