import React from 'react'
import './styles/subscription.css'


class Subscription extends React.Component {

    constructor(props) {
        super(props);
        this.selectSubscription = this.selectSubscription.bind(this);

        this.state = {
            opacity: 0.0
        }
    }

    selectSubscription() {
        this.props.select(this.props.id);
    }

    render() {
        let newOpacity = 0.0;

        if (this.props.selected === true) {
            // this.setState({opacity: 0.0});
            newOpacity = 0.7;
        }

        return (
        <div key={this.props.id} className="col-sm-6 col-md-4">
            <div className="subscription">
                <img src={this.props.image} alt={this.props.name}/>
                <div className="image_overlay" style={{opacity: newOpacity}}/>
                <div className="view_details" onClick={this.selectSubscription}>
                    Select
                </div>
                <div className="stats">
                    <div className="stats-container">
                        <span className="tier_price">{this.props.price}</span>
                        <span className="tier_name">{this.props.name}</span>
                    </div>
                </div>
            </div>
        </div>
        )

    }

}

export default Subscription;