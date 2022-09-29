<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="考试名称">
							<el-input v-model="searchParams.name"></el-input>
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
			<el-button type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
			<el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">

				<el-table-column type="selection" width="55" fixed="left"></el-table-column>
				<el-table-column prop="name" label="考试名称"></el-table-column>
				<el-table-column prop="cname" label="考试班级"></el-table-column>
				<el-table-column prop="exampName" label="试卷名称"></el-table-column>
				<el-table-column prop="userName" label="出题人"></el-table-column>
				<el-table-column prop="hadNumber" label="已做/全部人数">
				
							<template slot-scope="scope">
							{{scope.row.hadNumber}}/{{scope.row.allNumber}}
							<!--  -->
						</template>
			
				</el-table-column>
				<el-table-column prop="startTime" label="生效时间"></el-table-column>
				<el-table-column prop="endTime" label="截止时间"></el-table-column>

				<el-table-column label="操作" fixed="right" width="300px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<!-- <el-button size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑
							</el-button> -->
							<el-button size="mini" icon="el-icon-search" @click="toLookTP(scope.$index, scope.row)">改卷
							</el-button>
							<el-button size="mini" icon="el-icon-delete" type="danger"
								@click="remove(scope.$index, scope.row)">删除</el-button>
						</div>
					</template>
				</el-table-column>
			</el-table>
			<div class="pageBar">
				<el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
					:page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
					:page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<edit ref="edit"></edit>
		<anlist ref="anlist"></anlist>
	</div>
</template>
<script>
import edit from './edit.vue';
import anlist from '../answers/list.vue';
import api from '@/api/exams/exams/exams.js';
export default {
	name: 'exams',
	components: {
		edit, anlist
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
			console.log(params,"00");
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
		// toUpdate(index, row) {
		// 	let editParameter = {
		// 		id: row.id
		// 	};
		// 	this.$refs.edit.open(editParameter, '编辑');
		// },
		//去查卷
		toLookTP(index, row) {
			let parameter = {
				id: row.id
			};
			this.$refs.anlist.open(parameter, '试卷');
		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				id: row.id,
				expid: row.expid
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

