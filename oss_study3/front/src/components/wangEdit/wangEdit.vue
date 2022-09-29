<template>
	<div>
		<div ref="editor"></div>
	</div>
</template>

<script>
	import E from 'wangeditor'
	export default {
		name: 'editoritem',
		data() {
			return {
				editor: null,
				fisrt: true,
			}
		},
		props: {
			//内容
			value: {
				type: String,
				default () {
					return "";
				}
			},
			//文本框菜单
			menus: {
				type: Array,
				default () {
					return [
						'head',
						'bold',
						'fontSize',
						'fontName',
						'italic',
						'underline',
						'strikeThrough',
						'indent',
						'lineHeight',
						'foreColor',
						'backColor',
						'link',
						'list',
						'todo',
						'justify',
						'quote',
						'emoticon',
						'image',
						'video',
						'table',
						'code',
						'splitLine',
						'undo',
						'redo',
					];
				}
			},
			//图片上传大小
			uploadImgMaxSize: {
				type: Number,
				default () {
					return 2 * 1024 * 1024;
				}
			},
			//图片
			uploadImgAccept: {
				type: Array,
				default () {
					return ['jpg', 'jpeg', 'png', 'gif', 'bmp'];
				}
			},
			//图片base64模式
			uploadImgShowBase64: {
				type: Boolean,
				default () {
					return false;
				}
			},
			//一次最多上传 5 个图片
			uploadImgMaxLength: {
				type: Number,
				default () {
					return 5;
				}
			},
			//图片上传请求头
			uploadImgHeaders: {
				type: Object,
				default () {
					return null;
				}
			},
			//图片上传参数
			uploadImgParams: {
				type: Object,
				default () {
					return null;
				}
			},
			//图片上传地址
			uploadImgServer: {
				type: String,
				default () {
					return '';
				}
			},
			//图片上传文件名称
			uploadFileName: {
				type: String,
				default () {
					return '';
				}
			},
			//图片上传回调
			uploadImgHooks: {
				type: Object,
				default () {
					return {
						before: function(xhr, editor, files) {
							// 图片上传之前触发
							// xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

							// 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
							// return {
							//   prevent: true,
							//   msg: '放弃上传'
							// }
						},
						success: function(xhr, editor, result) {
							// 图片上传并返回结果，图片插入成功之后触发
							// xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
						},
						fail: function(xhr, editor, result) {
							// 图片上传并返回结果，但图片插入错误时触发
							// xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
						},
						error: function(xhr, editor) {
							// 图片上传出错时触发
							// xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
						},
						timeout: function(xhr, editor) {
							// 图片上传超时时触发
							// xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
						},
						// 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
						// （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
						customInsert: function(insertImg, result, editor) {
							// 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
							// insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
							// 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
							// result 必须是一个 JSON 格式字符串！！！否则报错
							insertImgFn(result.data[0])
						}
					};
				}
			},
			//视频上传地址
			uploadVideoServer: {
				type: String,
				default () {
					return '';
				}
			},
			//视频大小限制
			uploadVideoMaxSize: {
				type: Number,
				default () {
					return 1 * 1024 * 1024 * 1024;
				}
			},
			//视频格式限制
			uploadVideoAccept: {
				type: Array,
				default () {
					return ['mp4'];
				}
			},
			//视频上传参数
			uploadVideoParams: {
				type: Object,
				default () {
					return null;
				}
			},
			//视频上传请求头
			uploadVideoHeaders: {
				type: Object,
				default () {
					return null;
				}
			},
			//视频上传文件名
			uploadVideoName: {
				type: String,
				default () {
					return '';
				}
			},
			//视频上传回调
			uploadVideoHooks: {
				type: Object,
				default () {
					return {
						// 上传视频之前
						before: function(xhr) {
							// 可阻止视频上传
							// return {
							// 	prevent: true,
							// 	msg: '需要提示给用户的错误信息'
							// }
						},
						// 视频上传并返回了结果，视频插入已成功
						success: function(xhr) {},
						// 视频上传并返回了结果，但视频插入时出错了
						fail: function(xhr, editor, resData) {},
						// 上传视频出错，一般为 http 请求的错误
						error: function(xhr, editor, resData) {},
						// 上传视频超时
						timeout: function(xhr) {},
						// 视频上传并返回了结果，想要自己把视频插入到编辑器中
						// 例如服务器端返回的不是 { errno: 0, data: { url : '.....'} } 这种格式，可使用 customInsert
						customInsert: function(insertVideoFn, result) {
							// result 即服务端返回的接口

							// insertVideoFn 可把视频插入到编辑器，传入视频 src ，执行函数即可
							insertVideoFn(result.data.url)
						}
					};
				}
			},
			//禁用
			disable: {
				type: Boolean,
				default () {
					return false;
				}
			},

		},
		model: {
			prop: 'value',
			event: 'onchange'
		},
		mounted() {
			this.editor = new E(this.$refs.editor) //创建富文本实例
			this.editor.config.onchange = (val) => {
				this.$emit("onchange", val);
			};
			//自定义菜单
			this.editor.config.menus = this.menus;
			// this.editor.config.onblur = function(val) {
			// 	this.$emit("onblur", val);
			// };
			// this.editor.config.onfocus = function(val) {
			// 	this.$emit("onfocus", val);
			// };
			// 图片文件上传限制。
			if (this.uploadImgMaxSize) {
				this.editor.config.uploadImgMaxSize = this.uploadImgMaxSize;
			}

			//图片base64模式
			if (this.uploadImgShowBase64) {
				this.editor.config.uploadImgShowBase64 = this.uploadImgShowBase64;
			}
			//图片限制类型
			if (this.uploadImgAccept) {
				this.editor.config.uploadImgAccept = this.uploadImgAccept;
			}

			// 一次最多上传 5 个图片
			if (this.uploadImgMaxLength) {
				this.editor.config.uploadImgMaxLength = this.uploadImgMaxLength;
			}
			//图片上传请求头
			if (this.uploadImgHeaders) {
				this.editor.config.uploadImgHeaders = this.uploadImgHeaders;
			}
			//图片上传参数
			if (this.uploadImgParams) {
				this.editor.config.uploadImgParams = this.uploadImgParams;
			}
			//图片上传地址
			if (this.uploadImgServer) {
				this.editor.config.uploadImgServer = this.uploadImgServer;
			}
			//图片文件名称
			if (this.uploadFileName) {
				this.editor.config.uploadFileName = this.uploadFileName;
			}
			//图片上传回调
			if (this.uploadImgHooks) {
				this.editor.config.uploadImgHooks = this.uploadImgHooks;
			}
			//视频上传地址
			if (this.uploadVideoServer) {
				this.editor.config.uploadVideoServer = this.uploadVideoServer;
			}
			//视频大小限制
			if (this.uploadVideoMaxSize) {
				this.editor.config.uploadVideoMaxSize = this.uploadVideoMaxSize;
			}
			//视频格式限制
			if (this.uploadVideoAccept) {
				this.editor.config.uploadVideoAccept = this.uploadVideoAccept;
			}
			//视频格式限制
			if (this.uploadVideoParams) {
				this.editor.config.uploadVideoParams = this.uploadVideoParams;
			}
			//视频上传请求头
			if (this.uploadVideoHeaders) {
				this.editor.config.uploadVideoHeaders = this.uploadVideoHeaders;
			}
			//视频上传文件名
			if (this.uploadVideoName) {
				this.editor.config.uploadVideoName = this.uploadVideoName;
			}
			//视频双穿回调
			if (this.uploadVideoHooks) {
				this.editor.config.uploadVideoHooks = this.uploadVideoHooks;
			}

			this.editor.create()
            this.editor.txt.html(this.value)

		},
		watch: {
			disable: {
				handler: function(val) {
					this.$nextTick(() => {
						if (val) {
							this.editor.disable();
						} else {
							this.editor.enable();
						}
					})

				},
				immediate: true,
				deep: true
			},
			 value: {
				handler: function(val) {
			 	
			 	},
			 	immediate: true,
			 	deep: true
			 },
		},
		methods: {
			//每次使用该组件一定都需要调用init 方法作为值的初始化
			init(val) {
				this.$nextTick(() => {
					if (val) {
						this.editor.txt.html(val);
					} else {
						this.editor.txt.html(this.value);
					}

				});
			},
			showHtml(str) {
				return str
					.replace(str ? /&(?!#?\w+;)/g : /&/g, '&amp;')
					.replace(/&lt;/g, "<")
					.replace(/&gt;/g, ">")
					.replace(/&quot;/g, "\"")
					.replace(/&#39;/g, "\'")
					.replace(/&amp;nbsp;/g, '\u3000')
			}
		}
	}
</script>

<style lang="scss" scoped="scoped">
	//修复wangEditor层级过高覆盖其他元素问题
	/deep/ .w-e-toolbar {
		z-index: 2 !important;
	}

	/deep/.w-e-text-container {
		z-index: 1 !important;
	}
</style>
