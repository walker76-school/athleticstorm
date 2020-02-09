import * as React from 'react';
import {
    withRouter
} from 'react-router-dom';
import '../common/AppHeader.css';
import Player from "../types/Player"

class PlayerPage extends React.Component<Player> {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <h1>Player</h1>
                <p>This page gives specific info about a player</p>
            </div>
        );
    }
}

export default withRouter(Coach);