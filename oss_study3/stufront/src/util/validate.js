const validate = {
	required: (rule, value, callback) => {
		//必填规则
		let message = rule.message ? rule.message : "必填";
		if (value) {
			if (value instanceof Array) {
				if (value.length <= 0) {
					return callback(new Error(message));
				}
			}
			return callback();
		} else {
			return callback(new Error(message));
		}
	},
	digits: (rule, value, callback) => {
		//整数规则
		let message = rule.message ? rule.message : "非整数格式";
		if (value) {
			var reg = /^-?\d+$/;
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	number: (rule, value, callback) => {
		//数字规则
		let message = rule.message ? rule.message : "非数字格式";
		if (value) {
			var reg = /^(\-|\+)?\d+(\.\d+)?$/;
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	phone: (rule, value, callback) => {
		//手机规则
		let message = rule.message ? rule.message : "非手机格式";
		if (value) {
			var reg =
				/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	telephone: (rule, value, callback) => {
		//固话号码规则
		let message = rule.message ? rule.message : "非固话号码格式";
		if (value) {
			var reg = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	email: (rule, value, callback) => {
		//邮件规则
		let message = rule.message ? rule.message : "非邮件格式";
		if (value) {
			var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	idcard: (rule, value, callback) => {
		//身份证规则
		let message = rule.message ? rule.message : "非身份证格式";
		if (value) {
			var reg =
				/^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|31)|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}([0-9]|x|X)$/
			if (!reg.test(value)) {
				return callback(new Error(message));
			}
		}
		return callback();
	},
	length: (rule, value, callback) => {
		if (value) {
			//长度规则
			var min = rule.min ? rule.min : "";
			var max = rule.max ? rule.max : "";
			if (max && min) {
				if (value.length > max || value.length < min) {
					return callback(new Error(rule.message ? rule.message : "长度在" + min + "到" + max + "个字符"));
				} else {
					return callback();
				}
			} else if (max) {
				if (value.length > max) {
					return callback(new Error(rule.message ? rule.message : "长度最大" + max + "个字符"));
				} else {
					return callback();
				}
			} else if (min) {
				if (value.length < min) {
					return callback(new Error(rule.message ? rule.message : "长度最小" + min + "个字符"));
				} else {
					return callback();
				}
			}
		} else {
			return callback();
		}

	},
	range: (rule, value, callback) => {
		//长度规则
		var min = rule.min ? rule.min : "";
		var max = rule.max ? rule.max : "";
		if (max && min) {
			if (value > max || value < min) {
				return callback(new Error(rule.message ? rule.message : "数值应在" + min + "到" + max + "之间"));
			} else {
				return callback();
			}
		} else if (max) {
			if (value > max) {
				return callback(new Error(rule.message ? rule.message : "数值最大" + max));
			} else {
				return callback();
			}
		} else if (min) {
			if (value < min) {
				return callback(new Error(rule.message ? rule.message : "数值最小" + min));
			} else {
				return callback();
			}
		}
	},


};

export default validate;
