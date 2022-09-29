<template>
	<div class="tab-body">
		<el-row>
			<el-col :span="12">
				<el-card class="box-card userInfo">
					<div slot="header" class="userInfo-header clearfix"><span>个人信息</span></div>
					<el-row class="userInfo-body ">
						<el-col :span="12" class="userHead">
							<!-- <img id="headImg" :src="user.head" v-if="user.head" /> -->
							<!-- <img id="headImg" src="~assets/img/male.jpg" /> -->
							<image-upload @change="change" v-model="formParameter.head" model="base64"  :size="128"></image-upload>
							<div style=" font-weight: bold;">
								{{ formParameter.name }}
							</div>
						</el-col>
						<el-col :span="12" class="info">
							<div>
								<label>性别:</label>
								<span id="sex" name="sex">{{ formParameter.dictionary_sex }}</span>
							</div>
							<div>
								<label>生日:</label>
								<span id="birth" name="birth">{{ formParameter.birth }}</span>
							</div>
							<div>
								<label>手机号:</label>
								<span id="mobile" name="mobile">{{ formParameter.mobile }}</span>
							</div>
							<div>
								<label>角色:</label>
								<span id="rname" name="rname">{{ formParameter.rname }}</span>
							</div>
							<div>
								<label>部门:</label>
								<span id="dname" name="dname">{{ formParameter.dname }}</span>
							</div>
							<div>
								<label>邮件:</label>
								<span id="email" name="email">{{ formParameter.email }}</span>
							</div>
							<div class="operation">
								<el-button type="primary" @click="updateInfo">编辑资料</el-button>
								<el-button type="primary" @click="updatePwd">修改密码</el-button>
							</div>

						</el-col>
					</el-row>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card class="box-card" :body-style="{height:'80%'}">
					<div slot="header" class="clearfix"><span>登录记录</span></div>
					<div class="dataTable autoFlex" v-if="listData" style="height: 100%;">
						<el-table class="table" style="height: 100%;" :data="listData.records" stripe border
							height="auto" @selection-change="selectionChange" v-loading="listLoading">
							<el-table-column prop="uname" label="用户" show-overflow-tooltip></el-table-column>
							<el-table-column prop="ip" label="登录ip" show-overflow-tooltip></el-table-column>
							<el-table-column prop="useragent" label="客户端" show-overflow-tooltip></el-table-column>
							<el-table-column prop="logintime" label="登录时间" show-overflow-tooltip></el-table-column>
							<el-table-column prop="loginresult" label="登录结果" show-overflow-tooltip :formatter="(row, column) => {
										return row.loginresult == 1 ? '成功' : '失败';
									}"></el-table-column>
						</el-table>
						<div class="pageBar">
							<el-pagination @size-change="sizeChange" @current-change="currentChange"
								:total="listData.total" :page-size="page.size" :current-page="page.current"
								:layout="this.$constant.page.layout" :page-sizes="this.$constant.page.pageSizes">
							</el-pagination>
						</div>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="24">
				<el-card class="box-card" :body-style="{height:'80%',padding:'0'}">
					<div slot="header" class="clearfix"><span>登录记录图</span></div>
					<div id="loginrecord" style="height: 100%;"></div>
				</el-card>
			</el-col>
		</el-row>
		<update-info-dialog ref="updateInfoDialog"></update-info-dialog>
		<update-pwd-dialog ref="updatePwdDialog"></update-pwd-dialog>

	</div>
</template>
<script>
	import 'echarts/lib/chart/bar';
	import api from '@/api/main.js';

	import updateInfoDialog from './updateInfo.vue';
	import updatePwdDialog from './updatePwd.vue';
	import imageUpload from '@/components/biz/imageUpload/imageUpload.vue';

	export default {
		name: 'mainPage',
		components: {
			updateInfoDialog,
			updatePwdDialog,
			imageUpload
		},

		data() {
			return {
				listLoading: false,
				updateInfoDisplay: false,
				updatePwdDisplay: false,
				updatePwdParameter: {},
				listData: [],
				page: new this.$constant.pageObj(),
				formParameter: {},
				barData: []
			};
		},
		methods: {
			//获取数据
			list() {
				this.listLoading = true;
				let params = this.$utils.merger(this.searchParams, this.page);
				api.getInfo(params, response => {
					this.formParameter = response.data;
					this.listLoading = false;
				})
				api.getLoginrecordCountYear(params, response => {
					this.barData = response.data;
					this.makePiePicture('loginrecord');
					this.listLoading = false;
				})
				api.getLoginrecord(params, response => {
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
			//编辑用户信息
			updateInfo() {
				this.$refs.updateInfoDialog.open({}, '编辑');
			},
			//编辑密码
			updatePwd() {
				this.$refs.updatePwdDialog.open({}, '编辑');
			},

			makePiePicture(dom) {
				// 基于准备好的dom，初始化echarts实例
				var myChart = this.$echarts.init(document.getElementById(dom));
				// 指定图表的配置项和数据
				var option = {
					xAxis: {
						type: 'category',
						data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
					},
					yAxis: {
						type: 'value'
					},
					grid: {
						left: '3%',
						top: '8%',
						right: '3%',
						bottom: '8%',
						containLabel: true
					},
					series: [{
						data: this.barData,
						type: 'bar',
					}],
					tooltip: {}
				};

				myChart.setOption(option);
			},
			change(val) {
			   this.confirmLoading = true;
				//编辑
				console.log(this.formParameter.head)
				api.updateInfo(
					this.formParameter,
					response => {
						this.$utils.msg.success(response.msg);
						this.confirmLoading = false;
					},
					response => {
						this.$utils.msg.warning(response.msg);
						this.confirmLoading = false;
					}
				); 
			}
		},
		mounted() {
			this.list();
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/main.scss';
</style>
