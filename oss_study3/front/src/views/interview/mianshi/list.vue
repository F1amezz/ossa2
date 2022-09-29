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
        <div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange" v-loading="listLoading">
                        
						<el-table-column prop="mstm" label="面试时间"></el-table-column>
		                <el-table-column prop="dbColumn_stuid" label="学生"></el-table-column>
						<el-table-column prop="cmpname" label="企业"></el-table-column>
						<el-table-column label="岗位"></el-table-column>			
						<el-table-column prop="mszbqk" label="准备情况">
                             <template slot-scope="scope">
                                <el-rate  :disabled="isReadonly"  v-model="scope.row.mszbqk" :colors="colors">
                                </el-rate>
                             </template>
						</el-table-column>
						<el-table-column prop="msscore" label="过程打分">
							  <template slot-scope="scope">
                                <el-rate  :disabled="isReadonly"  v-model="scope.row.msscore" :colors="colors">
                                </el-rate>
                             </template>
						</el-table-column>
						<el-table-column prop="msresult" label="面试结果"></el-table-column>
						<el-table-column prop="mslast" label="面试时长(分钟)"></el-table-column>

				<el-table-column label="操作" fixed="right" width="200px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button    size="mini"  @click="toShow( scope.row)">详情</el-button>

						    <el-button v-if="!scope.row.msscore"     size="mini" @click="toUpdate( scope.row )">总结</el-button>
                            <el-button v-if="!scope.row.msdp && scope.row.msscore"     size="mini" @click="toDianpin(scope.row)">点评</el-button>
                            
							<el-button  v-if="!scope.row.msscore"  size="mini"   type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
                        </div>
					</template>
				</el-table-column>
			</el-table>
			<div class="pageBar">
				<el-pagination 
				@size-change="sizeChange" 
				@current-change="currentChange" 
				:total="listData.total" 
				:page-size="page.size"
				:current-page="page.current" 
				:layout="this.$constant.page.layout" 
				:page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<zhj ref="zhj"></zhj>
	    <dianp ref="dianp"></dianp>
	</div>
</template>
<script>

import zhj from './zhj.vue';
import dianp from './dianp.vue';

import api from '@/api/interview/mianshi/mianshi.js';
export default {
	name: 'mianshi',
	components: {
		dianp,zhj
	},
	data() {
		return {
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
			colors: { 3: '#CC3366',  4: '#00CC33',5: '#00CC33' },
			isReadonly:true
		};
	},
	methods: {
		//获取数据
		list() {
			this.listLoading = true;
			let params = this.$utils.merger(this.searchParams, this.page);
			api.list(params, response => {
				this.listData = response.data;
				console.log(this.listData)
				this.listLoading = false;
			})
		},
		//去添加
		toSave() {
			this.$refs.edit.open({}, '添加');
		},
		//去总结
		toUpdate( row) {
			this.$refs.zhj.open(row, '面试总结');
		},toDianpin( row) {
			this.$refs.dianp.open(row, '面试点评');
		},toShow( row) {
			this.$refs.dianp.open(row, '面试详情', true);
		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				id: row.id
			};
			this.$refs.show.open(detailParameter, '面试详情', true);
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

