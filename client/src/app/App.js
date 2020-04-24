//Import Required Libraries
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

//Import All Pages
import Home from '../pages/home/Home';
import Coach from '../pages/coach/Coach';
import Ranking from '../pages/ranking/Ranking';
import Login from '../user/login/Login';
import School from "../pages/school/School";
import Signup from '../user/signup/Signup';
import AppHeader from '../common/AppHeader';
import AppFooter from '../common/AppFooter';
import Team from '../pages/home/Team';
import NotFound from '../common/NotFound';
import SubscriptionError from "../pages/Subscription_Error";
import LoadingIndicator from '../common/LoadingIndicator';
import {Layout, notification} from 'antd';
import { createMuiTheme } from '@material-ui/core/styles';
import {ThemeProvider} from "@material-ui/styles";
import PrivateRoute from "../common/PrivateRoute";
const {Content} = Layout;
const cookies = new Cookies();

//Formatting
const theme = createMuiTheme({
        palette: {
            primary: {
                main: '#3773B0'
            },
            info: {
                light: '#3773B0',
                main: '#3773B0',
                dark: '#3773B0'
            }
        }
    },
);

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null, //Username of logged in
            isAuthenticated: false, //Are they logged in
            isLoading: false,
            clickedSchool: null //School Selected
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
        console.log('starting');
        this.setState({
            isLoading: true
        });
        //Returns logged in user
        getCurrentUser()
            .then(response => {
                console.log('getcurr');
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
    //Logout Function
    handleLogout(redirectTo = "/", notificationType = "success", description = "You're successfully logged out.") {
        localStorage.removeItem(ACCESS_TOKEN);
        //Clear Cookies
        cookies.set('Num_teams', 0,{path: '/'});
        cookies.set('Teams_visited', [],{path: '/'});
        cookies.set('Num_players', 0,{path: '/'});
        cookies.set('Role', '',{path: '/'});

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

    handleLogin(redirectTo = "/") {
        notification.success({
            message: 'Athletic Storm',
            description: "You're successfully logged in.",
        });
        this.setState({
            isLoading: true
        });

        getCurrentUser().then(response => {
            console.log(response);
            this.setState({
                currentUser: response,
                isAuthenticated: true,
                isLoading: false
            });
            //Set subscription values
            let numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[0]);
            let numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[0]);
            let role = this.state.currentUser.roleName[0];
            //Check if number of teams is 0
            if(!numTeams) {
                numTeams = SUBSCRIPTION_TEAM_MAPPING.get(this.state.currentUser.roleName[1]);
                numPlayers = SUBSCRIPTION_PLAYER_MAPPING.get(this.state.currentUser.roleName[1]);
                role = this.state.currentUser.roleName[1];
            }
            console.log(numTeams);
            //Set Cookie values
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

        this.props.history.push(redirectTo);
    }

    setSchool(school) {
        this.setState({clickedSchool: school});
    }

    render() {
        if (this.state.isLoading) {
            return <LoadingIndicator/>
        }
        return (
            <ThemeProvider theme={theme}>
                <Layout className="app-container">
                <AppHeader isAuthenticated={this.state.isAuthenticated}
                           currentUser={this.state.currentUser}
                           onLogout={this.handleLogout}/>

                <Content className="app-content">
                    <div className="container">
                        {/* Set routes for different pages */}
                        <Switch>
                            <Route exact path="/" render={(props) => <Home isAuthenticated={this.state.isAuthenticated}  {...props} />}/>
                            <Route path="/signup" component={Signup}/>
                            <Route path="/login" render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>
                            <Route path="/team" component={Team}/>
                            <Route path="/SubscriptionError" render={(props) => <SubscriptionError {...props}/>}/>

                            <PrivateRoute exact path="/ranking">
                                <Ranking/>
                            </PrivateRoute>

                            <PrivateRoute exact path="/school/:schoolName">
                                <School/>
                            </PrivateRoute>

                            <PrivateRoute exact path="/coach/:coachName">
                                <Coach/>
                            </PrivateRoute>

                            <Route component={NotFound}/>
                        </Switch>
                    </div>
                </Content>
                <AppFooter/>
            </Layout>
            </ThemeProvider>
        );
    }
}

export default withRouter(App);
