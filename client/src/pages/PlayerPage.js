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

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

class PlayerPage extends React.Component<Player> {

    constructor(props) {
        super(props);
    }

    render() {
        const classes = useStyles;
        return (
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="Season Statistics">
                    <TableHead>
                    <TableRow>
                        <TableCell>Season Start Year</TableCell>
                        <TableCell>Season End Year</TableCell>
                        <TableCell align="right">School</TableCell>
                        <TableCell align="right">Position</TableCell>
                        <TableCell align="right">Start Ranking</TableCell>
                        <TableCell align="right">End Ranking</TableCell>
                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {this.props.history.map(row => (
                        <TableRow key={row.startSeason}>
                        <TableCell component="th" scope="row">
                            {row.startSeason}
                        </TableCell>
                        <TableCell >{row.endSeason}</TableCell>
                        <TableCell align="right">{row.school}</TableCell>
                        <TableCell align="right">{row.position}</TableCell>
                        <TableCell align="right">{row.protein}</TableCell>
                        <TableCell align="right">{row.startRanking}</TableCell>
                        <TableCell align="right">{row.endRanking}</TableCell>

                        </TableRow>
                    ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default withRouter(PlayerPage);