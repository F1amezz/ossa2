import permissions from '@/util/permissions.js';
/**
 * 判断角色标识含子-or
 * 即拥有任意角色标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasRoleCodes.or(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
