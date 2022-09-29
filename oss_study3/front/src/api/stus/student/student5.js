import {
	ApiUtils,
	_axios
}from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/list", params, successCallback, failCallback, exceptionCallback);
	},intnStu(params, successCallback, failCallback, exceptionCallback){
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/intnStu", params, successCallback, failCallback, exceptionCallback);
	},
	save: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.postLoadingAjax(constant.serverUrl + "/stus/student/save", params, successCallback, failCallback, exceptionCallback);
	},
	toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/toUpdate", params, successCallback, failCallback, exceptionCallback);
	},
	update: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.putLoadingAjax(constant.serverUrl + "/stus/student/update", params, successCallback, failCallback, exceptionCallback);
	},
	remove: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.deleteLoadingAjax(constant.serverUrl + "/stus/student/remove", params, successCallback, failCallback, exceptionCallback);
	},


	//查询该班级学生
	toStus: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/toStus", params, successCallback, failCallback, exceptionCallback);
	},
	stuInClass: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/stuInClass", params, successCallback, failCallback, exceptionCallback);
	},
	
	//修改全班学生默认密码
	updStuPwd: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/updStuPwd", params, successCallback, failCallback, exceptionCallback);
	},
	//统计班级学生人数
	ClazzStusNum: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/stus/student/clazzstusnum", params, successCallback, failCallback, exceptionCallback);
	},
}

export default api;
