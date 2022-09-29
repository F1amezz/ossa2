<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="题目">
							<el-input v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="试题类型">
							<select-option-dictionary v-model="searchParams.type" @change="search()" dkey="exam-types">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
					<el-col :span="6">
						<el-form-item label="试题来源">
							<select-option-dictionary v-model="searchParams.reference" @change="search()"
								dkey="exam-reference">
							</select-option-dictionary>
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
			<el-button v-has-permission-code-and="'001600000005'" type="primary" icon="el-icon-plus" @click="toSave()">
				添加</el-button>
			<el-button v-has-permission-code-and="'001600000005'" type="primary" icon="el-icon-document-add"
				@click="toImport()">导入试题</el-button>
			<el-button v-has-permission-code-and="'001600000005'" type="primary" icon="el-icon-document"
				@click="toExport()">下载模板</el-button>
			<el-button v-has-permission-code-and="'001600000015'" type="danger" icon="el-icon-delete"
				@click="batchRemove()">批量删除</el-button>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<el-table-column type="selection" width="55" fixed="left"></el-table-column>
				<!-- <el-table-column prop="zpid" label="知识点id（章节）"></el-table-column> -->
				<el-table-column prop="name" label="试题名称" show-overflow-tooltip>
				</el-table-column>
				<el-table-column prop="sbname" width="220px" label="科目"></el-table-column>
				<el-table-column prop="type" width="110px" label="试题类型">
					<template slot-scope="scope">
						<!-- 试题 1-单选题、2多选题、3-判断题、4-填空题、5-、简答题、6-论述题、7-分析题 -->
						<p v-if="scope.row.type == 1">单选题</p>
						<p v-if="scope.row.type == 2">多选题</p>
						<p v-if="scope.row.type == 3">判断题</p>
						<p v-if="scope.row.type == 4">填空题</p>
						<p v-if="scope.row.type == 5">简答题</p>
						<p v-if="scope.row.type == 6">论述题</p>
						<p v-if="scope.row.type == 7">分析题</p>
					</template>
				</el-table-column>
				<el-table-column prop="exposeTimes" width="110px" label="出题次数">
					<template slot-scope="scope">
						<p>{{ scope.row.rightTimes }} / {{ scope.row.exposeTimes }} </p>
					</template>
				</el-table-column>
				<el-table-column prop="reference" width="110px" label="来源"></el-table-column>
				<el-table-column label="操作" fixed="right" width="250px">
					<template slot-scope="scope">
						<div class="dataTable-operator">

							<el-button size="mini" v-has-permission-code-and="'001600000020'" icon="el-icon-search"
								@click="toDetail(scope.$index, scope.row)">详情
							</el-button>
							<el-button size="mini" v-has-permission-code-and="'001600000010'" icon="el-icon-edit"
								@click="toUpdate(scope.$index, scope.row)">编辑
							</el-button>
							<el-button size="mini" v-has-permission-code-and="'001600000015'" icon="el-icon-delete"
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

		<el-dialog title="导入试题" :visible.sync="dialogVisible" width="30%">
			<excel-upload drag model="fileSystem" uri="/statics/exam" :size="upsize" v-on:change="uploadSuccess">
			</excel-upload>

			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
			</span>
		</el-dialog>
		<el-dialog title="导入试题" :visible.sync="dialogUpVisible" width="90%" class="abow_dialog">
			<el-table :data="qlistdata" class="el-dialog_body">
				<el-table-column prop="name" label="试题名称"></el-table-column>
				<el-table-column prop="content" label="选项内容"></el-table-column>
				<el-table-column prop="sbid" label="科目"></el-table-column>
				<el-table-column prop="type" label="试题类型">
					<template slot-scope="scope">
						<!-- 试题 1-单选题、2多选题、3-判断题、4-填空题、5-、简答题、6-论述题、7-分析题 -->
						<p v-if="scope.row.type == 1">单选题</p>
						<p v-if="scope.row.type == 2">多选题</p>
						<p v-if="scope.row.type == 3">判断题</p>
						<p v-if="scope.row.type == 4">填空题</p>
						<p v-if="scope.row.type == 5">简答题</p>
						<p v-if="scope.row.type == 6">论述题</p>
						<p v-if="scope.row.type == 7">分析题</p>
					</template>

				</el-table-column>
				<el-table-column prop="reference" label="试题来源"></el-table-column>

			</el-table>

			<div slot="footer">
				<el-button v-if="!disabled" type="primary" @click="confirm()">确 定</el-button>
				<el-button @click="close">取 消</el-button>
			</div>
		</el-dialog>


		<edit ref="edit"></edit>
	</div>
