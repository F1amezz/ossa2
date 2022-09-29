<template>
	<div class="tab-body">
		<el-row class="autoFlex" :gutter="10">
			<el-col :span="6">
				<el-card class="leftCard">
					<div slot="header" class="clearfix">
						<span>地区列表</span>
						<!-- 	<el-button @click="expandTree" class="expandBtn" type="primary" icon="el-icon-s-fold" size="small" style="margin-left: 20px;"></el-button> -->
					</div>
					<tree v-loading="treeLoading" ref="areaTree" :items="areaTree" dataMode="standard" :defaultParent="defaultParent"
					 @nodeClick="treeClick"></tree>
				</el-card>
			</el-col>
			<el-col :span="18">
				<el-card class="rightCard">
					<div slot="header" class="clearfix"><span>子地区</span></div>
					<div class="operator">
						<el-button v-has-permission-code-and="'000000450000'" type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
					</div>
					<div class="dataTable autoFlex">
						<qlh-table class="table" style="height: auto" :data="areaTable" stripe border height="auto" :excelProp="excelProp">
							<qlh-table-column prop="id" label="区域编号" sortable></qlh-table-column>
							<qlh-table-column prop="name" label="区域名称" :editable="false"></qlh-table-column>
							<qlh-table-column prop="pid" label="区域上级标识" sortable></qlh-table-column>
							<qlh-table-column prop="simplename" label="地名简称" :editable="false"></qlh-table-column>
							<qlh-table-column prop="level" label="区域等级" :formatter="
										(row, column) => {
											return row.level == 0 ? '国家' : row.level == 1 ? '省份' : row.level == 2 ? '城市' : '县区';
										}
									"></qlh-table-column>
							<qlh-table-column prop="citycode" label="城市编码"></qlh-table-column>
							<qlh-table-column prop="zipcode" label="邮政编码"></qlh-table-column>
							<qlh-table-column prop="mername" label="组合名称" :editable="false"></qlh-table-column>
							<qlh-table-column prop="lng" label="经度" sortable></qlh-table-column>
							<qlh-table-column prop="lat" label="纬度" sortable></qlh-table-column>
							<qlh-table-column prop="pinyin" label="拼音" sortable :editable="false"></qlh-table-column>

							<qlh-table-column label="操作" fixed="right" width="300" ignored :editable="false">
								<template slot-scope="scope">
									<div class="dataTable-operator">
										<el-button v-has-permission-code-and="'000000450015'" size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
										</el-button>
										<el-button v-has-permission-code-and="'000000450005'" size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑
										</el-button>
										<el-button v-has-permission-code-and="'000000450010'" size="mini" type="danger" icon="el-icon-delete" @click="remove(scope.$index, scope.row)">删除
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
	import api from '@/api/system/area/area.js';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	export default {
		name: 'areaList',
		components: {
			tree,
			edit,
			qlhTable,
			qlhTableColumn
		},
		data() {
			return {
				treeLoading: false,
				treeRef: 'areaTree',
				treeExpand: true,
				areaList: [],
				areaTree: [],
				currentNode: {},
				areaTable: [],
				tableLoading: true,
				editParameter: {},
				defaultParent: 100000,
				excelProp: {
					excelName: '地区信息',
					sheetname: '地区信息'
				}
			};
		},
		methods: {
			//获取数据
			list() {

				this.treeLoading = true;
				let areas = this.$caches.areas.get();
				if (areas) {
					this.areaList = areas;
					this.areaTree = this.$caches.areasStandard.get();
					this.treeLoading = false;
					this.freshTable();
				} else {
					api.list({}, response => {
						this.$caches.areas.set(response.data);
						this.$caches.areasStandard.remove();
						let tmp = this.$utils.convertSimpleTreeToStandardTree(response.data, {
							id: "id",
							pid: "pid",
							defaultParent: 100000,
							label: "name"
						});
						this.$caches.areasStandard.set(tmp);
						this.areaTree = tmp;
						this.areaList = response.data;
						this.treeLoading = false;
						this.freshTable();
					})
				}

			},
			expandTree() {
				this.treeExpand = !this.treeExpand;
				//控制el-tree展开收起
				for (var i = 0; i < this.$refs.areaTree.getTree().store._getAllNodes().length; i++) {
					this.$refs.areaTree.getTree().store._getAllNodes()[i].expanded = this.treeExpand;
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
				this.areaTable = [];
				if (this.currentNode.id) {
					for (var d of this.areaList) {
						if (d.pid == this.currentNode.id) {
							this.areaTable.push(d);
						}
					}
				} else {
					for (var d of this.areaList) {
						//根节点
						if (d.pid == this.defaultParent) {
							this.areaTable.push(d);
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
							this.$caches.areas.remove();
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
