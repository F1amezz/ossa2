<template>
	<div class="tab-body ">
		<el-form class="dataForm" ref="form" :inline="true" label-width="80px">
			<el-row>
				<el-col :span="12">
					<el-form-item label="图片上传组件">
						<image-upload v-model="imgValue" model="fileSystem" uri="/statics/head" :size="128">
						</image-upload>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-tag type="success">{{imgValue}}</el-tag>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="12">
					<el-form-item label="阿里OSS上传">
						<image-upload v-model="aliyunOssValue" model="aliyunOss" uri="/statics/demo" :size="128">
						</image-upload>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-tag type="success">{{aliyunOssValue}}</el-tag>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="12">
					<el-form-item label="文件上传组件">
						<file-upload v-model="fileValue" model="fileSystem" uri="/statics/head" :size="128">
						</file-upload>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-tag type="success">{{fileValue}}</el-tag>
				</el-col>
			</el-row>
			
		</el-form>

	</div>
</template>

<script>
	import fileUpload from '@/components/biz/fileUpload/fileUpload.vue';
	import imageUpload from '@/components/biz/imageUpload/imageUpload.vue';
	export default {
		components: {
			fileUpload,
			imageUpload,
		},
		data() {
			return {
				imgValue: "http://127.0.0.1:8085/QLH-Api/statics/head/947361c1-a553-4227-b37e-da8063f38fec-1.jpg",
				fileValue:"http://127.0.0.1:8085/QLH-Api/statics/head/055664f3-2064-487e-ad09-ec122ec4af5b-j210303.sql",
				aliyunOssValue:""
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
