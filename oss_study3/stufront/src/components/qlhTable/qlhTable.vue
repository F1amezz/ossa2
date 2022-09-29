<template>
	<div style="display: flex;flex-direction: column;">
		<table-tool-bar ref="tableTool" v-if="showTableTool" :tableId="tableId" :showedCol="showedCol" :editableCol="editableCol"
		 @changeShowedCol="changeShowedCol" v-bind="$attrs" v-on="$listeners"></table-tool-bar>
		<el-table v-bind="$attrs" v-on="$listeners" style="flex: auto;">
			<slot></slot>
		</el-table>
		<el-table :id="tableId" v-bind="$attrs" v-on="$listeners" style="display:none">
			<slot></slot>
		</el-table>
	</div>
</template>

<script>
	import shortid from "shortid"
	import tableToolBar from './tableToolBar.vue';
	export default {
		name: 'qlhTable',
		components: {
			tableToolBar
		},
		inheritAttrs: false,
		data() {
			return {
				//可编辑的列中目前展示的列，由qlhTableColumn组件进行初始化
				showedCol: [],
				//可编辑的列，由qlhTableColumn组件进行初始化
				editableCol: [],
				tableId: shortid.generate()
			};
		},
		props: {
			showTableTool: {
				type: Boolean,
				default () {
					return true;
				},
			}
		},
		methods: {
			getAllShowedCol() {
				return this.showedCol;
			},
			getAllEditableCol() {
				return this.editableCol;
			},
			//列显示改变的回显
			changeShowedCol(val) {
				this.showedCol = val;
				var columns = this.$slots.default;
				for (var item of columns) {
					item.child.changeShow();
				}
			},
			print() {
				this.$refs.tableTool.print();
			},
			exportExcel() {
				this.$refs.tableTool.exportExcel();
			}
		},
		mounted() {}
	};
</script>

<style>
</style>
