<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="24">
						<el-form-item label="标题" prop="title">
							<el-input v-model="formParameter.title" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="内容" prop="content">
							<el-input v-model="formParameter.content" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="24">
						<el-form-item label="提交时间" prop="subtime">
							<el-date-picker v-model="formParameter.subtime" align="right" type="date" placeholder="选择日期"
								:picker-options="pickerOptions">
							</el-date-picker>
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
// import api from '@/api/study/feedback/feedback.js';
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
export default {
	name: 'edit',
	components: {
		popup,
		selectOptionDictionary
	},
	data() {
		return {
			pickerOptions: {
				disabledDate(time) {
					return time.getTime() > Date.now();
				},
				shortcuts: [{
					text: '今天',
					onClick(picker) {
						picker.$emit('pick', new Date());
					}
				}, {
					text: '昨天',
					onClick(picker) {
						const date = new Date();
						date.setTime(date.getTime() - 3600 * 1000 * 24);
						picker.$emit('pick', date);
					}
				}, {
					text: '一周前',
					onClick(picker) {
						const date = new Date();
						date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
						picker.$emit('pick', date);
					}
				}]
			},
			colors: { 3: '#99A9BF', 5: '#FF9900' },
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			parameter: {},
			formParameter: {},

		};
	},
	methods: {
		//打开弹框
		open(parameter, title, disabled) {
			this.parameter = parameter;
			this.disabled = disabled;
			this.$refs.popup.open(title);
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
			}
		}
	}
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
