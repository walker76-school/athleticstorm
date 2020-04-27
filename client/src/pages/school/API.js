/*
*   Filename: API.js
*   Author: Andrew Walker
*   Date Last Modified: 4/23/2019
*/

import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

export function getTeamByName(teamName) {
    return request({
        url: API_BASE_URL + "/teams/byName/" + teamName,
        method: 'GET'
    });
}

export function getVideosByTeamName(teamName) {
    return request({
        url: API_BASE_URL + "/teams/videos/byName/" + teamName,
        method: 'GET'
    });
}

export function getCoachesByTeamId(teamId) {
    return request({
        url: API_BASE_URL + "/coaches/byTeamId/" + teamId,
        method: 'GET'
    });
}

export function getCoordinatorsByTeamId(teamId) {
    return request({
        url: API_BASE_URL + "/coordinators/byTeamId/" + teamId,
        method: 'GET'
    });
}

export function getRoster(teamId, year) {
    return request({
        url: API_BASE_URL + "/roster/" + teamId + "/" + year,
        method: 'GET'
    });
}