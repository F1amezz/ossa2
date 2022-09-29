<template>
	<div class="background">
		<div class="findPwd-frame">
			<el-card class="box-card">
				<div slot="header" class="clearfix"><span id="logo">找回密码</span></div>
				<div class="text item">
					<el-form class="dataForm" ref="form" :model="formParameter" v-loading="loading" :rules="rules">
						<el-form-item prop="email">
							<el-input v-model="formParameter.email" autocomplete="off" :disabled="emailIsDisabled"
								placeholder="邮箱" style="width: 210px;"></el-input>
							<el-button type="primary" :loading="sendEmailButtonLoading"
								:disabled="sendEmailButtonIsDisabled" @click="sendEmail">{{sendEmailButtonText}}
							</el-button>
						</el-form-item>
						<el-form-item prop="code">
							<el-input id="code" v-model="formParameter.code" autocomplete="off" :disabled="isDisabled"
								placeholder="验证码"></el-input>
						</el-form-item>
						<el-form-item prop="newPwd">
							<el-input id="newPwd" v-model="formParameter.newPwd" show-password :disabled="isDisabled"
								autocomplete="off" placeholder="新密码"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" :disabled="isDisabled" :loading="loading" @click="changePwd">修改密码
							</el-button>
						</el-form-item>
						<el-link type="primary" @click="toLogin">返回登录</el-link>
					</el-form>
				</div>
			</el-card>
		</div>
	</div>
</template>
<script>
	import api from '@/api/index.js';
	export default {
		name: 'findPwd',
		data() {
			return {
				formParameter: {},
				loading: false,
				emailIsDisabled: false,
				sendEmailButtonLoading: false,
				sendEmailButtonText: '发送邮件',
				sendEmailButtonIsDisabled: false,
				isDisabled: true,
				rules: {
					email: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.email,
							trigger: 'blur'
						}
					],
					code: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					newPwd: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
				},
				//倒计时
				count: 60,
				//定时器对象
				t: null,
			};
		},
		methods: {
			toLogin() {
				this.$router.replace('/login');
			},
			sendEmail() {
				this.$utils.checkField(this.$refs.form, 'email', () => {
					this.sendEmailButtonLoading = true;
					let parameter = {
						"email": this.formParameter.email
					};
					api.sendFindPwdEmail(parameter, response => {
						this.$utils.msg.success(response.msg);
						this.sendEmailButtonLoading = false;
						this.emailIsDisabled = true;
						this.sendEmailButtonIsDisabled = true;
						this.isDisabled = false;
						this.t = setInterval(this.countdown, 1000);
					}, response => {
						this.$utils.msg.warning(response.msg);
						this.sendEmailButtonLoading = false;
					});
				});
			},
			countdown() {
				this.count--;
				if (this.count < 0) {
					this.count = 60;
					this.isDisabled = true;
					this.emailIsDisabled = false;
					this.sendEmailButtonText = '发送邮件';
					this.sendEmailButtonIsDisabled = false;
					this.$refs.form.clearValidate();
					clearInterval(this.t);
					return;
				}
				this.sendEmailButtonText = this.count + "秒";
			},
			changePwd() {
				this.loading = true;
				this.$utils.checkForm(this.$refs.form, () => {
					let parameter = this.$utils.merger(this.formParameter);
					api.findPwd(parameter, response => {
						this.loading = false;
						this.$utils.msg.success(response.msg);
						this.$router.replace('/login');
					}, response => {
						this.loading = false;
						this.$utils.msg.warning(response.msg);

					})
				});

			}
		},
		beforeDestroy() {
			// 组件销毁前调用
			clearInterval(this.t);
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/findPwd.scss';
</style>
