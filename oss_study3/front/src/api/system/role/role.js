import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/role/list", params, successCallback, failCallback,
			exceptionCallback);
	},
	save: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/system/role/save", params, successCallback, failCallback,
			exceptionCallback);
	},
	toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/role/toUpdate", params, successCallback, failCallback,
			exceptionCallback);
	},
	update: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/system/role/update", params, successCallback, failCallback,
			exceptionCallback);
	},
	remove: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/system/role/remove", params, successCallback, failCallback,
			exceptionCallback);
	},jxfn: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/system/role/jxfn", params, successCallback, failCallback,
			exceptionCallback);
	},
}
export default api;
