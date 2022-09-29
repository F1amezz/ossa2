import permissions from '@/util/permissions.js';
/**
 * 判断角色标识-and
 * 即同时拥有角色标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasRoleCode.and(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
