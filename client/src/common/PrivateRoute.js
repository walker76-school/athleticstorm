import React, {Component} from 'react';
import {
    Route,
    Redirect
  } from "react-router-dom";
import {ACCESS_TOKEN} from "../constants";

export default class PrivateRoute extends Component {

    render() {
        return (
            <Route
                {...this.props}
                render={() => {
                    return localStorage.getItem(ACCESS_TOKEN) !== null ? (
                        this.props.children
                    ) : (
                        <Redirect to="/login"/>
                    )
                }
                }
            />
        );
    }
}