import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sms/sms/list", params, successCallback, failCallback,
			exceptionCallback);
	},
	toRead: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sms/sms/toRead", params, successCallback, failCallback,
			exceptionCallback);
	},
	readBatch: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/sms/sms/readBatch", params, successCallback, failCallback,
			exceptionCallback);
	},
	batchRead: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sms/sms/batchRead", params, successCallback, failCallback,
			exceptionCallback);
	},
	allRead: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sms/sms/allRead", params, successCallback, failCallback,
			exceptionCallback);
	},
}

export default api;
