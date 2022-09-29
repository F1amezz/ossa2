<template>
	<div style="position: relative;">
		<div class="toolBarBtn" style="width: 100%;height:15px;cursor: pointer;text-align: center;" @click="showToolBar=!showToolBar">
			<i :class="showToolBar?'el-icon-arrow-down':'el-icon-arrow-up'" style="color: #b0b1b4;"></i>
		</div>
		<el-collapse-transition>
			<div class="toolBar" style="width: 100%;height: 30px;border: 1px solid #EBEEF5;" :style='{"text-align":toolButtonDirection}'
			 v-if="showToolBar">
				<edit-col-button v-bind="$attrs" v-on="$listeners" title="自定义列"></edit-col-button>
				<el-button size="mini" icon="el-icon-printer" @click="print()" title="打印"></el-button>
				<el-button size="mini" icon="el-icon-download" @click="exportExcel()" title="导出"></el-button>
				<el-button v-for="(v,i) in tableToolButton" size="mini" :icon="v.icon" @click="v.func()" :title="v.name"></el-button>
			</div>
		</el-collapse-transition>

	</div>
</template>

<script>
	import editColButton from './editColButton.vue';
	export default {
		name: 'tableToolBar',
		components: {
			editColButton
		},
		inheritAttrs: false,
		data() {
			return {
				showToolBar: false,
				excelAttr: {
					excelName: "表格",
					sheetname: "sheet1"
				},
				printAttr: {
					printTitle: "table",
					printTitleStyle: "",
					printStyle: "table{border:1px solid #000000;border-collapse: collapse;text-align:center}table td{border:1px solid #000000;}",
					callback: () => {}

				}

			};
		},
		props: {
			tableId: {
				type: String,
				default () {
					return "";
				},
				required: true
			},
			excelProp: {
				type: Object,
				default () {
					return {};
				},
			},
			printProp: {
				type: Object,
				default () {
					return {};
				}
			},
			/**
			 * 表格自定义工具栏按钮
			 * name:按钮名
			 * icon:按钮图标
			 * func:按钮点击事件
			 */
			tableToolButton: {
				type: Array,
				default () {
					return [];
				}
			},
			/**
			 * 表格工具栏按钮位置
			 * 1-左，2-右
			 */
			toolButtonDirection: {
				type: String,
				default () {
					return "right"
				}
			},
			/**
			 * 表格渲染后左边fixed的表格classname
			 */
			fixedLeftColClassName: {
				type: String,
				default () {
					return "el-table__fixed";
				},
			},
			/**
			 * 表格渲染后右边fixed的表格classname
			 */
			fixedRightColClassName: {
				type: String,
				default () {
					return "el-table__fixed-right";
				},
			}
		},
		methods: {
			initTable() {
				//删除多余的内容
				var fix = document.querySelector("#" + this.tableId).querySelector('.' + this.fixedLeftColClassName);
				var fixRight = document.querySelector("#" + this.tableId).querySelector('.' + this.fixedRightColClassName);
				if (fix) {
					document.querySelector("#" + this.tableId).removeChild(fix);
				}
				if (fixRight) {
					document.querySelector("#" + this.tableId).removeChild(fixRight);
				}
				$("#" + this.tableId).find("td").remove(".ignored");
				$("#" + this.tableId).find("th").remove(".ignored");
			},
			exportExcel() {
				this.initTable();
				this.$utils.file.generateExcel(document.querySelector("#" + this.tableId), this.excelAttr);
			},
			print() {
				this.initTable();
				this.printHTML(this.tableId);
			},
			printHTML(id) {
				const html = document.querySelector('#' + id).innerHTML
				// 新建一个 DOM
				const div = document.createElement('div')
				const printDOMID = 'printDOMElement'
				div.id = printDOMID
				div.innerHTML = html

				// 提取第一个表格的内容 即表头
				const ths = div.querySelectorAll('.el-table__header-wrapper th')
				const ThsTextArry = []
				for (let i = 0, len = ths.length; i < len; i++) {
					if (ths[i].innerText !== '') ThsTextArry.push(ths[i].innerText)
				}

				// 删除多余的表头
				div.querySelector('.hidden-columns').remove()
				// 第一个表格的内容提取出来后已经没用了 删掉
				div.querySelector('.el-table__header-wrapper').remove()

				// 将第一个表格的内容插入到第二个表格
				let newHTML = '<tr>'
				for (let i = 0, len = ThsTextArry.length; i < len; i++) {
					newHTML += '<td style="text-align: center; font-weight: bold">' + ThsTextArry[i] + '</td>'
				}

				newHTML += '</tr>'
				div.querySelector('.el-table__body-wrapper table').insertAdjacentHTML('afterbegin', newHTML)
				// 将新的 DIV 添加到页面 打印后再删掉
				document.querySelector('body').appendChild(div);
				this.$utils.print.printHtml(printDOMID, this.printAttr);
				div.remove();
			},
			init() {
				//初始化excelAttr和printAttr
				this.excelAttr=Object.assign(this.excelAttr,this.excelProp);
				this.printAttr=Object.assign(this.printAttr,this.printProp);
				
			}

		},
		mounted() {
			this.init();
		}
	};
</script>

<style scoped="scoped" lang="scss">
	.el-button {
		margin-left: 0 !important;
	}
</style>
