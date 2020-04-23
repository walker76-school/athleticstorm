import {request} from './../../util/APIUtils';
import {API_BASE_URL} from "../../constants";

export function getPlayerStats(statsRequest) {
    return request({
        url: API_BASE_URL + "/player/getStats/",
        method: 'POST',
        body: JSON.stringify(statsRequest)
    });
}