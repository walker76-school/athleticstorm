import React, { Component } from 'react';
import {
    withRouter
} from 'react-router-dom';
import SchoolList from "./SchoolList";

class AuthenticatedHome extends Component {
    render() {
        return (
            <div>
                <table>
                    <tbody>
                        <SchoolList onSchoolSelected={this.props.setSchool} />
                    </tbody>
                </table>
            </div>
        );
    }
}

export default withRouter(AuthenticatedHome);