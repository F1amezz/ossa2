<template>
	<div class="tab-body">
		<el-row>
			<el-col :span="12">
				<el-card class="box-card userInfo">
					<div slot="header" class="userInfo-header clearfix"><span>个人信息</span></div>
					<el-row class="userInfo-body ">
						<el-col :span="8" class="userHead">
							<!-- <img id="headImg" :src="user.head" v-if="user.head" /> -->
							<!-- <img id="headImg" src="~assets/img/male.jpg" /> -->
							<image-upload @change="change" v-model="formParameter.head" model="base64" :size="128">
							</image-upload>
							<div style=" font-weight: bold;">
								{{ formParameter.name }}
							</div>
						</el-col>
					<el-col :span="16">
							<el-col :span="10" class="info">
								<div>
									<label>性别:</label>
									<span id="sex" name="sex">
										<span v-if="formParameter.dictionary_sex == 1">男</span>
										<span v-if="formParameter.dictionary_sex == 2">女</span>
									</span>
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
									<span id="sname" name="sname">{{ formParameter.sname }}</span>
								</div>
								<div>
									<label>部门:</label>
									<span id="dname" name="dname">{{ formParameter.dname }}</span>
								</div>
								<div>
									<label>邮件:</label>
									<span id="email" name="email">{{ formParameter.email }}</span>
								</div>
							</el-col>
							<el-col :span="10" class="info">
								<div>
									<label>籍贯:</label>
								<span id="nativeplace" name="nativeplace">{{ formParameter.nativeplace }}</span>
								</div>
								<div>
									<label>班级:</label>
									<span id="cid" name="cid">{{ formParameter.dbColumn_cid }}</span>
								</div>
								<div>
									<label>职务:</label>
									<span id="groupleader" name="groupleader">
										<span v-if="formParameter.groupleader == 1">班长</span>
										<span v-if="formParameter.groupleader == 2">组长</span>
									</span>
								</div>
								<div>
									<label>状态:</label>
									<span id="state" name="state">
										<span v-if="formParameter.state== 1">在读</span>
										<span v-if="formParameter.state == 2">休学</span>
										<span v-if="formParameter.state == 3">毕业</span>
									</span>
								</div>
								
							</el-col>
							
						</el-col>
						<div class="operation">
							<el-button type="primary" @click="updateInfo">编辑资料</el-button>
							<el-button type="primary" @click="updatePwd">修改密码</el-button>
						</div>
					</el-row>
					
				</el-card>
			</el-col>

		</el-row>
		<update-info-dialog ref="updateInfoDialog"></update-info-dialog>
		<update-pwd-dialog ref="updatePwdDialog"></update-pwd-dialog>

	</div>
</template>
<script>

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
				console.log(response.data);
				this.formParameter = response.data;
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
			/* this.confirmLoading = true;
			//编辑
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
			); */
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
