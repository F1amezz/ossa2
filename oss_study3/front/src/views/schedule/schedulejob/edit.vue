<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="任务名称" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="bean" prop="beanname">
							<el-input v-model="formParameter.beanname" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="方法" prop="method">
							<el-input v-model="formParameter.method" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="参数" prop="params">
							<el-input v-model="formParameter.params" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="状态" prop="state">
							<el-switch v-model="formParameter.state" :active-value="1" :inactive-value="2">
							</el-switch>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="保存日志" prop="islog">
							<el-switch v-model="formParameter.islog" :active-value="1" :inactive-value="2">
							</el-switch>
						</el-form-item>
					</el-col>

					<el-col :span="12">
						<el-form-item label="cron" prop="cron">
							<el-input v-model="formParameter.cron" autocomplete="off"></el-input>
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
	import api from '@/api/schedule/schedulejob/schedulejob.js';
	export default {
		name: 'edit',
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
					//任务名称
					name: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 50,
							trigger: 'blur'
						}
					],
					//spring bean名称
					beanname: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 200,
							trigger: 'blur'
						}
					],
					//方法
					method: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 200,
							trigger: 'blur'
						}
					],
					//参数
					params: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 2000,
							trigger: 'blur'
						}
					],
					//状态 1-正常、2-暂停
					state: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 11,
							trigger: 'blur'
						}
					],
					//是否保存日志 1-是、2-否
					islog: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 11,
							trigger: 'blur'
						}
					],
					//cron表达式
					cron: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 100,
							trigger: 'blur'
						}
					],
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
						let parameter = this.$utils.merger(this.formParameter);
						api.save(parameter,
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
						let parameter = this.$utils.merger(this.formParameter);
						api.update(parameter,
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
				//清楚校验
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
					this.formParameter.state=1;
					this.formParameter.islog=1;
				}
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
