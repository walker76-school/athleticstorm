/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/23/2019
*/
import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

//Function for player stats API
export function getCoordinatorByName(coordinatorName) {
    return request({
        url: API_BASE_URL + "/coordinators/byName/" + coordinatorName,
        method: 'GET',
    });
}