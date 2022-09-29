import {
	ApiUtils,
	_axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';

const api = {
	getInfo: (params, successCallback, failCallback, exceptionCallback) => {
		ApiUtils.getAjax(constant.serverUrl + "/monitor/dashboard/getInfo", params, successCallback, failCallback,
			exceptionCallback);
	},
}
export default api;
