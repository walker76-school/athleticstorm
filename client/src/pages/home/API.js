/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/23/2019
*/
import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

//Function to get the endpoint for all FBS teams
export function getFBSTeams() {
    return request({
        url: API_BASE_URL + "/teams/fbs",
        method: 'GET'
    });
}