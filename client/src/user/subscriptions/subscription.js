import React from 'react'
import './styles/subscription.css'


class Subscription extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {

        console.log(this.props.image);
        return (
        <div key={this.props.id} className="col-sm-6 col-md-4">
            <div className="subscription">
                <img src={this.props.image} alt={this.props.name}/>
                <div className="image_overlay"/>
                <div className="view_details" onClick={() => this.View_Subscription_Details(this.props.id)}>
                    Subscription Details
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

    View_Subscription_Details = (id) => {
        console.log(id);
    }

}

export default Subscription;