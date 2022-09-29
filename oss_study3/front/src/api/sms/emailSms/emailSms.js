import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	toSend: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sms/emailSms/toSend", params, successCallback, failCallback,
			exceptionCallback);
	},
	send: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/sms/emailSms/send", params, successCallback, failCallback,
			exceptionCallback);
	},
}

export default api;
