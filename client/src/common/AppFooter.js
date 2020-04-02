import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import Football from './FootballLogo.png'
import { Layout } from 'antd';
const Footer = Layout.Footer;

const style = {
    backgroundColor: 'white',
    borderTop: "1px solid #E7E7E7",
    textAlign: "center",
    padding: "20px",
    // position: "fixed",
    // left: "0",
    bottom: "0",
    // height: "60px",
    width: "100%",
};


class AppFooter extends Component {
    constructor(props) {
        super(props);
        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick({ key }) {
        if(key === "logout") {
            this.props.onLogout();
        }
    }

    render() {
        return (
            <Footer className="app-header" style={style}>
                <div className="container">
                    <div>
                        <Link to={'/'} style={{ padding: '20px' }}>About the Team</Link>
                        <a target="_blank" rel="noopener noreferrer" href={'https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm'} style={{ padding: '20px' }}>GitLab Page</a>
                        <Link to={'/'} style={{ padding: '20px' }}><img src={Football} alt="Athletic Storm alt logo"/></Link>
                        <Link to={'/'} style={{ padding: '20px' }}>Placeholder</Link>
                        <Link to={'/'} style={{ padding: '20px' }}>Placeholder</Link>
                    </div>
                </div>
            </Footer>
        );
    }
}

export default withRouter(AppFooter);