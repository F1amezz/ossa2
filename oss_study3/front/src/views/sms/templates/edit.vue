<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="模版标识" prop="code">
							<el-input v-model="formParameter.code" :disabled="parameter.id ? true : false"
								autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="类型" prop="type">
							<select-option-dictionary v-model="formParameter.type" dkey="template-type">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="模版名称" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="模版内容" prop="content">
							<wang-edit ref="wangEdit" v-model="formParameter.content" :disable="disabled"></wang-edit>
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
	import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
	import wangEdit from '@/components/wangEdit/wangEdit.vue';
	import api from '@/api/sms/templates/templates.js';
	export default {
		name: 'edit',
		components: {
			popup,
			wangEdit,
			selectOptionDictionary
		},
		data() {
			return {
				disabled: false,
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
				rules: {
					//模版标识
					code: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 32,
							trigger: 'blur'
						}
					],
					//类型 1-站内信、2-邮件
					type: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 11,
							trigger: 'blur'
						}
					],
					//模版名称
					name: [{
							validator: this.$validate.required,
							trigger: 'blur'
						},
						{
							validator: this.$validate.length,
							max: 32,
							trigger: 'blur'
						}
					],
					//模版内容
					content: [{
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
						this.$refs.wangEdit.init();
						this.popupLoading = false;
					});
				} else {
					//添加
					this.popupLoading = false;
					this.$refs.wangEdit.init();
				}
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
