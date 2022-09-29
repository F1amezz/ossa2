import permissions from 'util/permissions.js';
/**
 * 判断权限标识-and
 * 即同时拥有权限标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasPermissionCode.and(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
