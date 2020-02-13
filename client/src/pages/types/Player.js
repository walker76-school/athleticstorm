import type {Person} from "Person";
import React, {Component} from 'react';
import {
    withRouter,
    useParams
} from 'react-router-dom';
import LoadingIndicator from "../../common/LoadingIndicator";
import axios from 'axios';


type PlayerType = {
    name: string,
    weight: number,
    height: number,
    age: int,
    school: string,
    position: string,
    startRanking: number,
    currentRanking: number,
    overall: number,
    pass: number,
    rush: number,
    firstDown: number,
    secondDown: number,
    thirdDown: number,
};

export default class Player extends Component {

    constructor(props) {
        super(props);

        this.state = {
            complete: false,
            id: 0,
            personData: null
        };
    }

    componentDidMount() {
        axios.get('https://api.collegefootballdata.com/player/search?searchTerm=' + this.props.name)
            .then(res => {
                const p = res.data[0]
                this.setState({
                    id: this.props.match.params.id,
                    personData : {
                        name: p.name,
                        weight: p.weight,
                        height: p.height,
                        school: p.team,
                        startRanking: null,
                        currentRanking: null
                    }
                })
                })
            .then(res =>{
                axios.get('https://api.collegefootballdata.com/player/usage?year=2019&playerId=' + this.state.id)
                .then(res => {
                    const p = res.data[0]
                    this.setState({
                        personData: {
                            overall: p.overall,
                            pass: p.pass,
                            rush: p.rush,
                            firstDown: p.firstDown,
                            secondDown: p.secondDown,
                            thirdDown: p.thirdDown
                        }
                    })
                })
            })    
            .then(this.setState({complete:true}))

        console.log("state", this.state.coach_first_name);
    }

    render() {
        if (this.state.complete){
            return  (
            <div class="stats">
                <h1> Name</h1>
                <p> {this.props.name}</p>
                <h1> Weight</h1>
                <p> {this.props.weight}</p>
                <h1> Height </h1>
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
}