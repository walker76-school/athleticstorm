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
    }

    render() {
        return (
            <div className="home mt-5">
                <div className="row text-center">
                    <div className="col-12">
                        <h2 className="mb-3">Compare Subscription Tiers</h2>
                    </div>
                </div>
                <Subscription_List/>
            </div>
        )
    }
}

export default Subscriptions_Page;
