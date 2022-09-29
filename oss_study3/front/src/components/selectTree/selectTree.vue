<template>
	<el-select v-model="showText" ref="select" :multiple="showCheckbox" @change="change" @visible-change="visibleChange"
	 @clear="clear" @remove-tag="removeTag" @blur="blur" @focus="focus">
		<el-option :value="showText">
			<tree ref="selectTree" :items="treeData" :dataMode="dataMode"  :checkedIds="checkedIds" :showCheckbox="showCheckbox" :isCascade="true"
			 :defaultExpandAll="defaultExpandAll" :expandOnClickNode="expandOnClickNode" @nodeClick="nodeClick"
			 @checkboxNodeClick="checkboxNodeClick" @check="check" @nodeContextmenu="nodeContextmenu" @node-expand="nodeExpand"
			 @node-collapse="nodeCollapse" :id="id" :label="label" :pid="pid" :defaultParent="defaultParent" :relationType="relationType"></tree>
		</el-option>
	</el-select>
</template>

<script>
	import utils from 'util/utils.js';
	import tree from 'components/tree/tree.vue';
	export default {
		name: 'selectTree',
		//上面是父组件可传入参数
		data() {
			return {
				treeData: [],
				showText: ''
			};
		},
		components: {
			tree
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
			//数据类型  simple-简单数据、standard-标准
			dataMode: {
				type: String,
				default () {
					return "simple";
				}
			},
			//树的类型
			showCheckbox: {
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
			//获取现在的已选择id数组
			getSelectedIds() {
				return this.$refs.selectTree.getSelectedIds();
			},
			//单选点击的回调
			nodeClick(data) {
				//选中回显
				this.showText = data.label;
				//选中关闭下拉框
				this.$refs.select.blur();
				//选中后修改父组件选中值
				this.$emit('nodeClick', this.$refs.selectTree.getSelectedIds());
			},
			//多选点击的回调
			checkboxNodeClick(data) {
				this.$emit('checkboxNodeClick', this.$refs.selectTree.getSelectedIds());
			},
			//多选选择的回调
			check(data, CheckedObj) {
				this.changeShowText();
				//选中后修改父组件选中值
				this.$emit('check', data, CheckedObj);
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
			//已选中回显
			changeShowText() {
				//判断选择类型
				if (!this.showCheckbox) {
					this.showText = '';
					//单选
					for (let item of this.items) {
						if (item[this.id] == this.$refs.selectTree.getSelectedIds()[0]) {
							this.showText = item[this.label];
						}
					}
				} else {
					//多选
					this.showText = [];
					for (let item of this.items) {
						for (let id of this.$refs.selectTree.getSelectedIds()) {
							if (item.id == id) {
								this.showText.push(item.name);
							}
						}
					}
				}
			},
			init() {
				if (this.items) {
					this.treeData = this.items;
				}
			}
		},
		watch: {
			items(val) {
				this.init();
			},
			checkedIds(val) {
				this.$refs.selectTree.setSelectedIds(val);
				this.changeShowText();
			}
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
