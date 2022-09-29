<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="姓名">
							<el-autocomplete v-model="searchParams.name" :fetch-suggestions="querySearchAsync"
								placeholder="请输入内容" @select="handleSelect"></el-autocomplete>
						</el-form-item>
					</el-col>

					<el-col :span="6">
						<el-form-item label="班级">
							<el-select v-model="searchParams.cname" placeholder="请选择班级">
								<el-option v-for="item in postsList" :key="item.id" :label="item.cname"
									:value="item.cname"></el-option>
							</el-select>
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
		<!-- <div id="main">
		</div> -->
		<div class="operator">
			<div>
				<el-row>
					<el-col :span="24">
						<el-button type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
						<el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
						<el-button type="text" @click="handleClick1 = true">点击打开数据图</el-button>
						<!-- :before-close="handleClose" -->
						<el-dialog title="提示" :visible.sync="handleClick1" width="30%" >
							<el-tabs v-model="activeName" type="card" @tab-click="handleClick">
								<el-tab-pane label="考试成绩分布图" name="first" lazy>
									<div id="user-manage"></div>
								</el-tab-pane>
								<el-tab-pane label="每日总结完成图" name="second" lazy>
									<div id="config-manage"></div>
								</el-tab-pane>
								<el-tab-pane label="每日点评趋势图" name="third" lazy>
									<div id="role-manage"></div>
								</el-tab-pane>
							</el-tabs>
						</el-dialog>

					</el-col>
				</el-row>
			</div>
		</div>
		<div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto"
				@selection-change="selectionChange" v-loading="listLoading">
				<el-table-column type="selection" width="55" fixed="left"></el-table-column>
				<qlh-table-column prop="head" label="头像" width="80" ignored>
					<template slot-scope="scope">
						<el-avatar size="large" :src="scope.row.head" v-if="scope.row.head"></el-avatar>
					</template>
				</qlh-table-column>
				<el-table-column prop="sname" label="学生名字"></el-table-column>
				<el-table-column prop="mobile" label="手机号码"></el-table-column>
				<!-- <el-table-column prop="tname" label="老师姓名"></el-table-column> -->
				<el-table-column prop="cname" label="班级名称"></el-table-column>
				<!-- <el-table-column prop="courname" label="所属课程名称+版本"></el-table-column> -->
				<el-table-column prop="tname" label="带班老师"></el-table-column>
				<el-table-column prop="monitor" label="该班班长"></el-table-column>
				<!-- <el-table-column prop="crtm" label="创建时间"></el-table-column>
						<el-table-column prop="mdtm" label="修改时间"></el-table-column>
						<el-table-column prop="crer" label="创建人"></el-table-column>
						<el-table-column prop="mder" label="修改人"></el-table-column> -->
				<el-table-column label="操作" fixed="right" width="200px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button size="mini" @click="toDetail(scope.$index, scope.row)">详情</el-button>
							<el-button size="mini" @click="toUpdate(scope.$index, scope.row)">编辑</el-button>
							<el-button size="mini" type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
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
	</div>
</template>
<script>
import edit from './edit.vue';
import apiClass from "@/api/edu/clazz/clazz.js";
import api from '@/api/study/myclass/myclass.js';
import qlhTable from '@/components/qlhTable/qlhTable.vue';
import qlhTableColumn from '@/components/qlhTable/qlhTableColumn.vue';
export default {
	name: 'myclass',
	components: {
		edit,
		qlhTable,
		qlhTableColumn
	},
	data() {
		return {
			activeName: "first",
			handleClick1: false,
			newVisible: false,
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
		};
	},
	watch: {
		"searchParams.sname": function (newVal, oldVal) {
			this.searchParams.sname = newVal;
			this.search();

		}



	},
	methods: {
		querySearchAsync(){},
		postsList(){},
		handleSelect(){},
		handleClose(){},
		change(){},
		// 图形化
		drawLine() {
			const lineChart = this.$echarts.init(
				document.getElementById("alarm-trend")
			);

			const option = {
				xAxis: {
					type: "category",
					data: ["60以下", "60-75", "76-85", "86-100"]
				},
				yAxis: {
					type: "value"
				},
				series: [
					{
						data: [3, 10, 4, 2],
						type: "line"
					}
				]
			};

			lineChart.setOption(option);
		},
		drawBar() {
			const barChart = this.$echarts.init(
				document.getElementById("user-manage")
			);

			const option = {
				xAxis: {
					type: "category",
					data: ["60以下", "60-75", "76-85", "86-100"]
				},
				yAxis: {
					type: "value"
				},
				series: [
					{
						data: [3, 10, 4, 2],
						type: "bar",
						showBackground: true,
						backgroundStyle: {
							color: "rgba(180, 180, 180, 0.2)"
						}
					}
				]
			};

			barChart.setOption(option);
		},
		drawPie() {
			const pieChart = this.$echarts.init(
				document.getElementById("config-manage")
			);

			const option = {
				title: {
					text: "学生总结完成图",
					left: "center"
				},
				tooltip: {
					trigger: "item"
				},
				legend: {
					orient: "vertical",
					left: "left"
				},
				series: [
					{
						name: "完成情况",
						type: "pie",
						radius: "50%",
						data: [
							{ value: 21, name: "正在进行" },
							{ value: 10, name: "已完成" },
							{ value: 30, name: "未完成" }
						],
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: "rgba(0, 0, 0, 0.5)"
							}
						}
					}
				]
			};

			pieChart.setOption(option);
		},
		drawGrandpa() {
			const grandpaChart = this.$echarts.init(
				document.getElementById("role-manage")
			);
			const option = {
				xAxis: {
					type: 'category',
					data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
				},
				yAxis: {
					type: 'value'
				},
				series: [
					{
						data: [20, 30, 40, 50, 60, 70, 80],
						type: 'line',
						smooth: true
					}
				]
			};


			grandpaChart.setOption(option);
		},
		handleClick(tab, event) {
			switch (this.activeName) {
				case "first":
					this.$nextTick(() => {
						this.drawBar();
					});
					break;
				case "second":
					this.$nextTick(() => {
						this.drawPie();
					});
					break;
				case "third":
					this.$nextTick(() => {
						this.drawGrandpa();
					});
					break;
				default:
					this.$nextTick(() => {
						this.drawBar();
					});
			}
		},
		mounted() {
			this.drawBar();
		},

		//获取数据
		list() {
			this.listLoading = true;
			let params = this.$utils.merger(this.searchParams, this.page);
			api.list(params, response => {
				this.listData = response.data;
				this.listLoading = false;
			})
		},
		//获取班级
		listCname() {
			this.listLoading = true;

			api.listCname({}, response => {
				this.postsList = response.data;
				this.listLoading = false;
			});
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
		// 清空搜索框
		clearSearchVal() {
			this.searchParams = {};
		}
	},
	mounted() {
		this.list();
		this.listCname();
	}
};
</script>

<style lang="scss" scoped="scoped">
#main {
	width: 600px;
	height: 400px;
	margin: auto;
	margin-top: 100px
}

#user-manage {
	height: 150px;
}

#config-manage {
	height: 150px;
}

#role-manage {
	height: 150px;
}



@import '~common/custom/css/common.scss';
</style>

