<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="24">
						<el-form-item label="学生名字" prop="dbColumn_sid">
							<el-input v-model="formParameter.dbColumn_sid" autocomplete="off" disabled>
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="班级名字" prop="dbColumn_cid">
							<el-input v-model="formParameter.dbColumn_cid" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="班主任" prop="dbColumn_tid">
							<el-input v-model="formParameter.dbColumn_tid" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="课程+章节" prop="dbColumn_courseid">
							<el-input v-model="formParameter.dbColumn_courseid" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="标题" prop="title">
							<el-input v-model="formParameter.title" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="内容" prop="content">
							<el-input v-model="formParameter.content" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="24">
						<el-form-item label="评分" prop="score">
							<el-rate v-model="formParameter.score * 1" :colors="colors" disabled>
							</el-rate>
						</el-form-item>
					</el-col>
				</el-row>

				<el-row>
					<el-col :span="24">
						<el-form-item label="提交时间" prop="subtime">
							<el-date-picker v-model="formParameter.subtime" align="right" type="date" placeholder="选择日期"
								:picker-options="pickerOptions" disabled>
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
import api from '@/api/study/feedback/feedback.js';
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
export default {
	name: 'edit',
	components: {
		popup,
		selectOptionDictionary
	},
	data() {
		return {
						colors: { 3: '#99A9BF', 5: '#FF9900' },
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
			rules: {
				//学生名字
				dbColumn_sid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//班级名字
				dbColumn_cid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//班主任
				dbColumn_tid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//课程+章节
				dbColumn_courseid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//内容
				content: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 100,
						trigger: 'blur'
					}
				],
				//评分
				score: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 10,
						trigger: 'blur'
					}
				],
				//已读状态（0-未读，1-已读）
				readst: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 10,
						trigger: 'blur'
					}
				],
				//学生提交时间
				subtime: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//老师查看时间
				chetime: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//标题
				title: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
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
			}
		}
	}
};
</script>

<style lang="scss" scoped="scoped">
@import '~common/custom/css/common.scss';
@import '~common/custom/css/popup/popup.scss';
</style>
