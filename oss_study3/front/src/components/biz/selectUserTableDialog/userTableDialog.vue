<template>
	<popup ref="popup" :loading="popupLoading" :append-to-body="true">
		<div slot="body" style="position: relative;height: 100%;margin-left: 5px; margin-right: 5px;">
			<div class="tab-body">
				<el-row class="autoFlex" :gutter="10">
					<el-col :span="14">
						<el-card class="leftCard">
							<div class="search">
								<el-form ref="form" :model="searchParams" label-width="80px">
									<el-row>
										<el-col :span="8">
											<el-form-item label="姓名">
												<el-input v-model="searchParams.name"></el-input>
											</el-form-item>
										</el-col>
										<el-col :span="8">
											<el-form-item label="登录名">
												<el-input v-model="searchParams.lname"></el-input>
											</el-form-item>
										</el-col>
										<el-col :span="8">
											<el-form-item label="手机">
												<el-input v-model="searchParams.mobile"></el-input>
											</el-form-item>
										</el-col>
										<el-collapse-transition>
											<div v-if="isShowMoreSearch">
												<!--隐藏区域-->
												<el-col :span="8">
													<el-form-item label="部门">
														<select-department-tree-dialog v-model="searchParams.did">
														</select-department-tree-dialog>
													</el-form-item>
												</el-col>
												<el-col :span="8">
													<el-form-item label="角色">
														<select-role-tree-dialog v-model="searchParams.rid">
														</select-role-tree-dialog>
													</el-form-item>
												</el-col>
											</div>
										</el-collapse-transition>
										<div class="search-btn">
											<el-button type="primary" icon="el-icon-search" @click="search">搜索
											</el-button>
											<el-button @click="clearSearchVal">清空</el-button>
											<el-button type="text" v-if="!isShowMoreSearch"
												@click="isShowMoreSearch = !isShowMoreSearch">
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
							<div class="dataTable autoFlex" v-if="selectListData">
								<el-table ref="selectTable" class="table" :data="selectListData" stripe border
									height="800" @select="selectData" @select-all="selectAllData" highlight-current-row
									@current-change="handleCurrentChange" v-loading="selectListLoading">
									<el-table-column v-if="multiple" type="selection" width="55" fixed="left">
									</el-table-column>
									<el-table-column prop="dname" label="部门" sortable></el-table-column>
									<el-table-column prop="rname" label="角色" sortable></el-table-column>
									<el-table-column prop="name" label="姓名" width="120" sortable></el-table-column>
									<el-table-column prop="lname" label="登录名" width="120" sortable></el-table-column>
									<el-table-column prop="mobile" label="手机"></el-table-column>
									<el-table-column prop="sex" label="性别" width="100" :formatter="
			 					(row, column) => {
			 						return row.sex == 1 ? '男' : '女';
			 					}"></el-table-column>
									<el-table-column prop="birth" label="生日"></el-table-column>
								</el-table>
								<div class="pageBar">
									<el-pagination @size-change="sizeChange" @current-change="currentChange"
										:total="selectTotal" :page-size="page.size" :current-page="page.current"
										:layout="this.$constant.page.layout"
										:page-sizes="this.$constant.page.pageSizes"></el-pagination>
								</div>
							</div>
						</el-card>
					</el-col>
					<el-col :span="10">
						<el-card class="rightCard">
							<div slot="header" class="clearfix"><span>已选择</span></div>
							<div class="dataTable autoFlex">
								<el-table class="table" :data="selectedListData" stripe border height="auto">
									<el-table-column prop="name" label="姓名" width="120" sortable></el-table-column>
									<el-table-column prop="lname" label="登录名" width="120" sortable></el-table-column>
									<el-table-column prop="email" label="email"></el-table-column>
									<el-table-column prop="mobile" label="手机"></el-table-column>
									<el-table-column label="操作" fixed="right" width="100px">
										<template slot-scope="scope">
											<div class="dataTable-operator">
												<el-button size="mini" icon="el-icon-delete" type="danger"
													@click="remove(scope.$index, scope.row)">删除
												</el-button>
											</div>
										</template>
									</el-table-column>
								</el-table>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</div>
		</div>
		<div slot="footer">
			<el-button type="primary" @click="confirm">确 定</el-button>
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
	import popup from '@/components/popup/dialogPopup.vue';
	import api from '@/api/components/biz/selectUserTableDialog/selectUserTableDialog.js';
	import selectRoleTreeDialog from '@/components/biz/selectRoleTreeDialog/selectRoleTreeDialog.vue';
	import selectDepartmentTreeDialog from '@/components/biz/selectDepartmentTreeDialog/selectDepartmentTreeDialog.vue';
	export default {
		name: 'userTableDialog',
		components: {
			popup,
			selectRoleTreeDialog,
			selectDepartmentTreeDialog
		},
		data() {
			return {
				popupLoading: false,
				searchParams: {},
				isShowMoreSearch: false,
				selectListLoading: false,
				selectListData: [],
				selectTotal: 0,
				selectedListData: [],
				page: new this.$constant.pageObj(),
			};
		},
		props: {
			multiple: {
				type: Boolean,
				default: false,
				required: false
			},
		},
		methods: {
			handleCurrentChange(row) {
				if (!this.multiple) {
					if (row) {
						this.selectedListData = [];
						this.selectedListData[0] = _.clone(row);
					}
				}
			},
			confirm() {
				this.$emit("confirm", this.selectedListData);
				this.$refs.popup.close();
			},
			selectAllData(selection) {
				if (!_.isEmpty(selection)) {
					selection.forEach(row => {
						this.addSelected(selection, row);
					});
				} else {
					this.selectListData.forEach(row => {
						this.removeSelected(row);
					});
				}

			},
			selectData(selection, row) {
				this.addSelected(selection, row);
			},
			remove(index, row) {
				this.removeSelected(row);
			},
			addSelected(selection, row) {
				//查看已选择是否存在
				let index = _.findIndex(this.selectedListData, function(o) {
					return o.id == row.id;
				});
				if (index >= 0) {
					//选中后删除
					let index1 = _.findIndex(selection, function(o) {
						return o.id == row.id;
					});
					if (index1 < 0) {
						this.selectedListData.splice(index, 1);
					}
				} else {
					this.selectedListData.push(row);
				}
			},
			//删除选中
			removeSelected(row) {
				let index = _.findIndex(this.selectedListData, function(o) {
					return o.id == row.id;
				});
				if (index >= 0) {
					this.selectedListData.splice(index, 1);
				}
				this.toggleSelection()
			},
			//获取数据
			list() {

				this.selectListLoading = true;
				let params = this.$utils.merger(this.searchParams, this.page);
				api.list(params, response => {
					this.selectListData = response.data.records;
					this.selectTotal = response.data.total;
					this.selectListLoading = false;
					this.toggleSelection()
				});

			},
			/**
			 * 同步已选择和选择表的选择状态
			 */
			toggleSelection() {
				let that = this;
				if (this.multiple) {
					//清空所有选项
					that.$nextTick(function() {
						that.$refs.selectTable.clearSelection();
					})
					//迭代已选择的内容，如果有就勾选
					if (!_.isEmpty(this.selectedListData)) {
						this.selectedListData.forEach(row => {
							that.selectListData.forEach(row1 => {
								if (row.id == row1.id) {
									that.$nextTick(function() {
										that.$refs.selectTable.toggleRowSelection(row1, true);
									})

								}
							});
						});
					}
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
			},
			//关闭弹框
			close() {
				this.selectedListData = [];
				this.clearSearchVal();
				this.$refs.popup.close();
			},
			//打开弹框
			open(parameter, title, size) {
				this.parameter = parameter;
				this.$refs.popup.open(title, size);
				this.$nextTick(() => {
					this.init();
				})
			},
			init() {
				this.popupLoading = true;
				//清空选择
				this.selectedListData = this.parameter;
				this.clearSearchVal();
				this.list();
				this.popupLoading = false;
			},
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
