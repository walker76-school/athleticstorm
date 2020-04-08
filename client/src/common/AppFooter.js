import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import Football from './FootballLogo.png'
import { Layout } from 'antd';
import {makeStyles} from "@material-ui/core/styles";
import withStyles from "@material-ui/core/styles/withStyles";
const Footer = Layout.Footer;

const styles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    list: {
        marginTop: theme.spacing(2),
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        width: 80,
        height: 80,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    logo: {
        marginTop: theme.spacing(2),
        paddingTop: theme.spacing(2)

    }
}));


const style = {
    backgroundColor: 'white',
    borderTop: "1px solid #E7E7E7",
    textAlign: "center",
    padding: "10px",
    // position: "fixed",
    // left: "0",
    bottom: "0",
    height: "70px",
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
                        <Link to={'/team'} style={{ padding: '20px' }}>Meet the Team</Link>
                        <Link to={'/'} style={{ padding: '20px' }}><img src={Football} alt="Athletic Storm alt logo"/></Link>
                        <a target="_blank" rel="noopener noreferrer" href={'https://gitlab.ecs.baylor.edu/aars/20200143c9-athleticstorm'} style={{ padding: '20px' }}>GitLab Page</a>
                    </div>
                </div>
            </Footer>
        );
    }
}

export default withStyles(styles)(withRouter(AppFooter));
