/*
*   Filename: Home.js
*   Author: Andrew Walker
*   Date Last Modified: 4/10/2019
*/
import React, {Component} from "react";
import SchoolList from "./SchoolList";
import WelcomePage from "./WelcomePage";

export default class Home extends Component {
    //Logic for handling home page, whether they are logged in or not
    render() {
        return (
            <div>
            {
                this.props.isAuthenticated ?
                    <SchoolList/>
                :
                    <WelcomePage />
            }
            </div>
        );
    }
}
