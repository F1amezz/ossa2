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
						'table',
						'code',
						'splitLine',
						'undo',
						'redo',
					];
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
			
			this.editor.create()
            this.editor.txt.html(this.value);
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
					 this.editor.txt.html(val);
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
			},
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
