<template>
	<div>
		<el-input v-if="filterable" placeholder="输入关键字进行过滤" v-model="filterText"> </el-input>
		<el-tree class="filter-tree" ref="tree" :node-key="id" check-strictly :data="treeData" :show-checkbox="showCheckbox"
		 :default-expand-all="defaultExpandAll" :expand-on-click-node="expandOnClickNode" @node-click="nodeClick" @check="check"
		 @node-contextmenu="nodeContextmenu" @node-expand="nodeExpand" @node-collapse="nodeCollapse" :filter-node-method="filterNode"></el-tree>
	</div>
</template>

<script>
	import utils from 'util/utils.js';
	export default {
		name: 'tree',
		data() {
			return {
				filterText: '',
				treeData: [],
				selectedIds: []
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
			//已选中的
			checkedIds: {
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
			},
			filterable: {
				type: Boolean,
				default () {
					return false;
				}
			},
		},
		methods: {
			//获取原生的组件本身
			getTree() {
				return this.$refs.tree;
			},
			//获取当前选中节点id
			getSelectedIds() {
				return this.selectedIds;
			},
			//获取当前选中节点
			getSelecteNodes() {
				if (this.showCheckbox) {
					return this.$refs.tree.getCheckedNodes(false, true);
				} else {
					let tmp = this.$refs.tree.getCurrentNode();
					if (_.isEmpty(tmp)) {
						return [];
					} else {
						return [tmp];
					}

				}
			},
			//设置选中节点
			setSelectedIds(selectedIds) {
				this.selectedIds = selectedIds;
				if (this.showCheckbox) {
					this.$refs.tree.setCheckedKeys(this.selectedIds);
				} else {
					if (_.isEmpty(this.selectedIds)) {
						this.$refs.tree.setCurrentKey();
					} else {
						this.$refs.tree.setCurrentKey(this.selectedIds[0]);
					}
				}
			},
			//根据id找到节点
			getNodeById(id) {
				if (id == this.defaultParent) {
					return null;
				} else {
					return this.findNode(this.treeData, id);
				}
			},
			//遍历寻找节点
			findNode(items, id) {
				if(items && items.length>0){
					for (let item of items) {
						if (id == item.id) {
							return item;
						}
						let node = this.findNode(item.children, id);
						if (node != null) {
							return node;
						}
					}
				}
				return null;
			},
			//获取所有子节点的id
			getAllChildrenId(id) {
				return this.getChildId(this.getNodeById(id));
			},
			//获取所有子节点
			getAllChildrenNode(id) {
				return this.getChild(this.getNodeById(id));
			},
			//遍历获取子节点id
			getChildId(node) {
				let ids = [];
				if (node != null && node.children && node.children.length >0) {
					for (let child of node.children) {
						//插入子节点
						ids.push(child.id);
						ids = ids.concat(this.getChildId(child));
					}
				}
				return ids;
			},
			//遍历获取子节点
			getChild(node) {
				let children = [];
				if (node != null && node.children && node.children.length >0) {
					for (let child of node.children) {
						//插入子节点
						children.push(child);
						children = children.concat(this.getChild(child));
					}
				}
				return children;
			},
			//获取所有父节点id
			getAllParentsId(id) {
				return this.getParentId(this.getNodeById(id));
			},
			//获取所有父节点
			getAllParentsNode(id) {
				return this.getParent(this.getNodeById(id));
			},
			//遍历获取父节点id
			getParentId(node) {
				let ids = [];
				if (node != null) {
					if (node.pid != this.defaultParent) {
						//插入父节点
						ids.push(node.pid);
						ids = ids.concat(this.getParentId(this.getNodeById(node.pid)));
					}
				}
				return ids;
			},
			//遍历获取父节点
			getParent(node) {
				let parents = [];
				if (node != null) {
					if (node.pid != this.defaultParent) {
						//插入父节点
						parents.push(this.getNodeById(node.pid));
						parents = parents.concat(this.getParent(this.getNodeById(node.pid)));
					}
				}
				return parents;
			},
			// 点击回调
			nodeClick(data) {
				//单选
				if (!this.showCheckbox) {
					this.selectedIds = [data.id];
					this.$emit('nodeClick', data);
					//多选
				} else {
					this.$emit('checkboxNodeClick', data);
				}
			},
			//多选时复选框点击回调
			check(data, checkedObj) {
				let isChecked = checkedObj.checkedKeys.indexOf(data.id) == -1 ? false : true;
				let Yes = this.relationType.Y;
				let No = this.relationType.N;
				//勾选时
				if (isChecked) {
					this.selectedIds = utils.array.union(this.selectedIds, [data.id]);
					//父关联子
					if (Yes.indexOf('p') > -1) {
						this.selectedIds = utils.array.union(this.selectedIds, this.getAllChildrenId(data.id));
					}
					//子关联父
					if (Yes.indexOf('s') > -1) {
						this.selectedIds = utils.array.union(this.selectedIds, this.getAllParentsId(data.id));
					}
					//取消勾选时
				} else {
					if (this.selectedIds.indexOf(data.id) > -1) {
						this.selectedIds.splice(this.selectedIds.indexOf(data.id), 1);
					}
					//父关联子
					if (No.indexOf('p') > -1) {
						this.selectedIds = utils.array.diff(this.selectedIds, this.getAllChildrenId(data.id));
					}
					//子关联父
					if (No.indexOf('s') > -1) {
						var parentids = this.getAllParentsId(data.id);
						for (let parentid of parentids) {
							var childrenIds = this.getAllChildrenId(parentid);
							if (utils.array.intersection(this.selectedIds, childrenIds).length == 0) {
								if (this.selectedIds.indexOf(parentid) > -1) {
									this.selectedIds.splice(this.selectedIds.indexOf(parentid), 1);
								}
							}
						}
					}
				}
				this.setSelectedIds(this.getSelectedIds());
				this.$emit('check', data, this.getSelecteNodes());
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
				if (this.items) {
					//转换数据格式
					let tmp = this.items;
					if (this.dataMode == "simple") {
						tmp = utils.convertSimpleTreeToStandardTree(
							tmp, {
								id: this.id,
								pid: this.pid,
								defaultParent: this.defaultParent,
								label: this.label
							}, {
								label: 'label'
							}
						);
					}
					this.treeData = tmp;
				}
			},
			filterNode(value, data) {
				if (!value) return true;
				return data.label.indexOf(value) !== -1;
			}
		},
		watch: {
			items() {
				this.init();
			},
			checkedIds(val) {
				this.setSelectedIds(Array.from(val));
			},
			filterText(val) {
				this.$refs.tree.filter(val);
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
