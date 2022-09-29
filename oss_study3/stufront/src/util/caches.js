import constant from 'util/constant.js';

let caches = {
	token: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.token) ? localStorage.getItem(constant.cacheskey.token) : null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.token, obj);
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.token);
		}
	},
	users: {
		get: function() {
			return    sessionStorage.getItem(constant.cacheskey.users) ? JSON.parse(sessionStorage.getItem(constant.cacheskey.users)) : null ;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.users,  JSON.stringify(obj) );
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.users);
		}
	},
	consu: {
		get: function() {
			return    sessionStorage.getItem(constant.cacheskey.consu) ? JSON.parse(sessionStorage.getItem(constant.cacheskey.consu)) : null ;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.consu,  JSON.stringify(obj) );
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.consu);
		}
	},
	user: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.user) ? JSON.parse(sessionStorage.getItem(constant.cacheskey.user)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.user, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.user);
		}
	},
	permissionMenus: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.permissionMenus) ? JSON.parse(sessionStorage.getItem(constant.cacheskey
					.permissionMenus)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.permissionMenus, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.permissionMenus);
		}
	},
	permissionCode: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.permissionCode) ? JSON.parse(sessionStorage.getItem(constant.cacheskey
					.permissionCode)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.permissionCode, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.permissionCode);
		}
	},
	departmentCode: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.departmentCode) ? JSON.parse(sessionStorage.getItem(constant.cacheskey
					.departmentCode)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.departmentCode, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.departmentCode);
		}
	},
	departmentCodes: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.departmentCodes) ? JSON.parse(sessionStorage.getItem(constant.cacheskey
					.departmentCodes)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.departmentCodes, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.departmentCodes);
		}
	},
	roleCode: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.roleCode) ? JSON.parse(sessionStorage.getItem(constant.cacheskey.roleCode)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.roleCode, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.roleCode);
		}
	},
	roleCodes: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.roleCodes) ? JSON.parse(sessionStorage.getItem(constant.cacheskey
					.roleCodes)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.roleCodes, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.roleCodes);
		}
	},
	theme: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.theme) ? localStorage.getItem(constant.cacheskey.theme) : null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.theme, obj);
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.theme);
		}
	},
	nightMode: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.nightMode) ? localStorage.getItem(constant.cacheskey.nightMode) : null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.nightMode, obj);
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.nightMode);
		}
	},
	menuMode: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.menuMode) ? localStorage.getItem(constant.cacheskey.menuMode) :
				null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.menuMode, obj);
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.menuMode);
		}
	},
	tabs: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.tabs) ? JSON.parse(sessionStorage.getItem(constant.cacheskey.tabs)) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.tabs, JSON.stringify(obj));
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.tabs);
		}
	},
	currentTabId: {
		get: function() {
			return sessionStorage.getItem(constant.cacheskey.currentTabId) ? sessionStorage.getItem(constant.cacheskey
					.currentTabId) :
				null;
		},
		set: function(obj) {
			sessionStorage.setItem(constant.cacheskey.currentTabId, obj);
		},
		remove: function() {
			sessionStorage.removeItem(constant.cacheskey.currentTabId);
		}
	},
	systemSetting: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.systemSetting) ? JSON.parse(localStorage.getItem(constant.cacheskey
					.systemSetting)) :
				null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.systemSetting, JSON.stringify(obj));
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.systemSetting);
		}
	},
	areas: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.areas) ? JSON.parse(localStorage.getItem(constant.cacheskey
					.areas)) :
				null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.areas, JSON.stringify(obj));
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.areas);
		}
	},
	areasStandard: {
		get: function() {
			return localStorage.getItem(constant.cacheskey.areasStandard) ? JSON.parse(localStorage.getItem(constant.cacheskey
					.areasStandard)) :
				null;
		},
		set: function(obj) {
			localStorage.setItem(constant.cacheskey.areasStandard, JSON.stringify(obj));
		},
		remove: function() {
			localStorage.removeItem(constant.cacheskey.areasStandard);
		}
	},
	cleanCaches: function() {
		caches.user.remove();
		caches.permissionCode.remove();
		caches.permissionMenus.remove();
		caches.departmentCode.remove();
		caches.departmentCodes.remove();
		caches.roleCode.remove();
		caches.roleCodes.remove();
		caches.systemSetting.remove();
		caches.areas.remove();
		caches.areasStandard.remove();
	},
	cleanLogin: function() {
		caches.user.remove();
		caches.permissionCode.remove();
		caches.permissionMenus.remove();
		caches.departmentCode.remove();
		caches.departmentCodes.remove();
		caches.roleCode.remove();
		caches.roleCodes.remove();
		caches.tabs.remove();
		caches.currentTabId.remove();
		caches.token.remove();
	},
	cleanAll: function() {
		caches.cleanCaches();
		caches.tabs.remove();
		caches.currentTabId.remove();
		caches.token.remove();

	},
}

export default caches;
