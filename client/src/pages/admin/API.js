/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/23/2019
*/
import {request} from './../../util/APIUtils';
import {ACCESS_TOKEN, API_BASE_URL} from "../../constants";

//Function to refresh data in entire db
export function refresh() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/admin/refresh",
        method: 'POST'
    });
}