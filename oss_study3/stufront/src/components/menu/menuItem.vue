<template>
	<el-submenu :index="menu.id" v-if="menu && menu.children && menu.children.length > 0" :class="{ hover: isHover }" @mouseon="hoverMenu"
	 @mouseout="notHoverMenu">
		<template slot="title">
			<i :class="menu.icon"></i>
			<span slot="title" :title="menu.name">{{ menu.name }}</span>
		</template>
		<menu-item :menu="item" v-for="(item, i) in menu.children" :key="item.id" @click="click"></menu-item>
	</el-submenu>
	<el-menu-item class="menu-item" :index="menu.url" :class="{ hover: isHover }" v-else @click="click" :name="menu.name"
	 :url="menu.opentype == 2 ? menu.name : menu.url">
		<template slot="title">
			<i :class="menu.icon"></i>
			<span slot="title" :title="menu.name">{{ menu.name }}</span>
		</template>
	</el-menu-item>
</template>

<script>
	export default {
		name: 'menuItem',
		components: {},
		data() {
			return {
				isHover: false
			};
		},
		props: {
			menu: {
				type: Object,
				default () {
					return {};
				}
			},
			isOpen: {
				type: Boolean,
				default () {
					return false;
				}
			}
		},
		methods: {
			click(e) {
				this.$emit('click', e);
			},
			//鼠标悬浮在菜单上
			hoverMenu() {
				this.isHover = true;
			},
			//鼠标没有悬浮在菜单上
			notHoverMenu() {
				this.isHover = false;
			}
		},
		mounted() {}
	};
</script>

<style scoped>
	span {
		display: inline-block;
		width: 70px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.el-menu--collapse>div>.el-submenu>.el-submenu__title span {
		height: 0;
		width: 0;
		overflow: hidden;
		visibility: hidden;
		display: inline-block;
	}

	.el-menu--collapse>div>.el-submenu>.el-submenu__title .el-submenu__icon-arrow {
		display: none;
	}
</style>
