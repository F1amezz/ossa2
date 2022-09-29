<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="任务名称">
							<el-input v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="bean">
							<el-input v-model="searchParams.beanname"></el-input>
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
			<el-button v-has-permission-code-and="'002500000000'" type="primary" icon="el-icon-plus" @click="toSave()">
				添加</el-button>
			<el-button v-has-permission-code-and="'002500000010'" type="danger" icon="el-icon-delete"
				@click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<qlh-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<qlh-table-column type="selection" width="55" fixed="left" ignored :editable="false"></qlh-table-column>
				<qlh-table-column prop="name" label="任务名称"></qlh-table-column>
				<qlh-table-column prop="beanname" label="bean"></qlh-table-column>
				<qlh-table-column prop="method" label="方法"></qlh-table-column>
				<qlh-table-column prop="params" label="参数"></qlh-table-column>
				<qlh-table-column prop="state" label="状态">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.state==1" type="success">正常</el-tag>
						<el-tag v-if="scope.row.state==2" type="danger">暂停</el-tag>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="islog" label="保存日志">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.islog==1" type="success">保存</el-tag>
						<el-tag v-if="scope.row.islog==2" type="danger">不保存</el-tag>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="cron" label="cron"></qlh-table-column>
				<qlh-table-column label="操作" fixed="right" width="350px" ignored>
					<template slot-scope="scope">
						<div class="dataTable-operator">

							<el-button size="mini" v-has-permission-code-and="'002500000020'" v-if="scope.row.state==2"
								icon="el-icon-caret-right" @click="run(scope.$index, scope.row)">运行</el-button>
							<el-button size="mini" v-has-permission-code-and="'002500000025'" v-if="scope.row.state==2"
								icon="el-icon-refresh-right" @click="resume(scope.$index, scope.row)">恢复</el-button>
							<el-button size="mini" v-has-permission-code-and="'002500000025'" v-if="scope.row.state==1"
								icon="el-icon-switch-button" @click="pause(scope.$index, scope.row)">暂停</el-button>
							<el-button v-has-permission-code-and="'002500000005'" size="mini" icon="el-icon-search"
								@click="toDetail(scope.$index, scope.row)">详情</el-button>
				<!-- 			<el-button v-has-permission-code-and="'002500000005'" size="mini" icon="el-icon-edit"
								@click="toUpdate(scope.$index, scope.row)">编辑</el-button>
							<el-button v-has-permission-code-and="'002500000010'" size="mini" icon="el-icon-delete"
								type="danger" @click="remove(scope.$index, scope.row)">删除</el-button> -->
							<el-dropdown v-has-permission-code-or="'002500000020','002500000025','002500000030'"
								size="small">
								<el-button icon="el-icon-more" size="mini">更多</el-button>
								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item v-has-permission-code-and="'002500000005'" icon="el-icon-edit"
										@click.native="toUpdate(scope.$index, scope.row)">编辑</el-dropdown-item>
									<el-dropdown-item v-has-permission-code-and="'002500000025'" icon="el-icon-delete"
										  @click.native="remove(scope.$index, scope.row)">删除
									</el-dropdown-item>

								</el-dropdown-menu>
							</el-dropdown>
						</div>
					</template>
				</qlh-table-column>
			</qlh-table>
			<div class="pageBar">
				<el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
					:page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
					:page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<edit ref="edit"></edit>
	</div>
</template>
<script>
	import edit from './edit.vue';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	import api from '@/api/schedule/schedulejob/schedulejob.js';
	export default {
		name: 'schedulejob',
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
			//恢复
			resume(index, row) {
				let parameter = {
					id: row.id
				}
				this.$utils.confirm.warning('提示', '确定恢复吗？', () => {
					api.resume(
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
			//暂停
			pause(index, row) {
				let parameter = {
					id: row.id
				}
				this.$utils.confirm.warning('提示', '确定暂停吗？', () => {
					api.pause(
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
			//运行
			run(index, row) {
				let parameter = {
					id: row.id
				}
				this.$utils.confirm.warning('提示', '确定运行吗？', () => {
					api.run(
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
