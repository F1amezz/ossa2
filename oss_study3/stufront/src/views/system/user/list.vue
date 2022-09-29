<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="姓名">
							<el-input v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="登录名">
							<el-input v-model="searchParams.lname"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="手机号">
							<el-input v-model="searchParams.mobile"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="邮件">
							<el-input v-model="searchParams.email"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="部门">
							<select-department-tree-dialog v-model="searchParams.did">
							</select-department-tree-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="角色">
							<select-role-tree-dialog v-model="searchParams.rid"></select-role-tree-dialog>
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
			<el-button type="primary" icon="el-icon-plus" @click="toSave()" v-has-permission-code-and="'000000000005'">
				添加</el-button>
			<el-button type="danger" icon="el-icon-delete" @click="batchRemove()" v-has-permission-code-and="'000000000015'">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<qlh-table class="table" ref="userTable" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
			 v-loading="listLoading" :tableToolButton="tableToolButton" :excelProp="excelProp">
				<qlh-table-column type="selection" width="55" fixed="left" ignored :editable="false"></qlh-table-column>
				<qlh-table-column prop="dname" label="部门" sortable :show="false"></qlh-table-column>
				<qlh-table-column prop="rname" label="角色" sortable></qlh-table-column>
				<qlh-table-column prop="name" label="姓名" sortable></qlh-table-column>
				<qlh-table-column prop="head" label="头像" width="80" ignored>
					<template slot-scope="scope">
						<el-avatar size="large" :src="scope.row.head" v-if="scope.row.head"></el-avatar>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="email" label="email"></qlh-table-column>
				<qlh-table-column prop="mobile" label="手机"></qlh-table-column>
				<qlh-table-column prop="dictionary_sex" label="性别"></qlh-table-column>
				<qlh-table-column label="操作" fixed="right" width="330" ignored :editable="false">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button v-has-permission-code-and="'000000000020'" size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<el-button v-has-permission-code-and="'000000000010'" size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑
							</el-button>
							<el-button v-has-permission-code-and="'000000000025'" size="mini" icon="el-icon-remove-outline" @click="toLeave(scope.$index, scope.row)">离职
							</el-button>
							<el-button v-has-permission-code-and="'000000000015'" size="mini" icon="el-icon-delete" type="danger" @click="remove(scope.$index, scope.row)">删除
							</el-button>
						</div>
					</template>
				</qlh-table-column>
			</qlh-table>
			<div class="pageBar">
				<el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total" :page-size="page.size"
				 :current-page="page.current" :layout="this.$constant.page.layout" :page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<edit ref="edit" :disabled="false"></edit>
	</div>
</template>
<script>
	import edit from './edit.vue';
	import selectRoleTreeDialog from '@/components/biz/selectRoleTreeDialog/selectRoleTreeDialog.vue';
	import selectDepartmentTreeDialog from '@/components/biz/selectDepartmentTreeDialog/selectDepartmentTreeDialog.vue';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	import api from '@/api/system/user/user.js';
	export default {
		name: 'user',
		components: {
			edit,
			selectRoleTreeDialog,
			selectDepartmentTreeDialog,
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
				showedCol: [],
				editableCol: [],
				/**
				 * 表格自定义工具栏按钮
				 * name:按钮名
				 * icon:按钮图标
				 * func:按钮点击事件
				 */
				tableToolButton: [{
					name: "customButton",
					icon: "el-icon-arrow-down",
					func: this.customButton
				}],
				excelProp: {
					excelName: '用户信息',
					sheetname: '部门信息'
				}
			};
		},
		methods: {
			customButton() {
				console.log("自定义事件");
			},
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
				this.$refs.edit.open({}, '添加');
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
			},toLeave(index, row){
             //离职
                let parameter = {
					id: row.id
				}
				this.$utils.confirm.warning('提示', '确定[ '+row.name+' ]离职吗？', () => {
					api.leave(
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
					api.remove(parameter,
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
