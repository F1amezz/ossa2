<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :disabled="disabled" :inline="true" label-width="80px"
			 :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="角色标识" prop="code">
							<el-input v-model="formParameter.code" autocomplete="off" :disabled="parameter.id ? true : false"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="角色名" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="排序" prop="sort">
							<el-input v-model="formParameter.sort" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="部门" prop="departmentId">
							<select-department-tree-dialog v-model="formParameter.departmentId"></select-department-tree-dialog>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
				
						<el-form-item label="绩效公式" prop="jxfm">
							<el-input type="textarea" v-model="formParameter.jxfm" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="权限" prop="permissionIds">
							<permission-tree multiple v-model="formParameter.permissionIds" :relationType="{ Y: 'ps', N: 'p' }"></permission-tree>
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
	import selectDepartmentTreeDialog from '@/components/biz/selectDepartmentTreeDialog/selectDepartmentTreeDialog.vue';
	import permissionTree from '@/components/biz/permissionTree/permissionTree.vue';
	import popup from '@/components/popup/drawerPopup.vue';
	import api from '@/api/system/role/role.js';
	export default {
		name: 'editDrawer',
		components: {
			popup,
			selectDepartmentTreeDialog,
			permissionTree
		},
		data() {
			return {
				disabled: false,
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
				rules: {
					code: [{
							validator: this.$validate.required,
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
					}],
					departmentId: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					permissionIds: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					roldid: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],

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
				this.formParameter = {};
				//清空校验
				this.$refs.form.clearValidate();
				if (this.parameter.id) {
					//编辑查询数据
					let parameter = {
						id: this.parameter.id
					};
					api.toUpdate(parameter, response => {
						let permissionIds = [];
						for (let rolePermission of response.data.rolePermissions) {
							permissionIds.push(rolePermission.pid);
						}
						this.formParameter = this.$utils.merger(this.formParameter, response.data.role, {
							departmentId: response.data.roleDepartment.did
						}, {
							permissionIds: permissionIds
						});
						this.popupLoading = false;
					});
				} else {
					//添加
					this.formParameter.pid = this.parameter.pid;
					this.formParameter.level = this.parameter.level;
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
