import React from 'react'
import Subscription from './subscription'

const Subscription_List = ({tiers}) =>
    <div className="row mt-3">
        {tiers.map(tier =>
            <Subscription key={tier.id} product={tier}/>
        )}
        {tiers}
    </div>;

export default Subscription_List