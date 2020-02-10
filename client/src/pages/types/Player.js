import type {Person} from "Person";
import PlayerPage from "../../PlayerPage";
import LoadingIndicator from "../../common/LoadingIndicator";
import { boolean, object } from "flow-validator"

type PlayerType = {
    name: string,
    weight: number,
    height: number,
    age: int,
    school: string,
    position: string,
    startRanking: number,
    currentRanking: number
};

export class Player extends Component {

    constructor(props) {
        super(props);
        this.state = {complete: false};
    }

    componentDidMount() {
        fetch('https://api.collegefootballdata.com/player/usage?year=2019&playerId=' + this.props.id)
            .then(res => res.json())
            .then(res -> Person.parse(res))
            .then(res => this.setState(personData : res))
            .then(this.setState(complete:true))
        console.log("state", this.state.coach_first_name);
    }

    render() {
        this.state.true && return <PlayerPage {...this.state.personData} />
    }
}