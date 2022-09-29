<template>

	<div class="tab-body">
		<el-form class="dataForm" ref="form" :model="formParameter" v-loading="loading" :inline="true"
			label-width="80px" :rules="rules">
			<el-row>
				<el-col :span="24">
					<el-form-item label="收件人" prop="to">
						<select-user-table-dialog lableKey="email" valueKey="email" v-model="formParameter.to">
						</select-user-table-dialog>
					</el-form-item>
				</el-col>
				<el-col :span="24">
					<el-form-item label="标题" prop="subject">
						<el-input v-model="formParameter.subject"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="24">
					<el-form-item label="发送类型" prop="sendType">
						<el-select v-model="formParameter.sendType">
							<el-option label="内容发送" value="1"></el-option>
							<el-option label="模版发送" value="2"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="24" v-if="formParameter.sendType==2">
					<el-form-item label="消息模版" prop="tcode">
						<el-select v-model="formParameter.tcode" @change="tcodeChange">
							<el-option v-for="(item,index) in templates" :label="(item.code+'-'+item.name)"
								:value="item.code"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="24" v-if="formParameter.sendType==2">
					<el-form-item label="模版内容"  >
						<div v-html='templateContent'></div> 
					</el-form-item>
				</el-col>
				<el-col :span="24" v-if="formParameter.sendType==1">
					<el-form-item label="邮件内容" prop="content">
						<wang-edit v-model="formParameter.content"></wang-edit>
					</el-form-item>
				</el-col>
				<el-col :span="24" v-if="formParameter.sendType==2">
					<el-form-item label="模版参数" prop="parameter">
						<el-input type="textarea" placeholder="请输入内容" v-model="formParameter.parameter" maxlength="200"
							show-word-limit>
						</el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row>
				<el-col :span="12">
					<el-form-item size="large">
						<el-button type="primary" @click="confirm" style="margin-left: 80px;">发送</el-button>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
	</div>

</template>

<script>
	import selectUserTableDialog from '@/components/biz/selectUserTableDialog/selectUserTableDialog.vue';
	import wangEdit from '@/components/wangEdit/wangEdit.vue';
	import api from '@/api/sms/emailSms/emailSms.js';
	export default {
		name: 'emailSms',
		components: {
			selectUserTableDialog,
			wangEdit
		},
		data() {
			return {
				loading: false,
				confirmLoading: false,
				formParameter: {},
				templates: [],
				templateContent:'',
				rules: {
					uid: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					subject: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					sendType: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					tcode: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					parameter: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					content: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
				}
			};
		},
		methods: {
			//消息模版改变
			tcodeChange(val) {
				let index = _.findIndex(this.templates, function(item) {
					return item.code == val || item.type == 2;
				});
				this.templateContent=this.templates[index].content;
			},
			//提交表单
			confirm() {
				this.$utils.checkForm(this.$refs.form, () => {
					this.confirmLoading = true;
					api.send(this.formParameter, response => {
						this.$utils.msg.success(response.msg);
						this.confirmLoading = false;
					})
				});
			},
			init() {
				this.loading = true;
				this.$set(this.formParameter, 'sendType', "1");
				let parameter = null;
				api.toSend(parameter, response => {
					this.templates = response.data;
					this.loading = false;
				});
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.init();
			})
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
