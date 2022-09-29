<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="用户名">
							<el-input v-model="searchParams.uname"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="模块">
							<el-input v-model="searchParams.module"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="操作">
							<el-input v-model="searchParams.operation"></el-input>
						</el-form-item>
					</el-col>
					<el-collapse-transition>
						<div v-if="isShowMoreSearch">
							<!--隐藏区域-->
							<el-col :span="6">
								<el-form-item label="时间范围">
									<el-col :span="11">
										<el-date-picker type="date" placeholder="请选择" v-model="searchParams.beginTime">
										</el-date-picker>
									</el-col>
									<el-col :span="11">
										<el-date-picker type="date" placeholder="请选择" v-model="searchParams.endTime">
										</el-date-picker>
									</el-col>
								</el-form-item>
							</el-col>
							<el-col :span="6">
								<el-form-item label="日志类型">
									<el-select v-model="searchParams.type" placeholder="请选择">
										<el-option label="系统日志" value="1"></el-option>
										<el-option label="错误日志" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
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
			<el-button v-has-permission-code-and="'000000400005'" type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<qlh-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
			 v-loading="listLoading" :excelProp="excelProp">
				<qlh-table-column type="selection" width="55" fixed="left" ignored :editable="false"></qlh-table-column>
				<qlh-table-column prop="uname" label="用户" sortable :editable="false"></qlh-table-column>
				<qlh-table-column prop="ip" label="ip"></qlh-table-column>
				<qlh-table-column prop="useragent" show-overflow-tooltip label="客户端"></qlh-table-column>
				<qlh-table-column prop="module" label="模块" :editable="false"></qlh-table-column>
				<qlh-table-column prop="operation" label="操作" :editable="false"></qlh-table-column>
				<qlh-table-column prop="optime" label="操作时间" sortable :editable="false"></qlh-table-column>
				<qlh-table-column prop="type" label="日志类型" sortable :formatter="(row, column) => {
							return row.type == 1 ? '系统日志' : '错误日志';
						}"></qlh-table-column>
				<qlh-table-column prop="uri" label="操作接口"></qlh-table-column>
				<qlh-table-column prop="content" show-overflow-tooltip label="内容"></qlh-table-column>
				<qlh-table-column label="操作" fixed="right" width="200px" ignored :editable="false">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button v-has-permission-code-and="'000000400000'" size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情</el-button>
							<el-button v-has-permission-code-and="'000000400005'" size="mini" icon="el-icon-delete" type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
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
	import edit from './detail.vue';
	import api from '@/api/system/loginfo/loginfo.js';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	export default {
		name: 'loginfo',
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
					excelName: '操作日志信息',
					sheetname: '操作日志信息'
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
			//去编辑
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
