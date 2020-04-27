/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/27/2019
*/
import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

//Function for player stats API
export function getCoordinatorByNameAndTeam(ratingRequest) {
    console.log(ratingRequest);
    return request({
        url: API_BASE_URL + "/coordinators/rating/",
        method: 'POST',
        body: JSON.stringify(ratingRequest)
    });
}