<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :disabled="disabled" :inline="true" label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="24">
						<el-form-item label="字典名称" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="字典编号" prop="dkey">
							<el-input v-model="formParameter.dkey" autocomplete="off" :disabled="parameter.id ? true : false"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="属性值" prop="dvalue">
							<el-input v-model="formParameter.dvalue" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="描述" prop="description">
							<el-input v-model="formParameter.description" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="备注" prop="remarks">
							<el-input type="textarea" v-model="formParameter.remarks" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="启用" prop="isdisable">
							<el-switch v-model="formParameter.isdisable" :active-value="1" :inactive-value="2">
							</el-switch>
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
	import popup from '@/components/popup/drawerPopup.vue';
	import api from '@/api/system/dictionary/dictionary.js';
	export default {
		name: 'editDrawer',
		components: {
			popup
		},
		data() {
			return {
				disabled: false,
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {
					isdisable: 1
				},
				rules: {
					name: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					dkey: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					dvalue: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					type: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					// description: [{
					// 	validator: this.$validate.required,
					// 	trigger: 'blur'
					// }],
					// remarks: [{
					// 	validator: this.$validate.required,
					// 	trigger: 'blur'
					// }],
					isdisable: [{
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
			//提交表单
			confirm() {
				this.$utils.checkForm(this.$refs.form, () => {
					this.confirmLoading = true;
					if (!this.parameter.id) {
						//设置默认类型：1-选项、2-属性值、3-系统属性
						this.formParameter.type = 2;
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
				//清除内容
				this.formParameter = {
					isdisable: 1
				};
				//清除校验
				this.$refs.form.clearValidate();
				if (this.parameter.id) {
					//编辑查询数据
					let parameter = {
						id: this.parameter.id
					};
					api.toUpdate(parameter, response => {
						this.formParameter = response.data;
						this.popupLoading = false;
					});
				} else {
					//添加
					this.popupLoading = false;
				}
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
