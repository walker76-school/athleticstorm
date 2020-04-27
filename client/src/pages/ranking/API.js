/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/23/2019
*/

import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

export function getCoachRankings() {
    return request({
        url: API_BASE_URL + "/coaches/allStats",
        method: 'GET'
    });
}