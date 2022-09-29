<template>
	<transition name="el-zoom-in-center">
		<el-menu :mode="mode" v-if="mode == currentMode" :default-active="activeIndex" :default-openeds="openeds"
			class="menu" :class="currentMode == 'vertical' ? 'el-menu-vertical' : ''" @open="handleExpend"
			@close="handleCollapse" :collapse="isCollapse">
			<menu-item :menu="item" v-for="(item, i) in menuData" :key="item.id"
				v-if="(currentMode == 'vertical') || i < menuShowedNum" @click="click">
			</menu-item>
			<el-submenu index="1" v-if="(currentMode == 'horizontal') && (menuData.length > menuShowedNum)">
				<template slot="title">
					<span slot="title" title="更多" class="el-icon-more"></span>
				</template>
				<menu-item :menu="item" v-for="(item, i) in menuData" :key="item.id" v-if="i >= menuShowedNum"
					@click="click">
				</menu-item>
			</el-submenu>
		</el-menu>
	</transition>
</template>

<script>
import utils from 'util/utils.js';
import menuItem from './menuItem.vue';
export default {
	name: 'menuTree',
	components: {
		menuItem
	},
	data() {
		return {
			menuData: [],
			openeds: [],
			activeIndex: '',
			menuShowedNum: 2
		};
	},
	props: {
		mode: {
			type: String,
			default() {
				return "vertical";
			}
		},
		currentMode: {
			type: String,
			default() {
				return "vertical";
			}
		},
		isOpen: {
			type: Boolean,
			default() {
				return false;
			}
		},
		isCollapse: {
			type: Boolean,
			default() {
				return false;
			}
		},
		items: {
			type: Array,
			default() {
				return [];
			}
		},
		type: {
			type: Number,
			default() {
				return 2;
			}
		}
	},
	methods: {
		setMenuShowedNumByWidth() {
			let width = window.innerWidth;
			if (width > 1950) {
				this.menuShowedNum = 8;
			} else if (width > 1800) {
				this.menuShowedNum = 7;
			} else if (width > 1650) {
				this.menuShowedNum = 6;
			} else if (width > 1500) {
				this.menuShowedNum = 5;
			} else if (width > 1350) {
				this.menuShowedNum = 4;
			} else if (width > 1200) {
				this.menuShowedNum = 3;
			} else if (width > 1050) {
				this.menuShowedNum = 2;
			} else if (width > 900) {
				this.menuShowedNum = 1;
			} else {
				this.menuShowedNum = 0;
			}
		},
		setActiveIndex(activeIndex) {
			this.activeIndex = activeIndex;
		},
		handleMenuOpen(menuid) {
			this.openeds = [];
			this.openeds = this.openeds.push(menuid);
		},
		handleMenuCollapse() {
			if (this.isOpen) {
				let menudata = this.getMenuDate();
				for (let v of menudata) {
					this.openeds = this.openeds.concat(this.getChildMenu(v));
				}
			} else {
				this.openeds = [];
			}
		},
		getChildMenu(menu) {
			let children = [];
			children.push(menu.id);
			if (menu.children && menu.children.length > 0) {
				for (let item of menu.children) {
					children = children.concat(this.getChildMenu(item));
				}
			}

			return children;
		},
		//点击事件
		click(e) {
			let option = {
				id: e.$attrs.url,
				name: e.$attrs.name,
				url: e.$attrs.url,
				closable: true
			};
			this.$emit('click', option);
		},
		getDate() {
			return this.items;
		},
		getMenuDate() {
			return this.menuData;
		},
		//打开左侧菜单
		handleExpend(key, keyPath) { },
		//关闭左侧菜单
		handleCollapse(key, keyPath) { },
		init() {
			if (this.items) {
				let tmp = this.items;
				if (this.type == 2) {
					tmp = utils.convertSimpleTreeToStandardTree(
						tmp, {
						defaultParent: 0,
						label: 'name'
					}, {
						label: 'name'
					}
					);
				}
				this.menuData = tmp;
				this.handleMenuCollapse();
			}
		}
	},
	watch: {
		items() {
			this.init();
		},
		isOpen() {
			this.handleMenuCollapse();
		}
	},
	mounted() {
		this.init();
		this.setMenuShowedNumByWidth();
		window.addEventListener('resize', this.setMenuShowedNumByWidth);
	}
};
</script>

<style scoped="scoped" lang="scss">
.el-menu-vertical:not(.el-menu--collapse) {
	width: 200px;
}
</style>
