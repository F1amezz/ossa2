<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="5">
						<el-form-item label="班级">
							<el-select size="mini" v-model="curclz" placeholder="请选择">
								<el-option v-for="(item,idx) in clzList" :key="item.id" :label="item.name"
									:value="idx">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="5">
						<el-form-item label="选择月份">
							<el-select  size="mini" v-model="mth" placeholder="请选择">
								<el-option v-for="(item,idx) in mthList" :key="item" :label="item"
									:value="idx">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="2">
							<el-button v-show="sid>0" type="primary" size="mini"  @click="naviStu(-1)">《《</el-button> 
					</el-col>
					<el-col :span="4">
						<el-form-item >
							 
							<el-select  size="mini" v-model="sid" placeholder="请选择学生">
								<el-option v-for="(item,idx) in stuList" :key="item.id" :label="item.name"
									:value="idx">
								</el-option>
							</el-select>
							 
						</el-form-item>
					</el-col>
					<el-col :span="2">
							<el-button type="primary"   v-if="sid<naviStu.length" size="mini"   @click="naviStu(1)">》》</el-button>
					</el-col>
					<div class="search-btn">
						<el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
						<el-button @click="clearSearchVal">清空</el-button>
					</div>
				</el-row>
			</el-form>
		</div>
		<div class="operator">
			<!-- <el-button type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button> -->
			<el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
			<el-button type="primary" icon="el-icon-document-add" @click="toImport()">导入考勤表</el-button>
		</div>

		<!-- 考勤数据查询 -->
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<el-table-column type="selection" width="55" fixed="left"></el-table-column>
				<!-- <el-table-column prop="id" label="学生id"></el-table-column> -->
				<el-table-column prop="stuname" label="名字"></el-table-column>
				<el-table-column prop="clzname" label="班级"></el-table-column>
				<el-table-column prop="kqdate" label="日期" width="180"></el-table-column>
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
				<el-table-column prop="state" label="异常原因">

				</el-table-column>
				<el-table-column prop="remarks" label="异常反馈"></el-table-column>
				<el-table-column prop="cfmdate" label="确认时间"></el-table-column>
				<el-table-column label="操作" fixed="right" width="180px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<!-- <el-button size="mini" icon="el-icon-search" @click="toDetail(scope.$index, scope.row)">详情
							</el-button> -->
							<el-button size="mini" icon="el-icon-edit" v-if="gettime3(scope.row.chkin,scope.row.chkout)"
								@click="toUpdate(scope.$index, scope.row)">编辑
							</el-button>
							<!-- <el-button size="mini" icon="el-icon-delete" type="danger"
								@click="remove(scope.$index, scope.row)">删除</el-button> -->
						</div>
					</template>
				</el-table-column>
			</el-table>
		 
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

		<!-- 导入考勤回显数据查看，数据正确，确认导入 -->
		<el-dialog :title="title" :visible.sync="dialogUpVisible" width="90%" class="abow_dialog">
			<el-table :data="qlistdata0" class="el-dialog_body" height="300">
				<el-table-column prop="stuname" label="名字"></el-table-column>
				<el-table-column prop="clzname" label="班级"></el-table-column>
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

			title: "",
			curclz: 0,  //当前班级
			clzList: [{ "id": "1ec8da868a8365bd5aaafa2ee3453ed4", "name": "q220627" },
			          { "id": "1ec8da868a8365bd5aaafa2ee3453ed3", "name": "q220813" }], //班级列表
			sid: 0,//当前学生
			stuList: [{ "id": "0938f9e55e70f8303678c49b3f529a03", "name": "辜宝捷" },
			{ "id": "0938f9e55e70f8303678c49b3f529a02", "name": "秦泽威" }], //学生列表
			mth: 2,
			mthList: ["2022-07", "2022-08", "2022-09", "2022-10"], //月份列表
		};
	},

	methods: {
		naviStu(val){
            //学生导航
			this.sid += val
			this.list()
		},
		// 判断上班时间
		gettime(ress) {
			if (ress) {
				let i = ress;
				let ii = i.replace(":", "");
				let iii = ii.replace(":", "");
				return iii < '093000' ? true : false;
			}
			// return false;
		},
		// 判断下班时间
		gettime2(ress) {
			if (ress) {
				let i = ress;
				let ii = i.replace(":", "");
				let iii = ii.replace(":", "");
				return iii > '173000' ? true : false;
			}
		},

		gettime3(chkin, chkout) {
			console.log(chkin, chkout);
			if (chkin == undefined) {
				chkin = false;
			}
			if (chkout == undefined) {
				chkout = false;
			}
			console.log(chkin, chkout);
			console.log(!(this.gettime(chkin) && this.gettime2(chkout)));
			return !(this.gettime(chkin) && this.gettime2(chkout))

		},

		//获取数据
		list() {
			this.listLoading = true;

			this.searchParams = {mth:this.mthList[this.mth] ,sid:this.stuList[this.sid].id  }

			api.list(this.searchParams , res => {
				console.log(res);

				this.listData = res.data.data;
				this.listLoading = false;
			})
		},
		//确认导入
		confirm() {
			let params = this.qlistdata0;
			api.toComfirm2(params, response => {
				if (response.code === 1000) {
					this.qlistdata = this.qlistdata0
					this.$utils.msg.success(response.msg, 3000);
					this.list();
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
			//成功上传后的回调
			if (res && res.length > 32) {

				let fname = res.substr(res.lastIndexOf("/") + 1)
				this.dialogVisible = false;

				console.log(fname)
				//通过文件名字调用后端启动解析文件，返回数据
				api.toParse({ fname: fname }, res2 => {
					let arr = res2.data;
					console.log(arr)

					if (arr && arr.length > 0) {
						console.log(arr)
						this.title = "本次解析成功 " + arr.length + " 条数据，确认导入?"
						this.qlistdata0 = [];
						this.qlistdata0 = arr;
						this.dialogUpVisible = true;
						this.$utils.msg.success("上传成功", 2000);
					} else {
						this.$utils.msg.warning("导入文件为空", 2000);
					}

				}, err => {
					console.log(err)
				})

			} else {
				this.$utils.msg.warning("导入失败，请联系管理员");
			}

		},
		close() {
			this.dialogUpVisible = false;
		},
		//去添加
		// toSave() {
		// 	this.$refs.edit.open({}, '添加');
		// },
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
		// batchRemove() {
		// 	if (!this.selected || this.selected.length == 0) {
		// 		this.$utils.msg.info('请选择至少一行');
		// 		return;
		// 	}
		// 	let parameter = {
		// 		ids: this.selected
		// 	};
		// 	this.$utils.confirm.warning('提示', '确定删除吗？', () => {
		// 		api.remove({
		// 			ids: this.selected
		// 		},
		// 			response => {
		// 				this.$utils.msg.success(response.msg);
		// 				this.list();
		// 			},
		// 			response => {
		// 				this.$utils.msg.warning(response.msg);
		// 			}
		// 		);
		// 	});
		// },

		//搜索
		search() {
			this.list();
		},
		//清空搜索框
		clearSearchVal() {
			this.searchParams = {};
			this.mth = 2;
			this.curclz = 0;
			//this.search();
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

