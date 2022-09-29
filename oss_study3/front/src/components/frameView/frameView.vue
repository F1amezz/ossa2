<template>
	<div class="tab-body"><iframe :src="reportUrl" height="100%" width="100%" frameborder="no"></iframe></div>
</template>

<script>
export default {
	name: 'frameView',
	data() {
		return {};
	},
	computed: {
		reportUrl() {
			return this.$route.meta.pathUrl;
		}
	}
};
</script>

<style></style>
