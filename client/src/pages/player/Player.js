import React, {Component} from 'react';
import LoadingIndicator from "../../common/LoadingIndicator";
import axios from 'axios';

export default class Player extends Component {

    constructor(props) {
        super(props);

        this.state = {
            completed: false,
            playerData: null
        };
    }

    componentDidMount() {

        axios.post("http://localhost:8080/api/player/getStats/", this.props.location.state)
        .then(result => {
            this.setState({
                completed: true,
                personData: result.data,
            });
        })
    }

    render() {
        if (this.state.completed){
            return  (
                <div style={{"text-align": "center"}}>
                    <div>
                        <h1 style={{marginTop: 14, fontSize: 80}} >{this.state.personData.player.firstName + " " + this.state.personData.player.lastName} ({this.props.location.state.year})</h1>
                        <h2> Weight: {this.state.personData.player.weight} pounds</h2>
                        <h2> Height: {this.state.personData.player.height} inches</h2>
                        <h2> Team: {this.state.personData.player.team}</h2>
                        <h2> Position: {this.state.personData.player.position}</h2>
                    </div>
                    {this.state.personData.hasAdvancedPlayer &&
                        (<div>
                            <h2> Overall: {this.state.personData.advancedPlayer.usage.overall}</h2>
                            <h2> Pass: {this.state.personData.advancedPlayer.usage.pass}</h2>
                            <h2> Rush: {this.state.personData.advancedPlayer.usage.rush}</h2>
                            <h2> First Down: {this.state.personData.advancedPlayer.usage.firstDown}</h2>
                            <h2> Second Down: {this.state.personData.advancedPlayer.usage.secondDown}</h2>
                            <h2> Third Down: {this.state.personData.advancedPlayer.usage.thirdDown}</h2>
                            {/*<h2> Start Ranking: {this.state.personData.advancedPlayer.startRanking}</h2>*/}
                            {/*<h2> Current Ranking: {this.state.personData.advancedPlayer.currentRanking}</h2>*/}
                        </div>)
                    }
                </div>
            );
        } else {
            return <LoadingIndicator />
        }
    }
}