/*
*   Filename: CoordinatorContent.js
*   Author: Andrew Walker
*   Date Last Modified: 4/26/2019
*/
import React, {Component} from 'react';
import LoadingIndicator from "../../common/LoadingIndicator";
import {notification} from "antd";
import {getCoordinatorByName} from "./API";
import Cookies from 'universal-cookie';
import LockIcon from "../../common/LockIcon.png";

const cookies = new Cookies();

export default class CoordinatorContent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            completed: false,
            coordinatorData: null
        };
    }

    componentDidMount() {

        //Storing coordinator information from API
        getCoordinatorByName(this.props.selectedCoordinator.name)
        .then(result => {
            console.log(result);
            this.setState({
                completed: true,
                coordinatorData: result
            }, () => {
                this.props.setCoordinatorData(result);
            });
        })
        .catch(error => {
            notification.error({
                message: 'Athletic Storm',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
            this.props.onClose();
        })
    }

    //Conversion to get hash of color
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

        if (this.state.completed) {
            // Handle player information not being found
            if(this.state.coordinatorData === null){
                return (
                    <div style={{"text-align": "center"}}>
                        <h1>Couldn't load data for {this.props.selectedCoordinator.name}.</h1>
                    </div>
                );
            }

            //Handling insufficient subscription
            let ratingContent = <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.coordinatorData.name} (<span style={{color: this.state.coordinatorData.rating === -1 ? "#000000" : this.perc2color(this.state.coordinatorData.rating)}}>{this.state.coordinatorData.rating === -1 ? "--" : this.state.coordinatorData.rating.toFixed(2)}</span>)</h1>;
            if(cookies.get('Role') === 'ROLE_REDSHIRT') {
                ratingContent = <h1 style={{marginTop: 14, fontSize: 80}}>{this.state.coordinatorData.name} <br/> (<img alt="Lock" style={{maxHeight: "75px"}} src={LockIcon} onClick={() => {
                    notification.error({
                        message: 'Athletic Storm',
                        description: 'Upgrade your subscription to see ratings.'
                    });
                }}/>)</h1>
            }

            return (
                // Create player popup
                <div style={{"textAlign": "center"}}>
                    <div style={{"text-align": "center"}}>
                        {ratingContent}
                    </div>
                </div>
            );
        } else {
            return (
                <div>
                    <LoadingIndicator/>
                    <br/>
                </div>
            );

        }
    }
}