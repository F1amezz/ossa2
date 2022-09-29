import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	list: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/components/departmentComponent/list", params, successCallback,
			failCallback,
			exceptionCallback);
	},
	getByIds: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getLoadingAjax(constant.serverUrl + "/components/departmentComponent/getByIds", params, successCallback,
			failCallback,
			exceptionCallback);
	}
}
export default api;
