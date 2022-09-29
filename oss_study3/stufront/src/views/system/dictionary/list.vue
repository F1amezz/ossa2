<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="字典名称">
							<el-input v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="字典编号">
							<el-input v-model="searchParams.dkey"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="状态">
							<el-select v-model="searchParams.isdisable" placeholder="状态">
								<el-option label="启用" value="1"></el-option>
								<el-option label="禁用" value="2"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-collapse-transition>
						<div v-if="isShowMoreSearch">
							<!--隐藏区域-->
						</div>
					</el-collapse-transition>
					<div class="search-btn">
						<el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
						<el-button @click="clearSearchVal">清空</el-button>
						<el-button type="text" v-if="!isShowMoreSearch" @click="isShowMoreSearch = !isShowMoreSearch">
							更多
							<i class="el-icon-arrow-down"></i>
						</el-button>
						<el-button type="text" v-else @click="isShowMoreSearch = !isShowMoreSearch">
							收起
							<i class="el-icon-arrow-up"></i>
						</el-button>
					</div>
				</el-row>
			</el-form>
		</div>
		<div class="operator">
			<el-button v-has-permission-code-and="'000000200005'" type="primary" icon="el-icon-plus" @click="toSave()">
				添加</el-button>
			<el-button v-has-permission-code-and="'000000200015'" type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<qlh-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
			 v-loading="listLoading" :excelProp="excelProp">
				<qlh-table-column type="selection" width="55" fixed="left" ignored :editable="false"> </qlh-table-column>
				<qlh-table-column prop="name" label="字典名称" sortable :editable="false"></qlh-table-column>
				<qlh-table-column prop="dkey" label="字典编号" sortable :editable="false"></qlh-table-column>
				<qlh-table-column prop="dvalue" label="属性值" sortable :editable="false"></qlh-table-column>
				<qlh-table-column prop="description" label="描述"></qlh-table-column>
				<qlh-table-column prop="remarks" label="备注"></qlh-table-column>
				<qlh-table-column prop="isdisable" label="启用" ignored :editable="false">
					<template slot-scope="scope">
						<el-switch v-model="scope.row.isdisable" :active-value="1" :inactive-value="2" disabled>
						</el-switch>
					</template>
				</qlh-table-column>
				<qlh-table-column label="操作" fixed="right" width="300" ignored :editable="false">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button v-has-permission-code-and="'000000200020'" size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<el-button v-has-permission-code-and="'000000200005'" size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑</el-button>
							<el-button v-has-permission-code-and="'000000200015'" size="mini" icon="el-icon-delete" type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
						</div>
					</template>
				</qlh-table-column>
			</qlh-table>
			<div class="pageBar">
				<el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total" :page-size="page.size"
				 :current-page="page.current" :layout="this.$constant.page.layout" :page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<edit ref="edit"></edit>
	</div>
</template>
<script>
	import edit from './edit.vue';
	import api from '@/api/system/dictionary/dictionary.js';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	export default {
		name: 'dictionary',
		components: {
			edit,
			qlhTable,
			qlhTableColumn
		},
		data() {
			return {
				searchParams: {},
				isShowMoreSearch: false,
				listLoading: false,
				listData: [],
				selected: [],
				page: new this.$constant.pageObj(),
				excelProp: {
					excelName: '数据字典信息',
					sheetname: '数据字典信息'
				}
			};
		},
		methods: {
			//获取数据
			list() {
				this.listLoading = true;
				let params = this.$utils.merger(this.searchParams, this.page);
				api.list(params, response => {
					this.listData = response.data;
					this.listLoading = false;
				})
			},
			//去添加
			toSave() {
				this.$refs.edit.open({}, '添加', false);
			},
			//去编辑
			toUpdate(index, row) {
				let editParameter = {
					id: row.id
				};
				this.$refs.edit.open(editParameter, '编辑');
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
			//批量删除
			batchRemove() {
				if (!this.selected || this.selected.length == 0) {
					this.$utils.msg.info('请选择至少一行');
					return;
				}
				let parameter = {
					ids: this.selected
				};
				this.$utils.confirm.warning('提示', '确定删除吗？', () => {
					api.remove({
							ids: this.selected
						},
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
			//改变选择项
			selectionChange(val) {
				//清空
				this.selected = [];
				for (let item of val) {
					this.selected.push(item.id);
				}
			},
			//改变每页显示数量
			sizeChange(val) {
				this.page.size = val;
				this.list();
			},
			//改变现在的页码
			currentChange(val) {
				this.page.current = val;
				this.list();
			},

			//搜索
			search() {
				this.list();
			},
			//清空搜索框
			clearSearchVal() {
				this.searchParams = {};
			}
		},
		mounted() {
			this.list();
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
</style>
