import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

export function getCoachRankings() {
    return request({
        url: API_BASE_URL + "/coaches/allStats",
        method: 'GET'
    });
}