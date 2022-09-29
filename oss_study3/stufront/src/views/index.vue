
<!--
 * @Date: 2021-06-11 10:15:59
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2021-06-22 20:08:27
 * @param : {}
 * @return:
-->
<template>
	<el-container class="indexPage">
		<el-header class="header">
			<div class="left">
				<div id="logo">
					{{ getSystemSetting.systemName }}
				</div>
				<div class="leftMenu-btn">
					<el-button v-if="getMenuMode == 'vertical'" @click="isCollapse = !isCollapse" icon="el-icon-s-fold"
						size="small"></el-button>
				</div>
			</div>
			<div class="right">
				<el-menu mode="horizontal" class="right-item">
					<el-submenu index="2" class="user">
						<template slot="title">
							<img :src="userInfo.head" class="headImg" v-if="userInfo.head" />
							<img class="headImg" src="~assets/img/male.jpg" v-else />
							<span>{{ userInfo.name }}</span>
						</template>
						<el-menu-item index="2-1" @click="cleanCaches()">清除缓存</el-menu-item>
						<el-menu-item index="2-2" @click="logout()">注销</el-menu-item>
					</el-submenu>
				</el-menu>

				<setting :menuMode="getMenuMode" :theme="getTheme" :themeColor="getThemeColor"
					@changeMenuMode="changeMenuMode" @changeTheme="changeTheme" @changeNightMode="changeNightMode">
				</setting>
				<menu-tree ref="menu" mode="horizontal" :currentMode="getMenuMode" class="right-item"
					style="float: left;" :items="menus" @click="clickMenu"></menu-tree>

			</div>
		</el-header>

		<el-container class="center">
			<!-- left侧边栏 -->
			<el-aside class="left">
				<menu-tree ref="menu" mode="vertical" :currentMode="getMenuMode" :items="menus" :isCollapse="isCollapse"
					@click="clickMenu" :isOpen="isOpen"></menu-tree>
			</el-aside>
			<!-- right内容 -->
			<el-container class="right">
				<el-main class="main">
					<tab :items="tabs" :tabsLimit="tabsLimit" ref="tab" @removeTab="removeTab" @tabChange="tabChange"
						@refresh="refresh">
					</tab>
					<div class="main-bottom">
						<transition name="el-zoom-in-center">
							<router-view v-if="frameShow"></router-view>
						</transition>
					</div>
				</el-main>
			</el-container>
		</el-container>

		<el-footer class="footer">
			<span class="footer-text">{{ getSystemSetting.footer }}</span>
			<img src="~assets/img/logo-black.png" class="footer-icon" v-if="getTheme == 'grey'" />
			<img src="~assets/img/logo-white.png" class="footer-icon" v-else />

		</el-footer>
	</el-container>
</template>

<script>
import Vue from 'vue';
import {
	router
} from 'router';
import {
	mapGetters
} from 'vuex';

import menuTree from 'components/menu/menuTree.vue';
import tab from 'components/tab/tab/tab.vue';
import setting from 'components/setting/setting.vue';
import api from '@/api/index.js';
import apiuser from '@/api/system/user/user.js';

