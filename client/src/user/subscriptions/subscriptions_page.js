import React from "react";
import makeStyles from "@material-ui/core/styles/makeStyles";
import Subscription_List from "./subscription_list";

const useStyles = makeStyles({
    root: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
});

class Subscriptions_Page extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tiers: [{image: '/client/src/images/Logo.png', title: '1 star', price: '50', id: 1},
                    {image: '/client/src/images/Logo.png', title: '3 star', price: '50', id: 2},
                    {image: '/client/src/images/Logo.png', title: '5 star', price: '50', id: 3}]
        };
    }

    render() {

        return (
            <div className="home mt-5">
                <div className="row text-center">
                    <div className="col-12">
                        <h2 className="mb-3">Compare Products</h2>
                    </div>
                </div>
                <Subscription_List tiers={this.state.tiers}/>
            </div>
        )
    }
}

export default Subscriptions_Page;
