<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="姓名" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="登录名" prop="lname">
							<el-input v-model="formParameter.lname" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="手机" prop="mobile">
							<el-input v-model="formParameter.mobile" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="email" prop="email">
							<el-input v-model="formParameter.email" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="生日" prop="birth">
							<el-date-picker v-model="formParameter.birth" type="date" placeholder="选择日期"
								value-format="yyyy-MM-dd"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="性别" prop="sex">
							<select-option-dictionary v-model="formParameter.sex" dkey="sex" :multiple="false"
								:stringMode="true"></select-option-dictionary>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="角色" prop="roleId">
							<select-role-tree-dialog v-model="formParameter.roleId"></select-role-tree-dialog>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="备注" prop="remarks">
							<el-input type="textarea" v-model="formParameter.remarks"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="头像" prop="head">
							<image-upload v-model="formParameter.head" model="base64" :size="128"></image-upload>
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
	import selectRoleTreeDialog from '@/components/biz/selectRoleTreeDialog/selectRoleTreeDialog.vue';
	import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
	import wangEdit from '@/components/wangEdit/wangEdit.vue';
	import imageUpload from '@/components/biz/imageUpload/imageUpload.vue';

	import api from '@/api/system/user/user.js';
	export default {
		name: 'editDrawer',
		components: {
			popup,
			selectRoleTreeDialog,
			selectOptionDictionary,
			imageUpload,
			wangEdit
		},
		data() {
			return {
				disabled: false,
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
				rules: {
					name: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							min: 0,
							max: 50,
							trigger: 'blur'
						}
					],
					head: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					birth: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					lname: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							min: 0,
							max: 50,
							trigger: 'blur'
						}
					],
					email: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.email,
							trigger: 'blur'
						}
					],
					sex: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					mobile: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.phone,
							trigger: 'blur'
						}
					],
					remarks: [{
						validator: this.$validate.length,
						min: 0,
						max: 200,
						trigger: 'blur'
					}],
					roleId: [{
						validator: this.$validate.required,
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
				this.$nextTick(() => {
					this.init();
				})
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
					if (!this.parameter.id) {
						//添加
						api.save(
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
					} else {
						//编辑
						api.update(
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
					}
				});
			},
			init() {
				this.popupLoading = true;
				//清除等待
				this.confirmLoading = false;
				//清空内容
				this.formParameter = {};
				//清空校验
				this.$refs.form.clearValidate();
				if (this.parameter.id) {
					//编辑查询数据
					let parameter = {
						id: this.parameter.id
					};
					api.toUpdate(parameter, response => {
						this.formParameter = this.$utils.merger(this.formParameter, response.data.user, {
							roleId: response.data.userRole.rid
						});
						this.popupLoading = false;
					});
				} else {
					//添加
					this.popupLoading = false;
					this.formParameter = {
						area: ["100000", "900000"]
					};
				}
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
