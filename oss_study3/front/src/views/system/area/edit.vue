<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :disabled="disabled" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="区域编号" prop="id">
							<el-input v-model="formParameter.id" autocomplete="off"
								:disabled="parameter.id ? true : false"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="区域名称" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="地名简称" prop="simplename">
							<el-input v-model="formParameter.simplename" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="区域等级" prop="level">
							<el-select v-model="formParameter.level" placeholder="请选择">
								<el-option label="国家" :value="0"></el-option>
								<el-option label="省份" :value="1"></el-option>
								<el-option label="城市" :value="2"></el-option>
								<el-option label="县区" :value="3"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="城市编码" prop="citycode">
							<el-input v-model="formParameter.citycode" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="邮政编码" prop="zipcode">
							<el-input v-model="formParameter.zipcode" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="组合名称" prop="mername">
							<el-input v-model="formParameter.mername" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="拼音" prop="pinyin">
							<el-input v-model="formParameter.pinyin" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="12">
						<el-form-item label="经度" prop="lng">
							<el-input v-model="formParameter.lng" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="纬度" prop="lat">
							<el-input v-model="formParameter.lat" autocomplete="off"></el-input>
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
	import api from '@/api/system/area/area.js';
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
					id: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					name: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					pid: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					simplename: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					level: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					citycode: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					zipcode: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					mername: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					lng: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					lat: [{
						validator: this.$validate.required,
						trigger: 'blur'
					}],
					pinyin: [{
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
								this.$caches.areas.remove();
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
								this.$caches.areas.remove();
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
