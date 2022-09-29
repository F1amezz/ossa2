import caches from 'util/caches.js';
import utils from 'util/utils.js';
let permissions = {
	hasPermissionCode: {
		and: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let permissionCode = caches.permissionCode.get();
				if (!_.isEmpty(permissionCode)) {
					return utils.array.includesAll(permissionCode, val);
				}
			}
			return false;
		},
		or: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let permissionCode = caches.permissionCode.get();
				if (!_.isEmpty(permissionCode)) {
					return utils.array.includes(permissionCode, val);
				}

			}
			return false;
		}
	},
	hasRoleCode: {
		and: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let roleCode = caches.roleCode.get();
				if (!_.isEmpty(roleCode)) {
					return utils.array.includesAll(roleCode, val);
				}
			}
			return false;
		},
		or: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let roleCode = caches.roleCode.get();
				if (!_.isEmpty(roleCode)) {
					return utils.array.includes(roleCode, val);
				}

			}
			return false;
		}
	},
	hasRoleCodes: {
		and: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let roleCodes = caches.roleCodes.get();
				if (!_.isEmpty(roleCodes)) {
					return utils.array.includesAll(roleCodes, val);
				}
			}
			return false;
		},
		or: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let roleCodes = caches.roleCodes.get();
				if (!_.isEmpty(roleCodes)) {
					return utils.array.includes(roleCodes, val);
				}

			}
			return false;
		}
	},
	hasDepartmentCode: {
		and: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let departmentCode = caches.departmentCode.get();
				if (!_.isEmpty(departmentCode)) {
					return utils.array.includesAll(departmentCode, val);
				}
			}
			return false;
		},
		or: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let departmentCode = caches.departmentCode.get();
				if (!_.isEmpty(departmentCode)) {
					return utils.array.includes(departmentCode, val);
				}
			}
			return false;
		}
	},
	hasDepartmentCodes: {
		and: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let departmentCodes = caches.departmentCodes.get();
				if (!_.isEmpty(departmentCodes)) {
					return utils.array.includesAll(departmentCodes, val);
				}
			}
			return false;
		},
		or: function(val) {
			if (!_.isEmpty(val)) {
				if (_.isString(val)) {
					//字符串
					val = val.split(",");
				} else if (_.isArray(val)) {
					//数组
				} else {
					try {
						throw "val格式错误"
					} catch (err) {
						console.error(err);
					}
					return false;
				}
				let departmentCodes = caches.departmentCodes.get();
				if (!_.isEmpty(departmentCodes)) {
					return utils.array.includes(departmentCodes, val);
				}
			}
			return false;
		}
	},
};



export default permissions;
