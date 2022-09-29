import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	login: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/login", params, successCallback, failCallback, exceptionCallback);
	},
	getInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getInfo", params, successCallback, failCallback, exceptionCallback);
	},
	logout: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/logout", params, successCallback, failCallback, exceptionCallback);
	}, 
	getVerifyCode: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getVerifyCode", params, successCallback, failCallback, exceptionCallback);
	},
	getSystemSetting: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getSystemSetting", params, successCallback, failCallback, exceptionCallback);
	},
	sendFindPwdEmail: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/sendFindPwdEmail", params, successCallback, failCallback, exceptionCallback);
	},
	findPwd: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/findPwd", params, successCallback, failCallback, exceptionCallback);
	},
	getAllAreas: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getAllAreas", params, successCallback, failCallback, exceptionCallback);
	},
	getUnReadSms: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getUnReadSms", params, successCallback, failCallback, exceptionCallback);
	},
	getWechatLoginInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/getWechatLoginInfo", params, successCallback, failCallback, exceptionCallback);
	},
	wechatBind: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/wechatBind", params, successCallback, failCallback, exceptionCallback);
	},
}
export default api;
