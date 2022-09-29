<template>
	<cascader ref="cascader" v-bind="$attrs" v-on="$listeners" dataMode="standard" :options="options">
	</cascader>
</template>

<script>
	import cascader from '@/components/cascader/cascader.vue';
	export default {
		name: 'araeCascader',
		components: {
			cascader,
		},
		data() {
			return {
				options: []
			}
		},
		props: {},
		methods: {
			init() {
				this.list();
			},
			list() {
				this.options = this.$caches.areasStandard.get();
			},
			getCheckedObj() {
				return this.selectValue;
			}
		},
		watch: {},
		created() {
			this.$nextTick(() => {
				this.init();
			})
		}
	}
</script>

<style>
</style>
