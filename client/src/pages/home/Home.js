import React, {Component} from "react";
import SchoolList from "./SchoolList";
import WelcomePage from "./WelcomePage";

export default class Home extends Component {

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
