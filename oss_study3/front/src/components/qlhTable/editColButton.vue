<template>
	<el-popover placement="right" width="100" trigger="click">
		<div>
			<el-checkbox :indeterminate="halfCheck" v-model="checkAll" @change="changeCheckAll">全选</el-checkbox>
			<el-checkbox-group v-model="checkedValList" @change="changeCheck">
				<el-checkbox v-for="(v,i) in colList" :label="v.value">{{v.label}}</el-checkbox>
			</el-checkbox-group>
		</div>
		<el-button slot="reference" icon="el-icon-tickets" size="mini">
		</el-button>
	</el-popover>
</template>

<script>
	export default {
		name: 'editColButton',
		components: {},
		data() {
			return {
				checkAll: this.showedCol.length == this.editableCol.length,
				colList: this.editableCol,
				checkedList: this.showedCol,
				checkedValList: [],
				halfCheck: this.showedCol.length < this.editableCol.length && this.showedCol.length > 0
			};
		},
		props: {
			//可编辑的列中目前展示的列
			showedCol: {
				type: Array,
				default () {
					return [];
				}
			},
			//可编辑的列
			editableCol: {
				type: Array,
				default () {
					return [];
				}
			}
		},
		methods: {
			init() {
				this.changeCheckedValListByCheckedList();
			},
			changeCheckedValListByCheckedList() {
				this.checkedValList = [];
				for (var item of this.checkedList) {
					this.checkedValList.push(item.value);
				}
				this.$emit("changeShowedCol", this.checkedList);
			},
			changeCheckedListByCheckedValList() {
				this.checkedList = [];
				for (var val of this.checkedValList) {
					for (var item of this.colList) {
						if (val == item.value) {
							this.checkedList.push(item);
						}
					}
				}
				this.$emit("changeShowedCol", this.checkedList);
			},
			changeCheckAll(val) {
				this.checkedList = val ? this.colList : [];
				this.changeCheckedValListByCheckedList();
				this.halfCheck = false;
			},
			changeCheck(val) {
				let checkedCount = val.length;
				this.checkAll = checkedCount === this.colList.length;
				this.halfCheck = checkedCount > 0 && checkedCount < this.colList.length;
				this.changeCheckedListByCheckedValList();
			}
		},
		watch: {},
		mounted() {
			this.init();
		}
	};
</script>

<style>
</style>
