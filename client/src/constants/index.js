export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'https://localhost:8443/api';
export const ACCESS_TOKEN = 'accessToken';

export const USERNAME_MIN_LENGTH = 3;
export const USERNAME_MAX_LENGTH = 15;

export const PASSWORD_MIN_LENGTH = 6;
export const PASSWORD_MAX_LENGTH = 20;

export const SUBSCRIPTION_TEAM_MAPPING = new Map([['ROLE_REDSHIRT', 10], ['ROLE_STARTER', 20], ['ROLE_MVP', 1000]]);
export const SUBSCRIPTION_PLAYER_MAPPING = new Map([['ROLE_REDSHIRT', 5], ['ROLE_STARTER', 10], ['ROLE_MVP', 1000]]);
