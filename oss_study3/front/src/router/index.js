import Vue from 'vue'
import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import caches from "@/util/caches.js";
import utils from "@/util/utils.js";
import api from "@/api/index.js";
import store from '@/store'
Vue.use(VueRouter)
const login = () => import("views/login.vue");
const index = () => import("views/index.vue");
const notFound = () => import("views/error/404.vue");
const findPwd = () => import("views/findPwd.vue");
const weChatLogin = () => import("views/weChatLogin.vue");
const wechatBind = () => import("views/wechatBind.vue");
const tokenLogin = () => import("views/tokenLogin.vue");
const failLogin = () => import("views/failLogin.vue");
//公共路由
export const constantRouterMap = [{
		path: '/login',
		component: login
	},
	{
		path: '/findPwd',
		component: findPwd
	},
	{
		path: '/weChatLogin',
		component: weChatLogin
	},{
		path: '/wechatBind',
		component: wechatBind
	},
	{
		path: '/tokenLogin',
		component: tokenLogin
	},
	{
		path: '/failLogin',
		component: failLogin
	},
	{
		path: '/',
		component: index,
		children: []
	},
	{
		path: '*',
		redirect: "/"
	}
];

const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
	return originalReplace.call(this, location).catch(err => {});
};
const router = new VueRouter({
	mode: 'history',
	routes: constantRouterMap
});
//路由的拦截器
router.beforeEach((to, from, next) => {
	//进度条开启
	NProgress.start();
	//判断系统设置，没有后端获取。
	let systemSetting = caches.systemSetting.get();
	if (!systemSetting) {
		api.getSystemSetting(null, response => {
			store.dispatch('setSystemSetting', response.data);
		});
	}
	//判断地区信息，没有后端获取。
	let areas = caches.areas.get();
	if (!areas) {
		api.getAllAreas(null, response => {
			caches.areas.set(response.data);
			caches.areasStandard.remove();
			let tmp = utils.convertSimpleTreeToStandardTree(response.data, {
				id: "id",
				pid: "pid",
				defaultParent: 100000,
				label: "name"
			});
			caches.areasStandard.set(tmp);
		});
	}

	if (to.path == '/login') {
		caches.token.remove();
	}
	let token = caches.token.get();;
	if (!token && to.path != '/login' ) {
		if (to.path == '/findPwd'|| to.path == "/weChatLogin"|| to.path=="/wechatBind" || to.path=="/tokenLogin" || to.path=="/failLogin" ) {
			next()
		} else {
			next({
				//path: '/login'
				path: '/weChatLogin'
			})
		}
	} else {
		next()
	}
});
router.afterEach(() => {
	NProgress.done()
})
export default router;
