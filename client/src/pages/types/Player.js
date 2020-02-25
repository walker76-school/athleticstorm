import React, {Component} from 'react';
import LoadingIndicator from "../../common/LoadingIndicator";
import axios from 'axios';


// type PlayerType = {
//     name: string,
//     weight: number,
//     height: number,
//     age: int,
//     school: string,
//     position: string,
//     startRanking: number,
//     currentRanking: number,
//     overall: number,
//     pass: number,
//     rush: number,
//     firstDown: number,
//     secondDown: number,
//     thirdDown: number,
// };

export default class Player extends Component {

    constructor(props) {
        super(props);

        this.state = {
            completeBase: false,
            completeAdvanced: false,
            id: 0,
            personData: {
                name: "",
                weight: null,
                height: null,
                school: "",
                position: "",
                startRanking: null,
                currentRanking: null,
                overall: null,
                pass: null,
                rush: null,
                firstDown: null,
                secondDown: null,
                thirdDown: null
            }
        };
    }

    componentDidMount() {
        const { match: { params } } = this.props;
        
        axios.get(`https://api.collegefootballdata.com/player/search?searchTerm=${params.id}`)
            .then(res => {
                const p = res.data[0]
                console.log(JSON.stringify(p));
                this.setState({
                    id: p.id,
                    personData : {
                        name: p.name,
                        weight: p.weight,
                        height: p.height,
                        school: p.team,
                        position: p.position,
                        startRanking: null,
                        currentRanking: null
                    },
                    completeBase: true
                })
                axios.get(`https://api.collegefootballdata.com/player/usage?year=2019&playerId=${p.id}`)
                .then(res => {
                    if(res.data.length > 0){
                        const p = res.data[0];
                        const usage = p.usage;
                        this.setState({
                            personData: {
                                ...this.state.personData,
                                overall: usage.overall,
                                pass: usage.pass,
                                rush: usage.rush,
                                firstDown: usage.firstDown,
                                secondDown: usage.secondDown,
                                thirdDown: usage.thirdDown
                            },
                            completeAdvanced: true
                        })
                    }
                })
                })   
    }

    render() {
        const {name, weight, height, school, position, startRanking, currentRanking, overall, pass, rush, firstDown, secondDown, thirdDown} = this.state.personData
        if (this.state.completeBase){
            return  (
            <div className="stats">    
                <div className="base stats">
                    <h1> {name}</h1>
                    <p> Weight: {weight} pounds</p>
                    <p> Height: {height} inches</p>
                    <p> Team: {school}</p>
                    <p> Position: {position}</p>
                </div>
                {this.state.completeAdvanced && 
                (<div className="advanced stats">
                    <p> Overall: {overall}</p>
                    <p> Pass: {pass}</p>
                    <p> Rush: {rush}</p>
                    <p> First Down: {firstDown}</p>
                    <p> Second Down: {secondDown}</p>
                    <p> Third Down: {thirdDown}</p>
                    <p> Start Ranking: {startRanking}</p>
                    <p> Current Ranking: {currentRanking}</p>
                </div>)
                }
            </div>
        );
        }else{
            return <LoadingIndicator />
        }
    }
}