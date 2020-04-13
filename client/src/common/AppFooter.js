import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Football from './FootballLogo.png'
import { Layout } from 'antd';
const Footer = Layout.Footer;

const style = {
    backgroundColor: 'white',
    borderTop: "1px solid #E7E7E7",
    textAlign: "center",
    padding: "10px",
    bottom: "0",
    height: "70px",
    width: "100%",
};


export default class AppFooter extends Component {
    render() {
        return (
            <Footer className="app-header" style={style}>
                <div className="container">
                    <div>
                        <Link to={'/team'} style={{ padding: '20px' }}>Meet the Team</Link>
                        <Link to={'/'} style={{ padding: '20px' }}><img src={Football} alt="Athletic Storm alt logo"/></Link>
                        <a target="_blank" rel="noopener noreferrer" href={'https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm'} style={{ padding: '20px' }}>GitLab Page</a>
                    </div>
                </div>
            </Footer>
        );
    }
}
