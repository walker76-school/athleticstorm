import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import Player from "./types/Player"
import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

class PlayerPage extends React.Component<Player> {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div class="stats">
                <h1> Name</h1>
                <p> {this.props.name}</p>
                <h1> Weight</h1>
                <p> {this.props.weight}</p>
                <h1> height </h1>
                <p> {this.props.height}</p>
                <h1> Team </h1>
                <p> {this.props.team}</p>
                <h1> Position </h1>
                <p> {this.props.position}</p>
                <h1> Start Ranking </h1>
                <p> {this.props.startRanking}</p>
                <h1> Current Ranking </h1>
                <p> {this.props.currentRanking}</p>
            </div>
        );
    }
}

export default withRouter(PlayerPage);