</template>
<script>

import edit from './edit.vue';
import api from '@/api/exams/question/question.js';
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
import excelUpload from '@/components/biz/fileUpload/excelUpload.vue';

export default {
	name: 'question',
	components: {
		edit,

		selectOptionDictionary,
		excelUpload,

	},
	data() {
		return {
			name: '',
			type: '',
			reference: '',
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
			dialogVisible: false,
			dialogUpVisible: false,
			qlistdata: [],
			upsize: '1024',
			fname: '',
			disabled: false,
			records: []

		};
	},
	watch: {
		"searchParams.name": function () {
			this.search();
		}


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
		ceshi() {
			this.$refs.show.open({}, '确定导入试题');
		},
		toImport() {
			this.dialogVisible = true;
		},
		handleClose() {

		},
		uploadSuccess(res) {
			if (res && res.length > 32) {
				this.$utils.msg.success("上传成功");
				let fname = res.substr(res.lastIndexOf("/") + 1)
				this.dialogVisible = false;
				//通过文件名字获取上传的文件
				api.toComfirm({ fname: fname }, res2 => {
					let arr = res2.data;
					if (arr.length) {
						this.qlistdata = arr;
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
		confirm() {
			let arr = this.qlistdata;
			for (let i = 0; i < arr.length; i++) {
				let parameter = {};
				let option = [];
				if (arr[i].content != undefined) {
					let lxx = arr[i].content.split(';');

					for (let index = 0; index < lxx.length; index++) {
						lxx[index] = lxx[index].trim();

						option.push({ o: lxx[index].substr(0, 1), n: lxx[index].substr(2) });
					}
					console.log(lxx);
					parameter.content = JSON.stringify({ "title": arr[i].name, "choiceList": option });
					let str = '';
					let danlist = arr[i].answer.split('');
					for (let index = 0; index < danlist.length; index++) {
						if (index != danlist.length - 1) {
							str = str + danlist[index] + ',';
						} else {
							str = str + danlist[index]
						}
					}
					parameter.answer = str;
				} else {
					parameter.content = JSON.stringify({ "title": arr[i].name, "choiceList": {} });
					parameter.answer = arr[i].answer;
				}
				parameter.name = arr[i].name;
				parameter.type = arr[i].type;
				parameter.sbid = arr[i].sbid;
				parameter.reference = arr[i].reference;
				parameter.analysis = arr[i].analysis;
				this.records.push(parameter);

			}
			api.finishImport(this.records,

				response => {
					this.$utils.msg.success(response.msg);
					this.list();
					this.close();

				},
				response => {
					this.$utils.msg.warning(response.msg);

				});


		},
		toExport() {
			var a = document.createElement("a"); //创建一个<a></a>标签
			a.href = "/static/QuestionTemplate.xlsx"; // 给a标签的href属性值加上地址
			a.download = "QuestionTemplate.xlsx"; //设置下载文件文件名，这里加上.xlsx指定文件类型
			a.style.display = "none"; // 障眼法藏起来a标签
			document.body.appendChild(a); // 将a标签追加到文档对象中
			a.click(); // 模拟点击了a标签，会触发a标签的href的读取
			a.remove(); // 一次性的，用完就删除a标签
		},
		//去添加
		toSave() {
			this.$refs.edit.open({}, '添加', false);
		},
		//去编辑
		toUpdate(index, row) {
			let editParameter = {
				id: row.id,
				sbname: row.sbname
			};
			this.$refs.edit.open(editParameter, '编辑', false);

		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				id: row.id,
				sbname: row.sbname
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
			console.log(this.searchParams);
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

<style lang="scss">
// 文字提示宽度设置
.el-tooltip__popper {
	max-width: 40% !important;
	/*背景色  !important优先级*/
	background: rgb(255, 255, 255) !important;
	/*背景色透明度*/
	// opacity: 0.5 !important;
	/*字体颜色*/
	color: #000000 !important;
	border: 1px solid #000000;
}
</style>

