import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';

import Home from '../pages/home/Home';
import Coach from '../pages/Coach';
import Login from '../user/login/Login';
import Player from "../pages/types/Player";
import School from "../pages/School";
import Signup from '../user/signup/Signup';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';

import { Layout, notification } from 'antd';
const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      clickedSchool: null
    };

    this.setSchool = this.setSchool.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });    
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
    .then(response => {
      this.setState({
        currentUser: response,
        isAuthenticated: true,
        isLoading: false
      });
    }).catch(error => {
      this.setState({
        isLoading: false
      });  
    });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    
    notification[notificationType]({
      message: 'Athletic Storm',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'Athletic Storm',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/");
  }

  setSchool(school){
    this.setState({ clickedSchool: school});
  }

  render() {
    if(this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
        <Layout className="app-container">
          <AppHeader isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout} />

          <Content className="app-content">
            <div className="container">
              <Switch>
                <Route exact path="/" render={(props) => <Home setSchool={this.setSchool} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}/>
                <Route path="/login" render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>
                <Route path="/signup" component={Signup} />
                <Route path="/school/:schoolName" render={(props) => <School school={this.state.clickedSchool} isAuthenticated={this.state.isAuthenticated}  currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}/>
                <Route path="/coach" render={(props) => <Coach isAuthenticated={this.state.isAuthenticated} 
                  currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}/>
                <Route path="/player/:id" render={(props) => <Player isAuthenticated={this.state.isAuthenticated} 
                  currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}/>
                <Route component={NotFound}/>
              </Switch>
            </div>
          </Content>
        </Layout>
    );
  }
}

export default withRouter(App);
