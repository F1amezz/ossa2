import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/user/list", params, successCallback, failCallback,
			exceptionCallback);
	},
	list2: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/user/list2", params, successCallback, failCallback,
			exceptionCallback);
	},
	listAll: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/user/listAll", params, successCallback, failCallback,
			exceptionCallback);
	},listAllEmps: (  successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/user/listEmps", {}, successCallback, failCallback,
			exceptionCallback);
	},
	save: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/system/user/save", params, successCallback, failCallback,
			exceptionCallback);
	},
	toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/user/toUpdate", params, successCallback, failCallback,
			exceptionCallback);
	},
	update: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/system/user/update", params, successCallback, failCallback,
			exceptionCallback);
	},
	remove: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/system/user/remove", params, successCallback, failCallback,
			exceptionCallback);
	},
	leave: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/system/user/leave", params, successCallback, failCallback,
			exceptionCallback);
	}
}
export default api;
