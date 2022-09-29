<template>
	<div class="tab-body">
		<el-row class="autoFlex" :gutter="10">
			<el-col :span="6">
				<el-card class="leftCard">
					<div slot="header" class="clearfix">
						<span>部门列表</span>
						<el-button @click="expandTree" class="expandBtn" type="primary" icon="el-icon-s-fold" size="small" style="margin-left: 20px;"></el-button>
					</div>
					<tree v-loading="treeLoading" ref="departmentTree" :items="departmentList" :defaultExpandAll="treeExpand"
					 :defaultParent="0" @nodeClick="treeClick"></tree>
				</el-card>
			</el-col>
			<el-col :span="18">
				<el-card class="rightCard">
					<div slot="header" class="clearfix"><span>子部门</span></div>
					<div class="operator">
						<el-button v-has-permission-code-and="'000000050005'" type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
					</div>
					<div class="dataTable autoFlex">
						<qlh-table class="table" :data="departmentTable" stripe border height="auto"  :excelProp="excelProp">
							<qlh-table-column prop="code" label="部门标识" sortable></qlh-table-column>
							<qlh-table-column prop="name" label="部门名" sortable :editable="false"></qlh-table-column>
							<qlh-table-column prop="sort" label="排序" sortable></qlh-table-column>
							<qlh-table-column prop="remarks" label="备注" show-overflow-tooltip></qlh-table-column>
							<qlh-table-column label="操作" fixed="right" width="300" ignored :editable="false">
								<template slot-scope="scope">
									<div class="dataTable-operator">
										<el-button v-has-permission-code-and="'000000050020'" size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
										</el-button>
										<el-button v-has-permission-code-and="'000000050010'" size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑
										</el-button>
										<el-button v-has-permission-code-and="'000000050015'" size="mini" type="danger" icon="el-icon-delete" @click="remove(scope.$index, scope.row)">删除
										</el-button>
									</div>
								</template>
							</qlh-table-column>
						</qlh-table>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<edit ref="edit"></edit>
	</div>
</template>
<script>
	import edit from './edit.vue';
	import tree from '@/components/tree/tree.vue';
	import api from '@/api/system/department/department.js';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	export default {
		name: 'department',
		components: {
			tree,
			edit,
			qlhTable,
			qlhTableColumn
		},
		data() {
			return {
				treeLoading: false,
				treeRef: 'departmentTree',
				treeExpand: true,
				departmentList: [],
				currentNode: {},
				departmentTable: [],
				tableLoading: true,
				editParameter: {},
				excelProp: {
					excelName: '部门信息',
					sheetname: '部门信息'
				}
			};
		},
		methods: {
			//获取数据
			list() {
				this.treeLoading = true;
				api.list({}, response => {
					this.departmentList = response.data;
					this.treeLoading = false;
					this.freshTable();
				})
			},
			expandTree() {
				this.treeExpand = !this.treeExpand;
				//控制el-tree展开收起
				for (var i = 0; i < this.$refs.departmentTree.getTree().store._getAllNodes().length; i++) {
					this.$refs.departmentTree.getTree().store._getAllNodes()[i].expanded = this.treeExpand;
				}
			},
			//点击部门
			treeClick(data) {
				if (this.currentNode == data) {
					this.currentNode = {};
					$('.el-tree-node').removeClass('is-current');
				} else {
					this.currentNode = data;
				}
				this.freshTable();
			},
			freshTable() {
				this.tableLoading = true;
				this.departmentTable = [];
				if (this.currentNode.id) {
					for (var d of this.departmentList) {
						if (d.pid == this.currentNode.id) {
							this.departmentTable.push(d);
						}
					}
				} else {
					for (var d of this.departmentList) {
						//根节点
						if (d.pid == 0) {
							this.departmentTable.push(d);
						}
					}
				}
				this.tableLoading = false;
			},
			//去添加
			toSave() {
				let parameter = {
					pid: this.currentNode.id != undefined ? this.currentNode.id : 0,
					level: this.currentNode.level != undefined ? this.currentNode.level + 1 : 0
				};

				this.$refs.edit.open(parameter, '添加');
			},
			//去编辑
			toUpdate(index, row) {
				let parameter = {
					id: row.id
				};
				this.$refs.edit.open(parameter, '编辑');
			},
			//去详情
			toDetail(index, row) {
				let detailParameter = {
					id: row.id
				};
				this.$refs.edit.open(detailParameter, '详情', true);
			},
			//删除
			remove(index, row) {
				let parameter = {
					ids: row.id
				}
				this.$utils.confirm.warning('提示', '确定删除吗？', () => {
					api.remove(
						parameter,
						response => {
							this.$utils.msg.success(response.msg);
							this.list();
						},
						response => {
							this.$utils.msg.warning(response.msg);
						}
					);
				});
			},
		},

		mounted() {
			this.list();
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
</style>
