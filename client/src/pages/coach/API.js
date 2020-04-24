/*
*   Filename: Coach.js
*   Author: John Eyre
*   Date Last Modified: 4/23/2019
*/
import {request} from './../../util/APIUtils';
import {ACCESS_TOKEN, API_BASE_URL} from "../../constants";

//Function to get coaches from the api
export function coachByName(coachName) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/coaches/record/byName/" + coachName,
        method: 'GET'
    });
}