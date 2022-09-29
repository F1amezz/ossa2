<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="创建人">
							<el-input v-model="searchParams.crerName"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="试卷名称">
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
					</div>
				</el-row>
			</el-form>
		</div>
		<div class="operator">
			<el-button type="primary" v-has-permission-code-and="'001600005005'" icon="el-icon-plus" @click="toSave()">
				添加</el-button>
			<el-button type="danger" v-has-permission-code-and="'001600005015'" icon="el-icon-delete"
				@click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<el-table-column type="selection" width="50" fixed="left"></el-table-column>
				<el-table-column prop="mdtm" width="160" label="修改时间"></el-table-column>
				<el-table-column prop="crtm" width="160" label="创建时间"></el-table-column>
				<el-table-column prop="crerName" label="创建人"></el-table-column>
				<el-table-column prop="name" label="试卷名称"></el-table-column>
				<el-table-column prop="duration" width="150" label="考试时间（分钟）"></el-table-column>
				<el-table-column prop="score" width="60" label="总分"></el-table-column>
				<el-table-column prop="passScore" width="80" label="及格分数"></el-table-column>
				<el-table-column prop="type" width="60" label="类型">
					<template slot-scope="scope">
						<p v-if="scope.row.type == 1">小测</p>
						<p v-if="scope.row.type == 2">周测</p>
						<p v-if="scope.row.type == 3">阶段测</p>
					</template>
				</el-table-column>
				<el-table-column prop="status" width="60" label="状态">
					<template slot-scope="scope">
						<p v-if="scope.row.status == 1">启用</p>
						<p v-if="scope.row.status == 2">不启用</p>
					</template>
				</el-table-column>
				<!-- <el-table-column prop="mark" label="备注"></el-table-column> -->
				<el-table-column label="操作" fixed="right" width="260px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button v-has-permission-code-and="'001600005020'" size="mini" icon="el-icon-search"
								@click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<el-button v-has-permission-code-and="'001600005010'" size="mini" icon="el-icon-edit"
								@click="toUpdate(scope.$index, scope.row)">编辑
							</el-button>
							<el-button v-has-permission-code-and="'001600005015'" size="mini" icon="el-icon-delete"
								type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
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
		<edtails ref="edtails"></edtails>

	</div>
</template>
<script>
import edit from './edit.vue';
import api from '@/api/exams/exampaper/exampaper.js';
import edtails from './edtails.vue';
export default {
	name: 'exampaper',
	components: {
		edit, edtails
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
			this.$refs.edit.open({}, '添加', false, '80%');
		},
		//去编辑
		toUpdate(index, row) {
			let editParameter = {
				id: row.id
			};
			this.$refs.edtails.open(editParameter, '编辑', false, '80%');
		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				id: row.id
			};
			this.$refs.edtails.open(detailParameter, '详情', true, '80%');
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
			this.list();
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

