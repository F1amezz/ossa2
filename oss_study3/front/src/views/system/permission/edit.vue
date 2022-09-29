<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :disabled="disabled" :inline="true" label-width="80px"
			 :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="类型" prop="type">
							<el-select v-model="formParameter.type" placeholder="请选择" :disabled="parameter.id ? true : false">
								<el-option label="展示菜单" :value="1"></el-option>
								<el-option label="功能菜单" :value="2"></el-option>
								<el-option label="功能按钮" :value="3"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="权限标识" prop="code">
							<el-input v-model="formParameter.code" autocomplete="off" :disabled="parameter.id ? true : false"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="权限名" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="排序" prop="sort">
							<el-input v-model="formParameter.sort" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>

					<el-col :span="12">
						<el-form-item label="组件" prop="component" v-if="formParameter.type == 2 ">
							<el-input v-model="formParameter.component" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="打开方式" prop="opentype" v-if="formParameter.type == 2 ">
							<el-select v-model="formParameter.opentype" placeholder="请选择">
								<el-option label="内部" :value="1"></el-option>
								<el-option label="外部" :value="2"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="url" prop="url" v-if="formParameter.type != 1 && formParameter.type != 3">
							<el-input v-model="formParameter.url" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="图标" prop="icon" v-if="formParameter.type != 3">
							<e-icon-picker v-model="formParameter.icon"></e-icon-picker>
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
	import api from '@/api/system/permission/permission.js';
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
				formParameter: {},
				rules: {
					type: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					code: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							min: 0,
							max: 32,
							trigger: 'blur'
						}
					],
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
					opentype: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					url: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					icon: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					sort: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.digits,
							trigger: 'blur'
						}
					],
					remarks: [{
						validator: this.$validate.length,
						min: 0,
						max: 200,
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
				this.formParameter = {
					type: 1,
				};
				//清空校验
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
					this.formParameter = this.$utils.merger(this.formParameter, this.parameter);
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
