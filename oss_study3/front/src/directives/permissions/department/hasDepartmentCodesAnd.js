import permissions from '@/util/permissions.js';
/**
 * 判断部门标识含子-and
 * 即同时拥有部门标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasDepartmentCodes.and(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
