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
            complete: false,
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
                    }
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
                            }
                        })
                    }
                    this.setState({complete: true})
                })
                })   
    }

    render() {
        const {name, weight, height, school, position, startRanking, currentRanking, overall, pass, rush, firstDown, secondDown, thirdDown} = this.state.personData
        if (this.state.complete){
            return  (
            <div className="stats">
                <h1> Name</h1>
                <p> {name}</p>
                <h1> Weight</h1>
                <p> {weight}</p>
                <h1> Height </h1>
                <p> {height}</p>
                <h1> Team </h1>
                <p> {school}</p>
                <h1> Position </h1>
                <p> {position}</p>
                <h1> Overall </h1>
                <p> {overall}</p>
                <h1> Pass </h1>
                <p> {pass}</p>
                <h1> Rush </h1>
                <p> {rush}</p>
                <h1> First down </h1>
                <p> {firstDown}</p>
                <h1> Second Down </h1>
                <p> {secondDown}</p>
                <h1> Third Down </h1>
                <p> {thirdDown}</p>
                <h1> Start Ranking </h1>
                <p> {startRanking}</p>
                <h1> Current Ranking </h1>
                <p> {currentRanking}</p>
            </div>
        );
        }else{
            return <LoadingIndicator />
        }
    }
}