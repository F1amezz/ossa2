import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	getInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/main/getInfo", params, successCallback, failCallback,
			exceptionCallback);
	},
	getLoginrecordCountYear: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/main/getLoginrecordCountYear", params, successCallback, failCallback,
			exceptionCallback);
	},
	getLoginrecord: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/main/getLoginrecord", params, successCallback, failCallback,
			exceptionCallback);
	},
	toUpdateInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/main/toUpdateInfo", params, successCallback, failCallback,
			exceptionCallback);
	},
	updateInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/main/updateInfo", params, successCallback, failCallback,
			exceptionCallback);
	},
	updatePwd: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/main/updatePwd", params, successCallback, failCallback,
			exceptionCallback);
	},
}
export default api;
