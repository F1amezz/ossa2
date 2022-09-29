<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true" label-width="80px"
			 :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="姓名" prop="name">
							<el-input autocomplete="off" v-model="formParameter.name"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="登录名" prop="lname">
							<el-input autocomplete="off" v-model="formParameter.lname" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="email" prop="email">
							<el-input autocomplete="off" v-model="formParameter.email"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="性别">
							<el-select placeholder="请选择" v-model="formParameter.sex">
								<el-option label="男" :value="1"></el-option>
								<el-option label="女" :value="2"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="学校" prop="school">
							<el-input autocomplete="off" v-model="formParameter.school"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="专业" prop="professional">
							<el-input autocomplete="off" v-model="formParameter.professional" ></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="手机" prop="mobile">
							<el-input autocomplete="off" v-model="formParameter.mobile" ></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="生日">
							<el-date-picker type="date" v-model="formParameter.birth" placeholder="选择日期" value-format="yyyy-MM-dd"></el-date-picker>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="籍贯" prop="nativeplace">
							<el-input autocomplete="off" v-model="formParameter.nativeplace" ></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="紧急联系人关系：">
							<el-select autocomplete="off" v-model="formParameter.relationship">
										<el-option label="父亲" :value="1"></el-option>
										<el-option label="母亲" :value="2"></el-option>
										<el-option label="兄弟" :value="3"></el-option>
										<el-option label="姐妹" :value="4"></el-option>
										<el-option label="亲戚" :value="5"></el-option>
										<el-option label="朋友" :value="6"></el-option>
										
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="紧急联系人" prop="rname">
							<el-input autocomplete="off" v-model="formParameter.rname" ></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="紧急联系人电话">
							<el-input autocomplete="off" v-model="formParameter.rphone"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="班级">
							<el-input autocomplete="off" v-model="formParameter.dbColumn_cid" disabled></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="职务">
							<el-select autocomplete="off" v-model="formParameter.groupleader" disabled>
										<el-option label="班长" :value="1"></el-option>
										<el-option label="组长" :value="2"></el-option>
							</el-select>
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
	// import api from "@/api/stus/student/student.js";
	export default {
		name: 'editDrawer',
		components: {
			popup,
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
					rphone: [{
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
					//编辑
					api.updateInfo(
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
			init() {
				this.popupLoading = true;
				//清除等待
				this.confirmLoading = false;
				//清空内容
				this.formParameter = {};
				//清空校验
				this.$refs.form.clearValidate();
				this.formParameter = {};
				//编辑查询数据
				let parameter = {};
				api.toUpdateInfo(parameter, response => {
					this.formParameter = response.data;
					this.popupLoading = false;
				});
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
