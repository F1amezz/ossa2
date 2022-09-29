<template>
	<el-select ref="select" v-model="selectedValue" :multiple="multiple" clearable @change="change"
		@visible-change="visibleChange" @clear="clear" @remove-tag="removeTag" @blur="blur" @focus="focus">
		<el-option :key="selectedValue" :lable="selectedLable">
			<tree ref="tree" :items="items" :showCheckbox="multiple" :isCascade="true"
				:defaultExpandAll="defaultExpandAll" :expandOnClickNode="expandOnClickNode" @nodeClick="nodeClick"
				@checkboxNodeClick="checkboxNodeClick" @check="check" @nodeContextmenu="nodeContextmenu"
				@node-expand="nodeExpand" @node-collapse="nodeCollapse" :id="id" :label="label" :pid="pid"
				:defaultParent="defaultParent" :relationType="relationType"></tree>
		</el-option>
	</el-select>
</template>

<script>
	import tree from 'components/tree/tree.vue';
	export default {
		name: 'selectTree',
		components: {
			tree
		},
		data() {
			return {
				selectedValue: '',
				selectedLable: '',
			};
		},
		props: {
			//数据转换原数据的id名
			id: {
				type: String,
				default () {
					return 'id';
				}
			},
			//数据转换原数据的pid名
			pid: {
				type: String,
				default () {
					return 'pid';
				}
			},
			//数据转换原数据的label名
			label: {
				type: String,
				default () {
					return 'name';
				}
			},
			//数据转换原数据的defaultParent名
			defaultParent: {
				type: String | Number,
				default () {
					return null;
				}
			},
			//原数据
			items: {
				type: Array,
				default () {
					return [];
				}
			},
			//数据类型 1-pid 2-children
			dataType: {
				type: Number,
				default () {
					return 2;
				}
			},
			//树的类型
			multiple: {
				type: Boolean,
				default () {
					return false;
				}
			},
			checkedIds: {
				type: Array,
				default () {
					return [];
				}
			},
			//多选的关联类型  Y表示勾选时 N表示取消勾选时 p表示父关联子 s表示子关联父
			relationType: {
				type: Object,
				default () {
					return {
						Y: 'ps',
						N: 'ps'
					};
				}
			},
			//是否默认展开所有节点
			defaultExpandAll: {
				type: Boolean,
				default () {
					return false;
				}
			},
			//是否在点击节点的时候展开或者收缩节点
			expandOnClickNode: {
				type: Boolean,
				default () {
					return false;
				}
			}
		},
		methods: {
			//单选点击的回调
			nodeClick(data) {
				if (!this.multiple) {
					//选中回显
					this.selectedLable = data.name;
					//选中关闭下拉框
					this.$refs.select.blur();
					//选中后修改父组件选中值
					this.$emit('nodeClick', this.$refs.tree.getSelectedIds());
				}
			},
			//多选点击的回调 
			checkboxNodeClick(data) {
				this.$emit('checkboxNodeClick', this.$refs.selectTree.getSelectedIds());
			},
			//多选选择的回调
			check(data, checkedObj) {
				this.selectedLable = [];
				this.selectedData = [];
				if (_.isEmpty()) {
					for (let checked of checkedObj) {
						this.selectedValue.push(checked.id);
						this.selectedLable.push(checked.name);
					}
				}
				//父组件选中值
				this.$emit('check', data, checkedObj);
			},
			//获取选中的id
			getSelectedIds() {
				return this.$refs.tree.getSelectedIds();
			},
			//获取当前选中节点
			getSelecteNodes() {
				return this.$refs.tree.getSelecteNodes();
			},
			//选中值发生变化的回调
			change(val) {
				this.$emit('change', val);
			},
			//下拉框出现/隐藏的回调
			visibleChange(isShow) {
				this.$emit('visibleChange', isShow);
			},
			//多选模式下移除tag的回调
			removeTag(tab) {
				this.$emit('removeTag', tab);
			},
			//可清空的单选模式下用户点击清空按钮的回调
			clear() {
				this.$emit('clear');
			},
			//input 失去焦点的回调
			blur(event) {
				this.$emit('blur', event);
			},
			//input 获得焦点的回调
			focus(event) {
				this.$emit('focus', event);
			},
			//右键点击节点的回调
			nodeContextmenu(event, node, treeNode, cpn) {
				this.$emit('nodeContextmenu', event, node, treeNode, cpn);
			},
			//节点展开的回调
			nodeExpand(node, treeNode, cpn) {
				this.$emit('nodeExpand', node, treeNode, cpn);
			},
			//节点关闭的回调
			nodeCollapse(node, treeNode, cpn) {
				this.$emit('nodeCollapse', node, treeNode, cpn);
			},
			init() {
				if (this.multiple) {
					this.selectedLable = [];
					this.selectedValue = []
				} else {
					this.selectedLable = '';
				}
			},
		},
		watch: {
			// items(val) {
			// 	this.init();
			// },
			// checkedIds(val) {
			// 	this.$refs.selectTree.setSelectedIds(val);
			// 	this.changeShowText();
			// }
		},
		created() {
			this.$nextTick(() => {
				this.init();
			})
		}
	};
</script>


<style scoped>
	.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
		height: auto;
		padding: 0;
		overflow: hidden;
		overflow-y: auto;
	}

	.el-select-dropdown__item.selected {
		font-weight: normal;
	}

	ul li>>>.el-tree .el-tree-node__content {
		height: auto;
		padding: 0 20px;
	}

	.el-tree-node__label {
		font-weight: normal;
	}

	.el-tree>>>.is-current .el-tree-node__label {
		color: #409eff;
		font-weight: 700;
	}

	.el-tree>>>.is-current .el-tree-node__children .el-tree-node__label {
		color: #606266;
		font-weight: normal;
	}
</style>
