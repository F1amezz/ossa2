<template>
	<el-upload ref="upload" class="avatar-uploader" :show-file-list="false" :before-upload="beforeUpload"
		:on-success="onSuccess" :on-change="change" :action="action" :headers="headers" :data="data">
		<img v-if="url" :src="url" class="avatar">
		<i v-else class="el-icon-plus avatar-uploader-icon"></i>
		<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过{{size}}kb</div>
	</el-upload>
</template>

<script>
	export default {
		name: 'imageUpload',
		props: {
			/**
			 * 返回的值
			 */
			value: {
				type: String,
				required: false,
				default: ""
			},
			/**
			 * 上传模式 
			 * base64 直接把图片转为base64
			 * -----------------------
			 * 以下上传都会上传都后台
			 * -----------------------
			 * fileSystem 服务器把图片保存到文件服务器
			 */
			model: {
				type: String,
				required: true,
				default: "base64"
			},
			/**
			 * 保存目录
			 */
			uri: {
				type: String,
				required: false,
				default: ""
			},
			/**
			 * 上传大小限制，单位KB
			 */
			size: {
				type: Number,
				required: false,
				default: 128
			},
		},
		//上面是父组件可传入参数
		data() {
			return {

				url: '',
				action: this.$constant.serverUrl + "/components/uploadComponent/fileUpload",
				headers: {
					jwttoken: this.$caches.token.get()
				},
				data: {
					model: this.model,
					uri: this.uri
				},
			};
		},
		model: {
			prop: 'value',
			event: 'change'
		},
		methods: {
			onSuccess(res, file) {
				if (res.code == this.$constant.response.code.success) {
					this.url = URL.createObjectURL(file.raw);
					//默认，选择图片使用流上传
					this.$emit("change", res.data);
				} else {
					this.$utils.msg.warning(res.msg);
				}
			},
			beforeUpload(file) {
				if (!this.isRequest()) {
					file = file.raw;
				}
				//格式化和大小通过
				return this.isPic(file) && this.isLtSize(file);
			},
			isRequest() {
				return this.model != "base64";
			},
			isPic(file) {
				if (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg') {
					return true;
				} else {
					this.$utils.msg.warning('图片只能支持JPEG/JPG/PNG 格式!');
					return false;
				}
			},
			isLtSize(file) {
				if (file.size / 1024 < this.size) {
					return true;
				} else {
					this.$utils.msg.warning('上传头像图片大小不能超过 ' + this.size + 'KB!');
					return false;
				}
			},
			change(file, fileList) {
				//文件改变
				if (this.isPic(file.raw) && this.isLtSize(file)) {
					//多一层判断为后期添加类型扩展做准备
					if (!this.isRequest()) {
						if (this.model == "base64") {
							//显示图片
							this.url = URL.createObjectURL(file.raw);
							this.getBase64(file.raw).then(res => {
								this.$emit("change", res);
							});
						}
					}
				}
			},
			getBase64(file) {
				return new Promise(function(resolve, reject) {
					let reader = new FileReader();
					let imgResult = '';
					reader.readAsDataURL(file);
					reader.onload = function() {
						imgResult = reader.result;
					};
					reader.onerror = function(error) {
						reject(error);
					};
					reader.onloadend = function() {
						resolve(imgResult);
					};
				});
			},
		},
		watch: {
			value: {
				handler: function(val) {
					this.url = "";
					if (val) {
						this.url = val
					}
				},
				immediate: true,
				deep: true
			}
		}
	};
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
