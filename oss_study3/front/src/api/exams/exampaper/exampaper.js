import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/list", params, successCallback, failCallback, exceptionCallback);
	},
	list2: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/list2", params, successCallback, failCallback, exceptionCallback);
	},
	list3: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/list3", params, successCallback, failCallback, exceptionCallback);
	},
	save: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/exams/exampaper/save", params, successCallback, failCallback, exceptionCallback);
	},
	toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/toUpdate", params, successCallback, failCallback, exceptionCallback);
	},
	update: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/exams/exampaper/update", params, successCallback, failCallback, exceptionCallback);
	},
	remove: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/exams/exampaper/remove", params, successCallback, failCallback, exceptionCallback);
	},

	listQuesExamp: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/quesExamp/list", params, successCallback, failCallback, exceptionCallback);
	},
	listQuestion: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/quesExamp/listQuestion", params, successCallback, failCallback, exceptionCallback);
	},
	updateExamr: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/exams/quesExamp/updateExamr", params, successCallback, failCallback, exceptionCallback);
	},
	//查询课程
	listCourse: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/listCourse", params, successCallback, failCallback, exceptionCallback);
	},
	//查询模块
	listMid: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/listMid", params, successCallback, failCallback, exceptionCallback);
	},
	//查询科目
	listKid: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exampaper/listKid", params, successCallback, failCallback, exceptionCallback);
	},
}

export default api;
