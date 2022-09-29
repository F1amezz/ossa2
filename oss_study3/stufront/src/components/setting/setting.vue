<template>
	<el-popover class="right-item  el-submenu setting" style="height: 100%;" width="350" placement="top-start" trigger="click">
		<div class="setting-body">
			<div class="navType">
				<div class="navType-title">导航菜单风格</div>
				<div class="navType-body" style="position: relative;">
					<img src="~assets/img/navMenu-left.png" @click="changeMenuMode('vertical',0)" />
					<img src="~assets/img/navMenu-top.png" @click="changeMenuMode('horizontal',1)" />
					<div :style="navTypeCheckedStyle" class="el-icon-check navTypeChecked"></div>
				</div>
			</div>
			<div class="theme">
				<div class="theme-title">主题色</div>
				<div class="theme-body" style="position: relative;">
					<div v-for="(value,key,index)  in themeColor" :title="key" :style="{ background: value }" @click="changeTheme(key, index)"></div>
					<div :style="themeCheckedStyle" class="el-icon-check themeChecked"></div>
				</div>
			</div>
		</div>
		<div slot="reference" class="el-icon-s-tools el-submenu__title setting-icon" style="width: 60px;"></div>
	</el-popover>
</template>

<script>
	import {
		mapGetters
	} from 'vuex';

	export default {
		name: 'setting',
		data() {
			return {
				nigheMode: this.$caches.nightMode.get(),
				themeCheckedStyle: {
					left: '10px'
				},
				navTypeCheckedStyle: {
					left: '16px'
				}
			};
		},
		props: {
			menuMode: {
				type: String,
				default () {
					return "";
				}
			},
			themeColor: {
				type: Object,
				default () {
					return {};
				}
			},
			theme: {
				type: String,
				default () {
					return '';
				}
			}
		},
		methods: {
			changeMenuMode(menuMode, num) {
				this.navTypeCheckedStyle.left = 16 + 51 * num + 'px';
				this.$emit('changeMenuMode', menuMode);
			},
			//更换主题
			changeTheme(val, num) {
				this.themeCheckedStyle.left = 10 + 51 * num + 'px';
				this.$emit('changeTheme', val);
			},
			//更换深夜模式
			changeNightMode(val) {
				this.$emit('changeNightMode', val);
				this.nigheMode = val;
			},
			init() {
				let index = -1;
				for (const i in this.themeColor) {
					index++;
					if (i == this.theme) {
						break;
					}

				}
				this.changeTheme(this.theme, index);
				this.changeMenuMode(this.menuMode, this.menuMode == 'vertical' ? 0 : 1);
			}
		},
		mounted() {
			this.init();
		}
	};
</script>

<style scoped="scoped" lang="scss">
	.setting-body {
		>div {
			>[class$='-title'] {
				clear: left;
				border-bottom: 1px solid lightgrey;
				padding: 10px;
			}

			>[class$='-body'] {

				>div,
				>img {
					width: 35px;
					height: 35px;
					margin: 8px;
					float: left;
				}
			}
		}

		.theme {
			.theme-body {
				position: relative;

				.themeChecked {
					position: absolute;
					top: 10px;
					color: #ffffff;
				}
			}
		}

		.navType {
			.navType-body {
				position: relative;

				.navTypeChecked {
					position: absolute;
					top: 16px;
					color: #00aaff;
				}
			}
		}
	}
</style>
