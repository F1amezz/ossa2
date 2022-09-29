import permissions from '@/util/permissions.js';
/**
 * 判断权限标识-or
 * 即拥有任意权限标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasPermissionCode.or(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
