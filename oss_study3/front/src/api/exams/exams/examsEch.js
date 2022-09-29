import {
    ApiUtils,
    _axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
    list: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/list", params, successCallback, failCallback, exceptionCallback);
    },
    getExamsEch: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/examsEch/getExamsEch", params, successCallback, failCallback, exceptionCallback);
    },
    getExamsEchOne: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/examsEch/getExamsEchOne", params, successCallback, failCallback, exceptionCallback);
    },
}

export default api;
