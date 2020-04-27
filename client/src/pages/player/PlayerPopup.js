/*
*   Filename: PlayerPopup.js
*   Author: Andrew Walker
*   Date Last Modified: 4/18/2019
*/
import React, {Component} from 'react';
import { withStyles } from '@material-ui/core/styles';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import Content from "./Content";

//Formatting for dialog
const styles = (theme) => ({
    root: {
        margin: 0,
        padding: theme.spacing(2),
    },
    closeButton: {
        position: 'absolute',
        right: theme.spacing(1),
        top: theme.spacing(1),
        color: theme.palette.grey[500],
    },
});

const DialogTitle = withStyles(styles)((props) => {
    const { children, classes, onClose, ...other } = props;
    return (
        <MuiDialogTitle disableTypography className={classes.root} {...other}>
            <Typography variant="h6">{children}</Typography>
            {onClose ? (
                <IconButton aria-label="close" className={classes.closeButton} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
            ) : null}
        </MuiDialogTitle>
    );
});

const DialogContent = withStyles((theme) => ({
    root: {
        padding: theme.spacing(2),
    },
}))(MuiDialogContent);

export default class PlayerPopup extends Component {

    constructor(props) {
        super(props);

        this.state = {
            completed: false,
            playerData: null
        };

        this.setPlayerData = this.setPlayerData.bind(this);
    }

    setPlayerData(playerData){
        this.setState({
            playerData: playerData
        })
    }


    render() {

        let name = "";
        //Check that player data is there
        if(this.props.selectedPlayer !== null){
            name = this.props.selectedPlayer.first_name + " " + this.props.selectedPlayer.last_name + " (" + this.props.selectedPlayer.year + ")";
        }

        return (
            <Dialog onClose={this.props.handleClose} aria-labelledby="customized-dialog-title" open={this.props.open} fullWidth={true} maxWidth={"sm"}>
                <DialogTitle id="customized-dialog-title" onClose={this.props.handleClose}>
                    {name}
                </DialogTitle>
                <DialogContent dividers>
                    <Content selectedPlayer={this.props.selectedPlayer} setPlayerData={this.setPlayerData} onClose={this.props.handleClose}/>
                </DialogContent>
            </Dialog>
        );
    }
}