import Vue from 'vue';
import Vuex from 'vuex';
import caches from 'util/caches.js';
import constant1 from 'util/constant.js';
import {
	router,
	constantRouterMap,
} from 'router';

const constant = constant1;
Vue.use(Vuex)
export default new Vuex.Store({
	state: {
		menus: caches.permissionMenus.get() ? caches.permissionMenus.get() : [],
		routes: [],
		menuMode: caches.menuMode.get() ? caches.menuMode.get() : 'vertical',
		theme: caches.theme.get() ? caches.theme.get() : 'blue',
		themeColor: {
			blue: "#409eff",
			green: "#009688",
			grey: "#c0c4cc",
			darkblue: "#334157",
			purple: "#926dde",
			night: "#303133",
		},
		systemSetting: caches.systemSetting.get() ? caches.systemSetting.get() : '',

	},
	mutations: {
		setSystemSetting(state, systemSetting) {
			state.systemSetting = systemSetting;
			caches.systemSetting.set(systemSetting);
		},
		setMenus(state, menus) {
			state.menus = menus;
			caches.permissionMenus.set(menus);
		},
		setRouters(state, addRoutes) {
			state.routes = constantRouterMap.concat(addRoutes);
		},
		setTheme(state, theme) {
			state.theme = theme;
			caches.theme.set(theme);
		},
		setMenuMode(state, menuMode) {
			state.menuMode = menuMode;
			caches.menuMode.set(menuMode);
		}
	},
	actions: {
		setSystemSetting(context, systemSetting) {
			context.commit("setSystemSetting", systemSetting);
		},
		setTheme(context, theme) {
			context.commit("setTheme", theme);
		},
		setMenuMode(context, menuMode) {
			context.commit("setMenuMode", menuMode);
		},
		setMenus(context, menus) {
			return new Promise(resolve => {
				const result = [];
				const children = [];
				result.push({
					path: '/',
					component: () => import("@/views/index.vue"),
					children,
				})
				for (let menu of menus) {
					// URL支持{{ window.xxx }}占位符变量
					let url = (menu.url || '').replace(/{{([^}}]+)?}}/g, (s1, s2) => {
						return eval(s2)
					});
					if (menu.opentype == 2) {
						children.push({
							name: menu.name,
							path: menu.name,
							component: () => import("components/frameView/frameView.vue"),
							meta: {
								pathUrl: url,
							},
						});

					} else if (menu.opentype == 1) {
						children.push({
							name: menu.name,
							path: url,
							component: () => import('@/' + menu.component)
						});
					}
				}
				children.push({
					path: "main",
					component: () => import("@/views/main.vue")
				}, {
					path: "*",
					component: () => import("@/views/error/404.vue")
				});
				context.commit("setMenus", menus);
				context.commit("setRouters", result);
				resolve();
			});
		},
	},
	getters: {
		getSystemSetting: state => {
			return state.systemSetting;
		},
		getMenus: state => {
			return state.menus;
		},
		getRoutes: state => {
			return state.routes;
		},
		getTheme: state => {
			return state.theme;
		},
		getThemeColor: state => {
			return state.themeColor;
		},
		getMenuMode: state => {
			return state.menuMode;
		}
	},
	modules: {}
})
