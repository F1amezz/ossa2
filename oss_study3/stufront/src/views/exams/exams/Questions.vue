<template>
	<popup ref="popup" :loading="popupLoading">
		










	</popup>
</template>
<script>
	import popup from '@/components/popup/dialogPopup.vue';
	export default {
		components: {
			popup,
		},
		data() {
			return {

				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
				
			};
		},
		methods: {
			//打开弹框
			open(parameter, title, size) {
				this.parameter = parameter;
				this.$refs.popup.open(title, size);
					this.init();
				
			},
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>