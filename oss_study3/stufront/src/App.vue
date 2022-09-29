<template>
	<div id="app">
		<router-view></router-view>
	</div>
</template>
<script>
	import imgurl from 'assets/img/webIcon.jpg'
	import {
		mapGetters
	} from 'vuex';
	export default {
		name: 'app',
		data() {
			return {
				currentTheme: 'blue',
				isNightMode: false
			}
		},
		computed: {
			...mapGetters(['getTheme', 'getSystemSetting'])
		},
		methods: {
			setTheme(val) {
				this.currentTheme = val;
				if (!this.isNightMode && val) {
					$('#body').removeAttr('class');
					$('#body').addClass(val);
				}
			},
		},
		watch: {
			'$store.getters.getSystemSetting'(val) {
				$("#icon").attr("href", val.icon);
				$("#title").html(val.title);
			},
			'$store.getters.getTheme'(val) {
				this.setTheme(val);
			},
		},
		created() {
			this.setTheme(this.$caches.theme.get());
			//动态修改网站图标和标题
			$("#icon").attr("href", this.getSystemSetting.icon);
			$("#title").html(this.getSystemSetting.title);
		}
	};
</script>

<style lang="scss">
	@import '~common/theme/blue.scss';
	@import '~common/theme/green.scss';
	@import '~common/theme/grey.scss';
	@import '~common/theme/darkblue.scss';
	@import '~common/theme/purple.scss';
	@import '~common/theme/night.scss';
</style>
