import permissions from '@/util/permissions.js';
/**
 * 判断部门标识-or
 * 即拥有任意部门标识
 * 
 */
export default {
	inserted(el, binding, vnode) {
		if (!permissions.hasDepartmentCode.or(binding.value)) {
			el.parentNode.removeChild(el);
		}
	}
}