export default {
	name: 'index',
	components: {
		menuTree,
		tab,
		setting
	},
	data() {
		return {
			menus: [],
			isCollapse: false,
			isOpen: false,
			userInfo: {},
			systemsetting: [],
			currentTabId: '',
			tabs: [],
			tabsLimit: 20,
			messageLimit: 5,
			frameShow: true,
			socket: "",
			toUid: "",
			unReadSms: [],
		};
	},
	computed: {
		...mapGetters(['getThemeColor', "getTheme", "getMenuMode", 'getMenus', 'getRoutes', 'getSystemSetting'])
	},
	methods: {
		//点击菜单
		clickMenu(option) {
			if (!this.$refs.tab.addTab(option)) {
				this.$utils.msg.warning('最多打开' + this.tabsLimit + '个！');
			}
			this.$caches.tabs.set(this.$refs.tab.getTabs());
		},
		//tab切换的回调
		tabChange(id) {
			this.$caches.currentTabId.set(id);
			this.$router.replace('/' + id);
			this.$refs.menu.setActiveIndex(id);
		},
		//删除tab回调
		removeTab(id) {
			this.$caches.tabs.set(this.$refs.tab.getTabs());
		},
		//刷新tab
		refresh() {
			this.frameShow = !this.frameShow;
			setTimeout(() => {
				this.frameShow = !this.frameShow;
			}, 300);
		},
		//更换主题
		changeTheme(val) {
			this.$store.dispatch('setTheme', val);
		},
		changeMenuMode(val) {
			this.$store.dispatch('setMenuMode', val);
		},
		changeNightMode(val) {
			this.$store.dispatch('setNightMode', val);
		},
		//清除缓存
		cleanCaches() {
			this.$utils.confirm.warning('提示', '将清除系统缓存, 是否继续?', () => {
				this.$utils.msg.success('已清除缓存', 500, () => {
					this.$caches.cleanCaches();
					window.location.reload();
				});
			});
		},
		logout() {
			this.$utils.confirm.warning('提示', '是否退出?', () => {
				api.logout(null, response => {
					this.$utils.msg.success(response.msg, 500, () => {
						this.$caches.cleanAll();
						this.$router.replace('/weChatLogin');
					});
				});
			});
		},
		//获取用户信息
		getInfo() {
			api.getInfo(null,
				response => {
					let user = response.data;
					user.role2 = user.roleCode[0];
					user.role = user.role2.substring(0, 2);

					this.$caches.permissionCode.set(user.permissionCode);
					this.$caches.departmentCode.set(user.departmentCode);
					this.$caches.departmentCodes.set(user.departmentCodes);
					this.$caches.roleCode.set(user.roleCode);
					this.$caches.roleCodes.set(user.roleCodes);
					if (user.permissionMenus) {
						//初始化动态路由
						this.$store.dispatch('setMenus', user.permissionMenus).then(() => {
							this.$router.addRoutes(this.getRoutes);
							this.$refs.tab.setCurrentTabId(this.currentTabId);
							this.$refs.menu.setActiveIndex(this.$refs.tab.getCurrentTabId());
						});
						//获取动态菜单
						this.menus = this.getMenus;
					}
					delete user.permissionCode;
					delete user.permissionMenus;
					delete user.departmentCode;
					delete user.departmentCodes;
					delete user.roleCode;
					delete user.roleCodes;
					this.$caches.user.set(user);
					this.userInfo = user;
				});
 
		},
		getUnReadSms() {
		 
		},
		listMoreSms() {
		 
		},
		initWebSocket() {
			 
		},

		open() {
			//webSocket连接成功的回调
		},
		error() {
			//webSocket 连接失败。返回登录页面
			this.$router.replace('/login');
		},
		//收到websocket发来的信息的回调
		getMessage(result) {
			let unReadSms = result.data;
			this.unReadSms = JSON.parse(unReadSms);
		},
		close() {
			//webSocket连接关闭的回调
		},
		init() {
			this.currentTabId = this.$caches.currentTabId.get() != null ? this.$caches.currentTabId.get() : 'main';
			this.tabs = this.$caches.tabs.get() ? this.$caches.tabs.get() : [{
				id: 'main',
				name: '首页',
				url: '/main',
				closable: false
			}];
			//有缓存
			if (!this.$caches.user.get()) {
				this.getInfo();
			} else {
				this.userInfo = this.$caches.user.get();
				//初始化动态路由''
				if (this.getMenus) {
					this.$store.dispatch('setMenus', this.getMenus).then(() => {
						this.$router.addRoutes(this.getRoutes);
						this.$refs.tab.setCurrentTabId(this.currentTabId);
						this.$refs.menu.setActiveIndex(this.$refs.tab.getCurrentTabId());
					});
					this.menus = this.getMenus;
				}
			}
			// this.getUnReadSms();
			// this.initWebSocket();
			this.$router.replace('/');
		}
	},
	mounted() {
		this.init();
	},
	destroyed() {
		//this.socket.onclose();
	}
};
</script>

<style lang="scss" scoped="scoped">
@import '~common/custom/css/common.scss';
@import '~common/custom/css/index.scss';
</style>

