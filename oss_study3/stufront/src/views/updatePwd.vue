<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true" label-width="80px"
			 :rules="rules">
				<el-row>
					<el-col :span="24">
						<el-form-item label="旧密码" prop="pwd">
							<el-input type="password" autocomplete="off" v-model="formParameter.pwd"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="新密码" prop="newPwd">
							<el-input type="password" autocomplete="off" v-model="formParameter.newPwd"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="再次输入新密码" prop="newPwd2">
							<el-input type="password" autocomplete="off" v-model="formParameter.newPwd2"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
		</div>
		<div slot="footer">
			<el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
	import popup from '@/components/popup/dialogPopup.vue';
	import api from '@/api/main.js';
	export default {
		name: 'editDrawer',
		components: {
			popup,
		},
		data() {
			var validatePwd2 = (rule, value, callback) => {
				if (value !== this.formParameter.newPwd) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				disabled: false,
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
				rules: {
					pwd: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}, {
						validator: this.$validate.length,
						min: 6,
						max: 16,
						trigger: 'blur'
					}],
					newPwd: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}, {
						validator: this.$validate.length,
						min: 6,
						max: 16,
						trigger: 'blur'
					}],
					newPwd2: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}, {
						validator: validatePwd2,
						trigger: 'blur'
					}, {
						validator: this.$validate.length,
						min: 6,
						max: 16,
						trigger: 'blur'
					}]
				}
			};
		},
		methods: {
			//打开弹框
			open(parameter, title, disabled, size) {
				this.parameter = parameter;
				this.disabled = disabled;
				this.$refs.popup.open(title, size);
			},
			//关闭弹框
			close() {
				this.disabled = false;
				this.$refs.popup.close();
			},
			//确认
			confirm() {
				this.$utils.checkForm(this.$refs.form, () => {
					this.confirmLoading = true;
					//编辑
					api.updatePwd(
						this.formParameter,
						response => {
							this.$utils.msg.success(response.msg);
							this.confirmLoading = false;
							this.close();
							this.$parent.list();
						},
						response => {
							this.$utils.msg.warning(response.msg);
							this.confirmLoading = false;
						}
					);
				});
			},
			init() {}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
