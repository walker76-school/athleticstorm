/*
*   Filename: AppFooter.js
*   Author: John Eyre
*   Date Last Modified: 4/23/2019
*/
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Football from './FootballLogo.png'
import { Layout } from 'antd';
const Footer = Layout.Footer;
// Set formatting for footer
const style = {
    backgroundColor: 'white',
    borderTop: "1px solid #E7E7E7",
    textAlign: "center",
    padding: "10px",
    bottom: "0",
    height: "90px",
    width: "100%",
};


export default class AppFooter extends Component {
    render() {
        return (
            <Footer className="app-header" style={style}>
                <div className="container">
                    <div>
                        {/* Set Links for different pages */}
                        <Link to={'/team'} style={{ padding: '20px', color: '#3773B0' }}>Meet the Team</Link>
                        <Link to={'/'} style={{ padding: '20px' }}><img src={Football} alt="Athletic Storm alt logo"/></Link>
                        <a target="_blank" rel="noopener noreferrer" href={'https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm'} style={{ padding: '20px', color: '#3773B0' }}>GitLab Page</a>
                    </div>
                </div>
                <p>Data comes courtesy of <a href={"https://collegefootballdata.com/"} target={"_blank"} style={{color: '#3773B0'}}>CollegeFootballData</a></p>
            </Footer>
        );
    }
}
