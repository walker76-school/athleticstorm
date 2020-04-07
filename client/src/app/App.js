import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import './App.css';
import {
  Route,
  withRouter,
  Switch
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import {ACCESS_TOKEN, SUBSCRIPTION_TEAM_MAPPING, SUBSCRIPTION_PLAYER_MAPPING} from '../constants';

import Home from '../pages/Home';
import SchoolList from '../pages/SchoolList';
import Coach from '../pages/Coach';
import Ranking from '../pages/Ranking';
import Login from '../user/login/Login';
import Player from "../pages/types/Player";
import School from "../pages/School";
import Signup from '../user/signup/Signup';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import SubscriptionError from "../pages/Subscription_Error";
import LoadingIndicator from '../common/LoadingIndicator';

import { Layout, notification } from 'antd';
import Subscriptions_Page from "../user/subscriptions/subscriptions_page";
import Subscription_List from "../user/subscriptions/subscription_list";
const { Content } = Layout;
const cookies = new Cookies();

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

    // Accomplishes the same task as loadCurrentUser, but only sets cookies when first logging in.
    // Otherwise, cookies would reload every time page is refreshed.
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
          let numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[0]);
          let numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[0]);
          if(!numTeams) {
            numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[1]);
            numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[1]);
          }
          console.log(numTeams);
          cookies.set('Num_teams', 1);
          cookies.set('Teams_visited', []);
          cookies.set('Num_players', numPlayers);
        }).catch(error => {
      this.setState({
        isLoading: false
      });
    });
    this.props.history.push("/schoollist");
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
                <Route exact path="/" component={Home} />
                <Route path="/signup" component={Signup} />
                <Route path="/login" render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>
                <Route path="/ranking" render={(props) => <Ranking isAuthenticated={this.state.isAuthenticated}  {...props} />}/>
                <Route path="/schoollist" render={(props) => <SchoolList isAuthenticated={this.state.isAuthenticated} {...props} />}/>
                  <Route path="/school/:schoolName" render={(props) => <School isAuthenticated={this.state.isAuthenticated}  {...props} />}/>
                <Route path="/SubscriptionError" render={(props) => <SubscriptionError {...props}/>}/>
                <Route path="/coach/:coachName" render={(props) => <Coach isAuthenticated={this.state.isAuthenticated} {...props} />}/>
                <Route path="/player/:id" render={(props) => <Player isAuthenticated={this.state.isAuthenticated} {...props} />}/>
                <Route component={NotFound}/>
              </Switch>
            </div>
          </Content>
        </Layout>
    );
  }
}

export default withRouter(App);
