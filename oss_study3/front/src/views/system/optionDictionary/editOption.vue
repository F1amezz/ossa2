<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :disabled="disabled" :inline="true" label-width="80px"
			 :rules="rules">

				<el-divider>选项</el-divider>
				<div v-for="(item, index) in formParameter.dictionarydatas" :key="index">
					<el-row>
						<el-col :span="5">
							<el-form-item label="名称" :prop="`dictionarydatas[${index}].dkey`" :rules="rules.dkey" :disabled="parameter.id ? true : false">
								<el-input type="text" v-model="item.dkey" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="数据值" :prop="`dictionarydatas[${index}].dvalue`" :rules="rules.dvalue">
								<el-input type="text" v-model="item.dvalue" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="排序" :prop="`dictionarydatas[${index}].sort`" :rules="rules.sort">
								<el-input type="text" v-model="item.sort" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="描述" :prop="`dictionarydatas[${index}].description`">
								<el-input type="text" v-model="item.description" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="2">
							<el-form-item label="启用" :prop="`dictionarydatas[${index}].isdisable`" :rules="rules.isdisable">
								<el-switch v-model="item.isdisable" :active-value="1" :inactive-value="2">
								</el-switch>
							</el-form-item>
						</el-col>
						<el-col :span="2">
							<el-button @click="del(index)">删 除</el-button>
						</el-col>
					</el-row>
				</div>
				<el-row>
					<el-col :span="24">
						<el-button @click="add">添加选项</el-button>
					</el-col>
				</el-row>
			</el-form>
		</div>
		<div slot="footer">
			<el-button type="primary" v-if="!disabled" :loading="confirmLoading" @click="confirm()">确 定</el-button>
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
	import popup from '@/components/popup/drawerPopup.vue';
	import api from '@/api/system/optionDictionary/optionDictionary.js';
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
					dictionarydatas: [{}]
				},
				loading: false,
				rules: {
					dkey: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					dvalue: [{
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
					// description: [{
					// 	validator: this.$validate.required,
					// 	trigger: 'blur'
					// }],
					isdisable: [{
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
					this.popupLoading = true;
					//编辑
					api.updateOption(
						this.formParameter,
						response => {
							this.$utils.msg.success(response.msg);
							this.popupLoading = false;
							this.close();
							this.$parent.list();
						},
						response => {
							this.$utils.msg.warning(response.msg);
							this.popupLoading = false;
						}
					);
				});
			},
			init() {
				this.popupLoading = true;
				//清除等待
				this.confirmLoading = false;
				this.formParameter = {
					did: this.parameter.id,
					dictionarydatas: []
				};
				//清除校验
				this.$refs.form.clearValidate();
				let parameter = {
					did: this.parameter.id
				};
				api.toUpdateOption(parameter, response => {
					this.formParameter.dictionarydatas = response.data;
					this.popupLoading = false;
				});


			},
			add() {
				this.formParameter.dictionarydatas.push({
					dkey: '',
					dvalue: '',
					sort: '',
					did: this.parameter.id,
					description: '',
					isdisable: '1'
				});
			},
			del(index) {
				this.formParameter.dictionarydatas.splice(index, 1);
			},
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
