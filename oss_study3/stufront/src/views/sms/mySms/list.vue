<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="标题">
							<el-input v-model="searchParams.title"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="发送者">
							<select-user-table-dialog v-model="searchParams.sid"></select-user-table-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="阅读状态">
							<el-select v-model="searchParams.rstate" placeholder="请选择">
								<el-option label="未读" :value="1"></el-option>
								<el-option label="已读" :value="2"></el-option>
							</el-select>
						</el-form-item>
					</el-col>

					<el-col :span="6">
						<el-form-item label="内容">
							<el-input v-model="searchParams.content"></el-input>
						</el-form-item>
					</el-col>

					<el-collapse-transition>
						<div v-if="isShowMoreSearch">
							<!--隐藏区域-->
							<el-col :span="6">
								<el-form-item label="内容级别">
									<selectOptionDictionary dkey="sms-level" v-model="searchParams.level">
									</selectOptionDictionary>
								</el-form-item>
							</el-col>
							<el-col :span="6">
								<el-form-item label="内容类型">
									<selectOptionDictionary dkey="sms-type" v-model="searchParams.type">
									</selectOptionDictionary>
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
			<el-button type="primary" icon="el-icon-search" @click="readBatch()"
				v-has-permission-code-and="'002000050005'">批量阅读</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<qlh-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
				v-loading="listLoading" :showTableTool="false">
				<qlh-table-column :selectable="function(row, rowIndex){
					return row.rstate == 1;
				}" type="selection" width="55" fixed="left"></qlh-table-column>
				<qlh-table-column prop="title" label="标题"></qlh-table-column>
				<qlh-table-column prop="sname" label="发送者"></qlh-table-column>
				<qlh-table-column prop="stime" label="发送时间"></qlh-table-column>
				<qlh-table-column prop="dictionary_level" label="内容级别">
					<template slot-scope="scope">
						<el-tag type="success" v-if="scope.row.level==1">普通</el-tag>
						<el-tag type="primany" v-else-if="scope.row.level==2">一般</el-tag>
						<el-tag type="danger" v-else-if="scope.row.level==3">紧急</el-tag>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="dictionary_type" label="内容类型">
					<template slot-scope="scope">
						<el-tag effect="dark">{{scope.row.dictionary_type}}</el-tag>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="title" label="标题"></qlh-table-column>
				<qlh-table-column prop="content" label="内容"></qlh-table-column>
				<qlh-table-column prop="rstate" label="阅读状态">
					<template slot-scope="scope">
						<el-tag type="danger" v-if="scope.row.rstate==1">未读</el-tag>
						<el-tag type="success" v-if="scope.row.rstate==2">已读</el-tag>
					</template>
				</qlh-table-column>
				<qlh-table-column prop="rtime" label="阅读时间"></qlh-table-column>
				<qlh-table-column label="操作" fixed="right" width="120px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button v-has-permission-code-and="'002000050000'" size="mini" icon="el-icon-search"
								@click="toRead(scope.$index, scope.row)">查看</el-button>
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
		<detail ref="detail"></detail>
	</div>
</template>
<script>
	import api from '@/api/sms/mySms/mySms.js';
	import selectUserTableDialog from '@/components/biz/selectUserTableDialog/selectUserTableDialog.vue';
	import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
	import detail from './detail.vue';
	import qlhTable from '@/components/qlhTable/qlhTable.vue';
	import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
	export default {
		name: 'mySms',
		components: {
			detail,
			selectUserTableDialog,
			selectOptionDictionary,
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
			//改变选择项
			selectionChange(val) {
				//清空
				this.selected = [];
				for (let item of val) {
					this.selected.push(item.id);
				}
			},
			readBatch() {
				if (!this.selected || this.selected.length == 0) {
					this.$utils.msg.info('请选择至少一行');
					return;
				}
				let parameter = this.selected;
				this.$utils.confirm.warning('提示', '确定阅读吗？', () => {
					api.readBatch(parameter,
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
			//阅读
			toRead(index, row) {
				let detailParameter = {
					id: row.id
				};
				this.$refs.detail.open(detailParameter, '详情');
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
