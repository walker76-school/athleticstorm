/*
*   Filename: SchoolList.js
*   Author: Joshua Pane
*   Date Last Modified: 4/23/2019
*/
import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import '../../common/AppHeader.css';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import {Avatar} from "@material-ui/core";
import Cookies from 'universal-cookie';
import LockIcon from '../../common/LockIcon.png';
import {notification} from "antd";
import LoadingIndicator from "../../common/LoadingIndicator";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";
import {getFBSTeams} from "./API";

//Create formatting for the schools
const styles = makeStyles(theme => ({
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    logo: {
        marginTop: theme.spacing(2),
        paddingTop: theme.spacing(2),
        opacity: 0.1
    }
}));

const cookies = new Cookies();

class SchoolList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            teams: [],
            allTeams: [],
            loaded: false
        };
        this.filterTeams = this.filterTeams.bind(this);
    }

    componentDidMount() {
        // Get List Of FBS Teams From API
        getFBSTeams()
        .then(res => {
            console.log(res);
            this.setState({
                teams: res,
                allTeams: res,
                loaded: true
            });
        })
        .catch(error => {
            this.setState({
                loaded: true
            });
        });
    }
    //Handle max teams for a subscription are already clicked
    onClickLock() {
        notification.error({
            message: 'Athletic Storm',
            description: 'You must upgrade your subscription to access more teams.'
        });
    }
    //Filter for teams when they are being searched for
    filterTeams(filter){
        var tempTeams = [];
        for( var x = 0; x < this.state.allTeams.length; x++){
            if(this.state.allTeams[x].school.toLocaleLowerCase().includes(filter.toLocaleLowerCase())){
             tempTeams.push(this.state.allTeams[x]);
            }
        }
        this.setState({ teams: tempTeams });
    }

    render() {
        const { classes } = this.props;
        //Loading screen
        if(!this.state.loaded){
            return <LoadingIndicator/>
        }

        if(this.state.teams.length === 0){
            return (
                <div style={{"text-align": "center"}}>
                    <h1>Couldn't load data for teams.</h1>
                </div>
            );
        }

        return (
            <div style={{flexGrow: 1}} >
                <br/>
                {/* Create search bar */}
                <div style={{ display: 'flex' }}>
                    <input type="text" placeholder="Search" onChange={(event) => {this.filterTeams(event.target.value)}}/>

                </div>
                <br/>
                <h6>* indicates a viewed team {cookies.get('Role') !== 'ROLE_MVP' ? "(" + cookies.get('Num_teams') + " remaining)" : ""}</h6>
                <Grid container align="center" justify="center" alignItems="center" spacing={3} >
                    {
                        this.state.teams.length > 0 ?
                            this.state.teams.map((team, ndx) => {

                                let unlocked = (cookies.get('Num_teams') > 0 || (cookies.get('Teams_visited') != null && cookies.get('Teams_visited').find(element => element === team.school)));

                                let visited = '';
                                if (cookies.get('Teams_visited') != null && cookies.get('Teams_visited').find(element => element === team.school)) {
                                    visited = '*';
                                }

                                return (
                                    <Grid item md={3} xs={12}>
                                        { unlocked &&
                                            <Link
                                                to={{
                                                    pathname: '/school/' + team.school,
                                                    state: {
                                                        teamId: team.id
                                                    }
                                                }}
                                                style={{color: this.state.primaryColor}}
                                            >
                                                <StyledPaper classes={classes}>
                                                    <Avatar className={classes.logo} src={team.logos[0]}/>
                                                    <Typography>{team.school + visited}</Typography>
                                                </StyledPaper>
                                            </Link>
                                        }
                                        {!unlocked &&
                                            <div style={{cursor:'pointer'}} onClick={this.onClickLock}>
                                                <StyledPaper classes={classes}>
                                                    <Avatar className={classes.logo} src={LockIcon}/>
                                                    <Typography>{team.school + visited}</Typography>
                                                </StyledPaper>
                                            </div>
                                        }
                                    </Grid>
                                );
                            })
                        :
                            <Typography>
                                No teams are available at this time. Please check in later.
                            </Typography>
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(SchoolList);

// In depth styling for the list of schools
class StyledPaper extends Component {
    constructor(props){
        super(props);
        this.state = {
            elevation: 1
        };

        this.onMouseOver = this.onMouseOver.bind(this);
        this.onMouseOut = this.onMouseOut.bind(this);
    }

    onMouseOver(){
        this.setState({ elevation: 5 });
    }

    onMouseOut(){
        this.setState({ elevation: 1 });
    }

    render() {

        const {classes} = this.props;

        return (
            <Paper
                onMouseOver={this.onMouseOver}
                onMouseOut={this.onMouseOut}
                elevation={this.state.elevation}
                square={true}
                className={classes.paper}
            >
                {this.props.children}
            </Paper>
        );
    }
}