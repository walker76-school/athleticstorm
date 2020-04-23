import React, { Component } from 'react';
import '../../common/AppHeader.css';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LoadingIndicator from "../../common/LoadingIndicator";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import {Link} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import {notification} from "antd";
import {Avatar} from "@material-ui/core";
import LockIcon from "../../common/LockIcon.png";
import Cookies from 'universal-cookie';
import {getCoachRankings} from "./API";
import Button from "@material-ui/core/Button";

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

class Ranking extends Component {

    constructor(props) {
        super(props);
        this.state = {
            allRecords: [],
            loaded: false
        };
        this.headcoachSort = this.headcoachSort.bind(this);
    }

    headcoachSort(event){
        let sortBy = event.target.value;
        if("Alphabetical" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => a.lastName > b.lastName ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Win %" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => a.winPercentage < b.winPercentage ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Most Wins" === sortBy){
            const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => parseInt(a.wins,10) < parseInt(b.wins,10) ? 1 : -1);
            this.setState({ allRecords: sortedCoaches });
        }else if("Coach Grade" === sortBy){
            if(cookies.get('Role') !== 'ROLE_REDSHIRT') {
                const sortedCoaches = [].concat(this.state.allRecords).sort((a, b) => a.rating < b.rating ? 1 : -1);
                this.setState({ allRecords: sortedCoaches });
            } else{
                notification.error({
                    message: 'Athletic Storm',
                    description: 'Upgrade your subscription to use ratings.'
                });
            }
         
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    componentDidMount() {
        
        getCoachRankings()
        .then(res => {
            this.setState({
                allRecords: res,
                loaded: true
            });
        })
        .catch(error => {
            this.setState({
                allRecords: [],
                loaded: true
            });
        });
    }

    perc2color(perc) {
        var r, g, b = 0;
        if(perc < 50) {
            r = 255;
            g = Math.round(5.1 * perc);
        }
        else {
            g = 255;
            r = Math.round(510 - 5.10 * perc);
        }
        var h = r * 0x10000 + g * 0x100 + b;
        return '#' + ('000000' + h.toString(16)).slice(-6);
    }

    render() {

        const {classes} = this.props;

        if(!this.state.loaded){
            return <LoadingIndicator/>
        }

        if(this.state.allRecords.length === 0){
            return (
                <div style={{"text-align": "center"}}>
                    <h1>Couldn't load data for coach rankings.</h1>
                    <Link to='/'>
                        <Button size="medium" variant="contained" color="secondary">
                            Return to Teams Page
                        </Button>
                    </Link>
                </div>
            );
        }

        return (
            <div style={{flexGrow: 1}} >
                <br />
                <h1 style={{ backgroundColor: "#3773B0", color: "#ffffff" }}>&nbsp;Rankings
                        <select style={{ float: 'right', color: "#3773B0" }} onChange={this.headcoachSort}>
                            <option value="Alphabetical">Alphabetical</option>
                            <option value="Win %">Win %</option>
                            <option value="Most Wins">Most Wins</option>
                            <option value="Coach Grade">Coach Grade</option>
                        </select>
                    </h1>
                <Grid container align="center" justify="center" alignItems="center" spacing={3} >
                    {
                        this.state.allRecords.length > 0 ?
                            this.state.allRecords.map((record, ndx) => {

                                let ratingContent = <Typography><b>{record.first_name} {record.last_name}</b>(<span style={{color: record.rating === -1 ? "#000000" : this.perc2color(record.rating)}}>{record.rating === -1 ? "--" : record.rating.toFixed(2)}</span>)</Typography>;
                                if(cookies.get('Role') === 'ROLE_REDSHIRT') {
                                    ratingContent = <Typography><b>{record.first_name} {record.last_name}</b><Avatar className={classes.logo} src={LockIcon}/></Typography>;
                                }

                                return (
                                    <Grid item xs={3} key={ndx}>
                                        <Link to={"/coach/" + record.first_name + " " + record.last_name}>
                                            <StyledPaper classes={this.props.classes}>
                                                <Typography>#{ndx+1}</Typography>
                                                {ratingContent}
                                                <Typography><b>Wins:</b> {record.wins} <b>Losses:</b> {record.losses}</Typography>
                                            </StyledPaper>
                                         </Link>
                                    </Grid>
                                );
                            })
                            :
                            <Typography>
                               No coaches loaded.
                            </Typography>
                    }
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(Ranking);

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