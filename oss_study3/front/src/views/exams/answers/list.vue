<template>
	<popup ref="popup" :loading="popupLoading">
<div slot="body">
			<div class="search">
				<el-form ref="form" :model="searchParams" label-width="80px" size="mini">
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
							<el-button type="primary" icon="el-icon-search" @click="search" size="mini">搜索</el-button>
							<el-button @click="clearSearchVal" size="mini">清空</el-button>
							<el-button type="text" v-if="!isShowMoreSearch"
								@click="isShowMoreSearch = !isShowMoreSearch" size="mini">
								更多
								<i class="el-icon-arrow-down"></i>
							</el-button>
							<el-button type="text" v-else @click="isShowMoreSearch = !isShowMoreSearch" size="mini">
								收起
								<i class="el-icon-arrow-up"></i>
							</el-button>
						</div>
					</el-row>
				</el-form>
			</div>
			<div class="operator">
				<el-button type="primary" icon="el-icon-plus" size="mini" @click="toSave()">添加</el-button>
				<el-button type="danger" icon="el-icon-delete" size="mini" @click="batchRemove()">批量删除</el-button>
			</div>
			<div class="dataTable autoFlex" v-if="listData">
				<el-table class="table" :data="listData.records" stripe border height="auto"
					@selection-change="selectionChange" v-loading="listLoading">
					<el-table-column type="selection" width="55" fixed="left"></el-table-column>
					<el-table-column prop="dbColumn_sid" label="学生名字"></el-table-column>
					<el-table-column prop="dbColumn_cid" label="班级"></el-table-column>
					<el-table-column prop="score" label="得分"></el-table-column>
					<el-table-column prop="isjudged" label="答卷是否已批">

							<template slot-scope="scope">
							{{scope.row.isjudged==1?"已批改":"未批改"}}
							<!--  -->
						</template>
					</el-table-column>
					<el-table-column prop="mdtm" label="修改时间"></el-table-column>
					<el-table-column prop="mder" label="修改人"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button
							size="mini"
							@click="toUpdate(scope.$index, scope.row)">批改</el-button>
							<el-button
							size="mini"
							type="danger"
							@click="remove(scope.$index, scope.row)">删除</el-button>
						</template>
						</el-table-column>
				</el-table>
				<div class="pageBar">
					<el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
						:page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
						:page-sizes="this.$constant.page.pageSizes"></el-pagination>
				</div>
			</div>

			<edit ref="edit"  @flush="list()"></edit>
			
		</div>
		<!-- <div slot="footer">
            <el-button v-if="!disabled" type="primary" :loading="confirmLoading" size="mini" @click="confirm()">确 定
            </el-button>
            <el-button size="mini" @click="close">取 消</el-button>
        </div> -->

		
		
		
		
		
	</popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import edit from './edit.vue';
import api from "@/api/exams/answers/answers.js";
import { log } from 'console';
export default {
	name: "lookTestPaper",
	components: {
		popup,edit
	},
	data() {
		return {
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
		};
	},
	methods: {
		//打开弹框
		open(parameter, title, disabled) {
			console.log(parameter,"------------------");
			this.row = parameter.id;
			this.disabled = disabled;
			this.$refs.popup.open(title, "90%");
			this.$nextTick(() => {
				this.list();
			});
		},
		//获取数据
		list() {
			this.listLoading = true;
			let params = this.$utils.merger(this.searchParams, this.page,);
			let param={current:params.current,size:params.size,kid:this.row};

			console.log(param,"params");
			api.listById(param, response => {
				this.listData = response.data;
				console.log(this.listData,"this.listData");
				this.listLoading = false;

			})
		},
		//去添加
		toSave() {
			this.$refs.edit.open({}, '添加');
		},
		//去编辑
		toUpdate(index, row) {
			// console.log(this.listData.records,"this.listData.records");
		//	 console.log(row,"index");
			let editParameter = {
				row: row,
			};
			this.$refs.edit.open(editParameter, '改卷');
		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				// id: row.id
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
				ids: this.selected,
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
	}
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
