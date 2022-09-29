import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/list", params, successCallback, failCallback, exceptionCallback);
	},
	listHis: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/listHis", params, successCallback, failCallback, exceptionCallback);
	},
	save: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/exams/exams/save", params, successCallback, failCallback, exceptionCallback);
	},
	toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/toUpdate", params, successCallback, failCallback, exceptionCallback);
	},
	update: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/exams/exams/update", params, successCallback, failCallback, exceptionCallback);
	},
	remove: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/exams/exams/remove", params, successCallback, failCallback, exceptionCallback);
	},
	getexpContent: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/getexpContent", params, successCallback, failCallback, exceptionCallback);
	},
	selectQuestionContent: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/exams/exams/selectQuestionContent", params, successCallback, failCallback, exceptionCallback);
	},
	saveAnswers: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/answers/saveAnswers", params, successCallback, failCallback, exceptionCallback);
	},
	updateAnswers: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/answers/updateAnswers", params, successCallback, failCallback, exceptionCallback);
	},
	getOneAnswers: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/answers/getOneAnswers", params, successCallback, failCallback, exceptionCallback);
	},
	isCanReadFdbck: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/answers/isCanReadFdbck", params, successCallback, failCallback, exceptionCallback);
	},

	
}

export default api;
