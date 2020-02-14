import React from 'react'

class Subscription extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
        <div key={this.props.tier.id} className="col-sm-6 col-md-3">
            <div className="subscription">
                <img src={this.props.tier.image} alt={this.props.tier.name}/>
                <div className="image_overlay"/>
                <div className="view_details" onClick={() => this.View_Subscription_Details(this.props.tier.id)}>
                    Subscription Details
                </div>
                <div className="stats">
                    <div className="stats-container">
                        <span className="product_price">{this.props.tier.price}</span>
                        <span className="product_name">{this.props.tier.name}</span>
                        <p>{this.props.tier.description}</p>
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