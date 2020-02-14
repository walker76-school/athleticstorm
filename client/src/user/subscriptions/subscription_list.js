import React from 'react'
import Subscription from './subscription'
import tier1 from './images/tier1.jpg'
import tier2 from './images/tier2.jpg'
import tier3 from './images/tier3.jpg'

class Subscription_List extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            tiers: [{image: tier1, name: '1 star', price: '50', id: 1, description: 'test'},
                {image: tier2, name: '3 star', price: '50', id: 2, description: 'test'},
                {image: tier3, name: '5 star', price: '50', id: 3, description: 'test'}]
        };
    }

    render() {
        return(

        <div className="row mt-3">
            {this.state.tiers.map(tier =>
                <Subscription key={tier.id} id={tier.id} image={tier.image} name={tier.name} price={tier.price}
                              description={tier.description}/>
            )}
        </div>
        );
    }
}

export default Subscription_List