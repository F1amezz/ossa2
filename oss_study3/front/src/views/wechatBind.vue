<template>
	<div class="background">
		<div class="findPwd-frame">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span id="logo" style="color: #000 !important">绑定账号</span>
				</div>
				<div class="text item">
					<el-form class="dataForm" ref="form" :model="formParameter" v-loading="loading" :rules="rules">
						<el-form-item prop="name">
							<el-input id="name" v-model="formParameter.name" autocomplete="off" placeholder="账号">
							</el-input>
						</el-form-item>
						<el-form-item prop="pwd">
							<el-input id="pwd" v-model="formParameter.pwd" show-password autocomplete="off"
								placeholder="密码"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" :loading="loading" @click="submit('form')">提交
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
	import api from "@/api/index.js";
	export default {
		name: "findPwd",
		data() {
			return {
				formParameter: {
					wxuid: ""
				},
				loading: false,
				rules: {
					name: [{
						validator: this.$validate.required,
						trigger: "blur",
					}, ],
					pwd: [{
						validator: this.$validate.required,
						trigger: "blur",
					}, ],
				},

			};
		},
		methods: {
			toLogin() {
				this.$router.replace("/login");
			},
			submit(name) {
				this.loading = true;
				this.$utils.checkForm(this.$refs.form, () => {
					let parameter = this.$utils.merger(this.formParameter);
					api.wechatBind(parameter, response => {
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
		created() {
			this.formParameter.openid = this.$route.query.uid
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import "~common/custom/css/common.scss";
	@import "~common/custom/css/findPwd.scss";
</style>
