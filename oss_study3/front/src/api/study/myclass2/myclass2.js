import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	getClassOne: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/study/myclass/getClassOne", params, successCallback, failCallback, exceptionCallback);
	},
	getSummarizeAll: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/study/myclass/getSummarizeAll2", params, successCallback, failCallback, exceptionCallback);
	},
	getStudentList: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/study/myclass/getStudentList", params, successCallback, failCallback, exceptionCallback);
	},
	// 获取全部班级
	getAllClass: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/study/myclass/listCname", params, successCallback, failCallback, exceptionCallback);
	},
}

export default api;