<template>
	<el-upload v-bind="$attrs" ref="upload" class="upload-demo" :show-file-list="true" :before-upload="beforeUpload"
		:on-success="onSuccess" :on-change="change" :action="action" :headers="headers" :data="data"
		:on-remove="onRemove" :file-list="fileList">
		<el-button size="small" type="primary">点击上传</el-button>
		<div slot="tip" class="el-upload__tip">上传文件不超过{{size}}kb</div>
	</el-upload>
</template>

<script>
	export default {
		name: 'fileUpload',
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
			/**
			 * 文件类型
			 */
			accapt: {
				type: String,
				required: false
			}
		  
		},
		//上面是父组件可传入参数
		data() {
			return {
				fileList: [],
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
			onRemove(file, fileLis) {
				this.$emit("change", "");
			},
			onSuccess(res, file) {
				if (res.code == this.$constant.response.code.success) {
					this.$emit("change", res.data);
				} else {
					console.log(res.msg)
					this.$utils.msg.warning(res.msg);
				}
			},
			beforeUpload(file) {
				if (!this.isRequest()) {
					file = file.raw;
				}
				//格式化和大小通过
				return this.isLtSize(file);
			},
			isRequest() {
				return this.model != "base64";
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
				if (this.isLtSize(file)) {
					//多一层判断为后期添加类型扩展做准备
					if (!this.isRequest()) {
						if (this.model == "base64") {
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
					if (val) {
						this.fileList = [{
							name: val.substr(val.lastIndexOf("/") + 1),
							url: val
						}]
					}
				},
				immediate: true,
				deep: true
			}
		}
	};
</script>

<style>

</style>
