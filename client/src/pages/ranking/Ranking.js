import React, { Component } from 'react';
import '../../common/AppHeader.css';
import axios from 'axios';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import LoadingIndicator from "../../common/LoadingIndicator";
import Paper from "@material-ui/core/Paper";
import {makeStyles} from "@material-ui/core/styles";
import {Link} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";

const styles = makeStyles(theme => ({
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    }
}));

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
         
        }else{
            console.log("Invalid Option " + sortBy);
        }
    }

    componentDidMount() {
        
        axios.get('http://localhost:8080/api/coaches/allStats')
        .then(res => {
            this.setState({
                allRecords: res.data,
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

    render() {

        if(!this.state.loaded){
            return <LoadingIndicator/>
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
                                return (
                                    <Grid item xs={3} key={ndx}>
                                        <Link to={"/coach/" + record.firstName + " " + record.lastName}>
                                            <StyledPaper classes={this.props.classes}>
                                                <Typography>#{ndx+1}</Typography>
                                                <Typography><b>{record.firstName} {record.lastName}</b></Typography>
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