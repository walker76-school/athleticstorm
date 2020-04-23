import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

export function getFBSTeams() {
    return request({
        url: API_BASE_URL + "/teams/fbs",
        method: 'GET'
    });
}