import Vue from 'vue';
import router from '../router';
import caches from "util/caches.js";
import utils from 'util/utils.js';
import constant from 'util/constant.js';
import _axios from './config.js';
import qs from 'qs'
import {
	Message
} from 'element-ui';

/**
 * 请求拦截器
 * 设置token请求头
 */
_axios.interceptors.request.use(
	config => {
		config.headers[constant.cacheskey.token] = caches.token.get();
		return config;
	}, error => {
		utils.msg.exception(error);
		return Promise.reject(error);
	}
);


/**
 * 响应拦截器
 * 错误时统一处理
 */
_axios.interceptors.response.use(response => {
	if (response.config.responseType == "blob") {
		return response;
	} else {
		return response.data;
	}
}, error => {
	utils.msg.exception(error);
	return Promise.reject(error);
});

Plugin.install = function(Vue, options) {
	Vue.axios = _axios;
	window.axios = _axios;
	Object.defineProperties(Vue.prototype, {
		axios: {
			get() {
				return _axios;
			}
		},
		$axios: {
			get() {
				return _axios;
			}
		},
	});
};

Vue.use(Plugin);

let ApiUtils = {
	getAjax: function(url, params, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "get", null, params, null, successCallback, failCallback, exceptionCallback,
			false);
	},
	getLoadingAjax: function(url, params, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "get", null, params, null, successCallback, failCallback, exceptionCallback,
			true);
	},
	postAjax: function(url, data, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "post", data, null, null, successCallback, failCallback, exceptionCallback,
			false);
	},
	postLoadingAjax: function(url, data, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "post", data, null, null, successCallback, failCallback, exceptionCallback,
			true);
	},
	putAjax: function(url, data, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "put", data, null, null, successCallback, failCallback, exceptionCallback,
			false);
	},
	putLoadingAjax: function(url, data, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "put", data, null, null, successCallback, failCallback, exceptionCallback, true);
	},
	deleteAjax: function(url, params, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "delete", null, params, null, successCallback, failCallback, exceptionCallback,
			false);
	},
	deleteLoadingAjax: function(url, params, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "delete", null, params, null, successCallback, failCallback, exceptionCallback,
			true);
	},
	uploadFileLoadingAjax: function(url, data, successCallback, failCallback, exceptionCallback) {
		ApiUtils.sendAjax(url, "post", data, null, {
			'Content-Type': 'multipart/form-data'
		}, successCallback, failCallback, exceptionCallback, true);
	},
	sendDownFile: function(url, method, data, params, headers) {
		axios({
			method: method,
			url: url,
			responseType: 'blob',
			params: params,
			data: data,
			headers: headers,
		}).then(res => {
			if (res.data) {
				//获取文件名
				let fileName = res.headers["content-disposition"].substring(
					res.headers["content-disposition"].indexOf("=") + 1
				);
				let blob = new Blob([res.data]);
				if (window.navigator.msSaveOrOpenBlob) {
					navigator.msSaveBlob(blob, fileName);
				} else {
					//下载
					var link = document.createElement("a");
					link.href = window.URL.createObjectURL(blob);
					link.download = fileName;
					link.click();
					window.URL.revokeObjectURL(link.href);
				}
			}
		}, error => {

		})
	},
	sendAjax: function(url, method, data, params, headers, successCallback, failCallback, exceptionCallback,
		isLoading) {
		if (typeof arguments[arguments.length - 1] == "boolean") {
			isLoading = arguments[arguments.length - 1];
		} else {
			isLoading = false;
		}
		let messageObj = null;
		if (isLoading) {
			messageObj = Message.info({
				message: "加载中",
				iconClass: "el-icon-loading",
				duration: 0
			});
		}
		_axios({
			method,
			url,
			data,
			params,
			headers,
			paramsSerializer: params => {
				return qs.stringify(params, {
					indices: false
				})
			}
		}).then(response => {
			if (messageObj) {
				messageObj.close();
			}
			ApiUtils.baseCallBack(url, method, data, params, response, successCallback, failCallback,
				exceptionCallback);
		}, error => {
			if (messageObj) {
				messageObj.close();
			}
		});
	},
	baseCallBack(url, method, data, params, response, successCallback, failCallback, exceptionCallback) {
		//登录失效
		if (response.code == constant.response.code.nologin || response.code == constant.response.code.badtoken) {
			utils.msg.warning(response.msg)
			setTimeout(() => {
				router.replace('/login');
			}, 1000);
			//刷新token
		} else if (response.code == constant.response.code.flusntoken) {
			//更新token
			caches.token.set(response.data);
			_axios.interceptors.request.use(
				//刷新token
				function(config) {
					config.headers[constant.cacheskey.token] = caches.token.get();
					return config;
				},
				function(error) {
					return Promise.reject(error);
				}
			);
			ApiUtils.sendAjax(url, method, data, params, successCallback, failCallback, exceptionCallback);
			//请求成功
		} else if (response.code == constant.response.code.success) {
			if (successCallback) {
				successCallback(response);
			} else {
				utils.msg.success(response.msg);
			}
			//请求失败
		} else if (response.code == constant.response.code.fail) {
			if (failCallback) {
				failCallback(response);
			} else {
				utils.msg.warning(response.msg);
			}
			//后台异常
		} else if (response.code == constant.response.code.exception) {
			if (exceptionCallback) {
				exceptionCallback(response);
			} else if (failCallback) {
				failCallback(response);
			} else {
				utils.msg.warning(response.msg);
			}
			//没有权限
		} else if (response.code == constant.response.code.noauth) {
			utils.msg.warning(response.msg)
		}
	}
}
export {
	ApiUtils,
	_axios
};
