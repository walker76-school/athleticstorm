import React, { Component } from 'react';
import { signup, checkUsernameAvailability } from '../../util/APIUtils';
import './Signup.css';
import { Link } from 'react-router-dom';
import { 
    USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH,
    PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH
} from '../../constants';

import { Form, Input, Button, notification } from 'antd';
import Subscription_List from "../subscriptions/subscription_list";
const FormItem = Form.Item;

class Signup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: {
                value: ''
            },
            password: {
                value: ''
            },
            roleName: {
                value: ''
            },
            renderSubscription: false
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.validateUsernameAvailability = this.validateUsernameAvailability.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
        this.handleNext = this.handleNext.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

        this.setState({
            [inputName] : {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(roleVal) {

        if(roleVal === 'None') {
            notification.error({
                message: 'Athletic Storm',
                description: 'Please select a subscription tier.'
            });
        }
        else {

            const signupRequest = {
                username: this.state.username.value,
                password: this.state.password.value,
                roleName: roleVal
            };
            signup(signupRequest)
                .then(response => {
                    notification.success({
                        message: 'Athletic Storm',
                        description: "Thank you! You're successfully registered. Please Login to continue!",
                    });
                    this.props.history.push("/login");
                }).catch(error => {
                notification.error({
                    message: 'Athletic Storm',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
            });
        }
    }

    handleNext(event) {
        this.setState({renderSubscription: true})
    }

    isFormInvalid() {
        return !(
            this.state.username.validateStatus === 'success' &&
            this.state.password.validateStatus === 'success'
        );
    }

    render() {
        return (
            <div>
                {!this.state.renderSubscription &&  <div className="signup-container">
                    <h1 className="page-title">Sign Up</h1>
                    <div className="signup-content">
                        <Form className="signup-form">
                            <FormItem label="Username"
                                hasFeedback
                                validateStatus={this.state.username.validateStatus}
                                help={this.state.username.errorMsg}>
                                <Input
                                    size="large"
                                    name="username"
                                    autoComplete="off"
                                    placeholder="A unique username"
                                    value={this.state.username.value}
                                    onBlur={this.validateUsernameAvailability}
                                    onChange={(event) => this.handleInputChange(event, this.validateUsername)} />
                            </FormItem>
                            <FormItem
                                label="Password"
                                validateStatus={this.state.password.validateStatus}
                                help={this.state.password.errorMsg}>
                                <Input
                                    size="large"
                                    name="password"
                                    type="password"
                                    autoComplete="off"
                                    placeholder="A password between 6 to 20 characters"
                                    value={this.state.password.value}
                                    onChange={(event) => this.handleInputChange(event, this.validatePassword)} />
                            </FormItem>
                            <FormItem>
                                <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    className="signup-form-button"
                                    disabled={this.isFormInvalid()}
                                    onClick={this.handleNext}>Next</Button>
                                Already registed? <Link to="/login">Login now!</Link>
                            </FormItem>
                        </Form>
                    </div>
                </div> }

                {this.state.renderSubscription &&
                    <Subscription_List handleSubmit={this.handleSubmit}/>
                        }
            </div>
        );
    }

    // Validation Functions

    validateUsername = (username) => {
        if(username.length < USERNAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters needed.)`
            }
        } else if (username.length > USERNAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: null,
                errorMsg: null
            }
        }
    };

    validateUsernameAvailability() {
        // First check for client side errors in username
        const usernameValue = this.state.username.value;
        const usernameValidation = this.validateUsername(usernameValue);

        if(usernameValidation.validateStatus === 'error') {
            this.setState({
                username: {
                    value: usernameValue,
                    ...usernameValidation
                }
            });
            return;
        }

        this.setState({
            username: {
                value: usernameValue,
                validateStatus: 'validating',
                errorMsg: null
            }
        });

        checkUsernameAvailability(usernameValue)
        .then(response => {
            if(response.available) {
                this.setState({
                    username: {
                        value: usernameValue,
                        validateStatus: 'success',
                        errorMsg: null
                    }
                });
            } else {
                this.setState({
                    username: {
                        value: usernameValue,
                        validateStatus: 'error',
                        errorMsg: 'This username is already taken'
                    }
                });
            }
        }).catch(error => {
            // Marking validateStatus as success, Form will be recchecked at server
            this.setState({
                username: {
                    value: usernameValue,
                    validateStatus: 'success',
                    errorMsg: null
                }
            });
        });
    }

    validatePassword = (password) => {
        if(password.length < PASSWORD_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters needed.)`
            }
        } else if (password.length > PASSWORD_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };            
        }
    }

}

export default Signup;