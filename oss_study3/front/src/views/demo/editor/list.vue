<template>
	<div class="tab-body ">
		<el-form class="dataForm" ref="form" :inline="true" label-width="80px">
			<el-row>
				<el-col :span="12">
					<el-form-item label="富文本">
						<wang-edit v-model="wangEditValue">
						</wang-edit>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-tag type="success">{{wangEditValue}}</el-tag>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="12">
					<el-form-item label="md富文本">
						<mavon-editor v-model="mavonEditorValue">
						</mavon-editor>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-tag type="success">{{mavonEditorValue}}</el-tag>
				</el-col>
			</el-row>
		</el-form>

	</div>
</template>

<script>

	import wangEdit from '@/components/wangEdit/wangEdit.vue';
	
	export default {
		components: {
			wangEdit
		},
		data() {
			return {
				wangEditValue: "",
				mavonEditorValue: ""
			};
		},

		methods: {

		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
