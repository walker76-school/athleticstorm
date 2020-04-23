import React, { Component } from 'react';
import {
    Link,
    withRouter
} from 'react-router-dom';
import './AppHeader.css';
import logo from './AthleticStormLogo.png'
import rankingIcon from '../logo.svg';
import { Layout, Menu, Dropdown, Icon } from 'antd';
import {ACCESS_TOKEN, SUBSCRIPTION_PLAYER_MAPPING, SUBSCRIPTION_TEAM_MAPPING} from "../constants";
import {getCurrentUser} from "../util/APIUtils";
import Cookies from 'universal-cookie';
const Header = Layout.Header;

const cookies = new Cookies();
    
class AppHeader extends Component {
    constructor(props) {
        super(props);   
        this.handleMenuClick = this.handleMenuClick.bind(this);
    }

    handleMenuClick({ key }) {
      if(key === "logout") {
        this.props.onLogout();
      }
      if(key === "subchange") {
          this.props.history.push("/SubscriptionError");
      }
    }

    render() {
        let menuItems;
        if(this.props.currentUser) {
          menuItems = [
            <Menu.Item key="/">
              <Link to="/">
                <Icon style={{ color: '#3773B0'}} type="home" className="nav-icon" />
              </Link>
            </Menu.Item>,
              <Menu.Item key="/ranking">
                  <Link to="/ranking">
                      <img style={{ color: '#3773B0'}} src={rankingIcon} alt="ranking" className="ranking-icon" />
                  </Link>
              </Menu.Item>,
            <Menu.Item key="/profile" className="profile-menu">
                <ProfileDropdownMenu 
                  currentUser={this.props.currentUser} 
                  handleMenuClick={this.handleMenuClick}/>
            </Menu.Item>
          ]; 
        } else {
            if(localStorage.getItem(ACCESS_TOKEN)) {
                getCurrentUser().then(response => {
                    console.log(response);
                    this.setState({
                        currentUser: response,
                        isAuthenticated: true,
                        isLoading: false
                    });
                    let numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[0]);
                    let numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[0]);
                    let role = this.state.currentUser.roleName[0];
                    if(!numTeams) {
                        numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[1]);
                        numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[1]);
                        role = this.state.currentUser.roleName[1];
                    }
                    console.log(numTeams);
                    cookies.set('Num_teams', numTeams, {path: '/'});
                    cookies.set('Teams_visited', [], {path: '/'});
                    cookies.set('Num_players', numPlayers,{path: '/'});
                    cookies.set('Role', role,{path: '/'});
                }).catch(error => {
                    console.log('error baby');
                    this.setState({
                        isLoading: false
                    });
                });
            }
          menuItems = [
            <Menu.Item key="/login">
              <Link to="/login">Login</Link>
            </Menu.Item>,
            <Menu.Item key="/signup">
              <Link to="/signup">Signup</Link>
            </Menu.Item>
        ];
        }

        return (
            <Header className="app-header">
            <div className="container">
              <div className="app-title" >
                  <a href="/">
                      <img src={logo} alt=""/>
                  </a>
              </div>
              <Menu
                className="app-menu"
                mode="horizontal"
                selectedKeys={[this.props.location.pathname]}
                style={{ lineHeight: '64px' }} >
                  {menuItems}
              </Menu>
            </div>
          </Header>
        );
    }
}

function ProfileDropdownMenu(props) {
  const dropdownMenu = (
    <Menu onClick={props.handleMenuClick} className="profile-dropdown-menu">
      <Menu.Item key="user-info" className="dropdown-item" disabled>
        <div className="username-info">
          @{props.currentUser.username}
        </div>
      </Menu.Item>
      <Menu.Divider />
        <Menu.Item key="subchange" className="dropdown-item">
            Change Subscription Plan
        </Menu.Item>
        <Menu.Divider />
        <Menu.Item key="logout" className="dropdown-item">
        Logout
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown 
      overlay={dropdownMenu} 
      trigger={['click']}
      getPopupContainer = { () => document.getElementsByClassName('profile-menu')[0]}>
      <a className="ant-dropdown-link">
         <Icon type="user" className="nav-icon" style={{marginRight: 0, color: '#3773B0'}} /> <Icon type="down" />
      </a>
    </Dropdown>
  );
}

export default withRouter(AppHeader);