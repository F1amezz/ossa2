<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="姓名">
							<el-input v-model="searchParams.studname"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="选择月份">
							<el-date-picker v-model="searchParams.value2" :disabled="searchParams.value1 ==  undefined ?false:true" type="month" @change="search" value-format="yyyy-MM" placeholder="选择月">
							</el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="选择日期">
							<el-date-picker v-model="searchParams.value1" :disabled="searchParams.value2 ==  undefined ?false:true" type="date" @change="search" value-format="yyyy-MM-dd" placeholder="选择日期">
							</el-date-picker>
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
			<el-button type="primary" icon="el-icon-document-add" @click="toImport()">导入试题</el-button>
		</div>
		<!-- 考勤导入按钮 -->
		<el-dialog title="导入考勤表" :visible.sync="dialogVisible" width="30%">
			<excel-upload drag model="fileSystem" uri="/statics/attendance" :size="upsize" v-on:change="uploadSuccess">
			</excel-upload>

			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
			</span>
		</el-dialog>
		<!-- 考勤数据查询 -->
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<el-table-column type="selection" width="55" fixed="left"></el-table-column>
				<!-- <el-table-column prop="id" label="学生id"></el-table-column> -->
				<el-table-column prop="studname" label="名字"></el-table-column>
				<el-table-column prop="sid" label="班级"></el-table-column>
				<el-table-column prop="kqday" label="日期" width="180"></el-table-column>
				<el-table-column prop="chkin" label="上班考勤时间">
					<template slot-scope="scope">
						<el-tag type="success" v-if="gettime(scope.row.chkin)">{{scope.row.chkin}}</el-tag>
						<el-tag type="danger" v-if="!gettime(scope.row.chkin)">{{scope.row.chkin == undefined ?
						'未打卡':scope.row.chkin}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="chkout" label="下班考勤时间">
					<template slot-scope="scope">
						<el-tag type="success" v-if="gettime2(scope.row.chkout)">{{scope.row.chkout}}</el-tag>
						<el-tag type="danger" v-if="!gettime2(scope.row.chkout)">{{scope.row.chkout == undefined ?
						'未打卡':scope.row.chkout}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="state" label="状态"></el-table-column>
				<el-table-column prop="cfmdate" label="确认时间"></el-table-column>
				<el-table-column label="操作" fixed="right" width="280px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<el-button size="mini" icon="el-icon-edit" @click="toUpdate(scope.$index, scope.row)">编辑
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
		<!-- 导入考勤回显数据查看，数据正确，确认导入 -->
		<el-dialog title="导入试题" :visible.sync="dialogUpVisible" width="90%" class="abow_dialog">
			<el-table :data="qlistdata0" class="el-dialog_body" height="300">
				<el-table-column prop="studname" label="名字"></el-table-column>
				<el-table-column prop="sid" label="班级"></el-table-column>
				<el-table-column prop="kqday" label="考勤日期"></el-table-column>
				<el-table-column prop="chkin" label="早上打卡时间"></el-table-column>
				<el-table-column prop="chkout" label="下午打卡时间"></el-table-column>
			</el-table>
			<div slot="footer">
				<el-button type="primary" @click="confirm()">确 定</el-button>
				<el-button @click="close">取 消</el-button>
			</div>
		</el-dialog>
		<edit ref="edit"></edit>
	</div>
</template>
<script>
import edit from './edit.vue';
import api from '@/api/study/attendance/attendance.js';
import excelUpload from '@/components/biz/fileUpload/excelUpload.vue';
import { log } from 'console';
export default {
	name: 'attendance',
	components: {
		edit,
		excelUpload
	},
	data() {
		return {
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
			dialogVisible: false,
			upsize: '1024',
			qlistdata0: [],//弹窗内的数组
			qlistdata: [], //确认后传入
			dialogUpVisible: false,
			disabled: false,

		};
	},
	updated() {
		console.log(this.qlistdata)
	},
	methods: {
		gettime(ress) {
			if (ress) {
				let i = ress;
				let ii = i.replace(":", "");
				let iii = ii.replace(":", "");
				return iii < '093000' ? true : false;
			}
			return false;
		},
		gettime2(ress) {
			if (ress) {
				let i = ress;
				let ii = i.replace(":", "");
				let iii = ii.replace(":", "");
				return iii > '173000' ? true : false;
			} else {

			}
		},
		//获取数据
		list() {
			this.listLoading = true;
			let params = this.$utils.merger(this.searchParams, this.page);
			api.list(params, response => {
				console.log(response);
				this.listData = response.data;
				this.listLoading = false;
			})
		},
		//确认导入
		confirm() {
			let params = this.qlistdata0;
			api.toComfirm2(params, response => {
				if (response.code === 1000) {
					this.qlistdata = this.qlistdata0
					this.$utils.msg.success("导入成功");
				} else {
					this.$utils.msg.warning("导入失败");
				}
			})
			this.dialogUpVisible = false

		},
		toImport() {
			this.dialogVisible = true;
		},
		uploadSuccess(res) {
			if (res && res.length > 32) {
				this.$utils.msg.success("上传成功");
				let fname = res.substr(res.lastIndexOf("/") + 1)
				this.dialogVisible = false;
				//通过文件名字获取上传的文件
				api.toComfirm({ fname: fname }, res2 => {
					let arr = JSON.stringify(res2.data);
					if (arr.length) {
						this.qlistdata0 = [];
						this.qlistdata0 = res2.data;
						this.dialogUpVisible = true;
					} else {
						this.$utils.msg.warning("导入文件为空");
					}

				})

			} else {
				this.$utils.msg.warning("导入失败，请联系管理员");
			}

		},
		close() {
			this.dialogUpVisible = false;
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
			this.value1 = '';
			this.value2 = '';
			this.search();
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

