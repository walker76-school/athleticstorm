import React from 'react'
import Subscription from './Subscription'
import './styles/comparisons.css'
import tier1 from './images/tier1.jpeg'
import tier2 from './images/tier2.jpg'
import tier3 from './images/tier3.jpg'
import {Button} from "antd";
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import {withRouter} from "react-router-dom";

class SubscriptionList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            tiers: [{image: tier1, name: 'Redshirt', price: '$4.99/month', id: 'ROLE_REDSHIRT', numTeams: 10, numPlayers: 5, years: 'Current Year', adv: false},
                {image: tier2, name: 'Starter', price: '$7.49/month', id: 'ROLE_STARTER', numTeams: 20, numPlayers: 10, years: 'All', adv: true},
                {image: tier3, name: 'MVP', price: '$9.99/month', id: 'ROLE_MVP', numTeams: 'All', numPlayers: 'All', years: 'All', adv: true}],
            selectedTier: this.props.tier
        };

        this.select = this.select.bind(this);
        this.handleSubmitPress = this.handleSubmitPress.bind(this);
    }

    select(value) {
        this.setState({selectedTier: value});
    }

    handleSubmitPress() {
        this.props.handleSubmit(this.state.selectedTier);
    }

    render() {

        return(

        <div className="home mt-5">
            <div className="row text-center">
                <div className="col-12">
                    <h2 className="mb-3">Compare Subscription Tiers</h2>
                </div>
            </div>

                <div className="row mt-3">
                    {this.state.tiers.map(tier =>
                        <Subscription key={tier.id} id={tier.id} image={tier.image} name={tier.name} price={tier.price}
                                      description={tier.description} select={this.select} selected={this.state.selectedTier === tier.id}/>
                    )}
                    <div className="row product_comparisons">
                        <div className="col-12 mt-5 text-center">
                            <table className="table">
                                <thead className="thead-default">
                                <tr>
                                    <th />
                                    {this.state.tiers.map(tier =>
                                        <th key={tier.id}>
                                            {tier.name}
                                        </th>
                                    )}
                                </tr>
                                </thead>
                                <tbody>
                                <tr className="price">
                                    <th scope="row">Price</th>
                                    {this.state.tiers.map(tier =>
                                        <td key={tier.id} className="text-center">{tier.price}</td>
                                    )}
                                </tr>
                                <tr className="description">
                                    <th scope="row">Access to coach rating</th>
                                    {this.state.tiers.map(tier =>
                                        <td key={tier.id}>
                                            {tier.adv && <CheckCircleIcon color="primary"/>}
                                        </td>
                                    )}
                                </tr>
                                <tr className="description">
                                    <th scope="row">Number of Teams Accessible</th>
                                    {this.state.tiers.map(tier =>
                                        <td key={tier.id}>
                                            {tier.numTeams}
                                        </td>
                                    )}
                                </tr>
                                <tr className="description">
                                    <th scope="row">Number of Players Accessible per Team</th>
                                    {this.state.tiers.map(tier =>
                                        <td key={tier.id}>
                                            {tier.numPlayers}
                                        </td>
                                    )}
                                </tr>
                                <tr className="description">
                                    <th scope="row">Years viewable for players</th>
                                    {this.state.tiers.map(tier =>
                                        <td key={tier.id}>
                                            {tier.years}
                                        </td>
                                    )}
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

            <Button type="primary"
                    size="large"
                    className="signup-form-button"
                    onClick={this.handleSubmitPress}>Submit</Button>
            <br/><br/><br/>
        </div>
        );
    }
}

export default withRouter(SubscriptionList);