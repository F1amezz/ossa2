<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="24">
						<el-form-item label="头像" prop="head">
							<image-upload @change="change" v-model="formParameter.head" model="base64" :size="128"></image-upload>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="学生名字" prop="name">
							<el-input v-model="formParameter.dbColumn_sid" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="手机号码" prop="mobile">
							<el-input v-model="formParameter.mobile" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<!-- <el-row>
					<el-col :span="24">
						<el-form-item label="老师姓名" prop="tname">
							<el-input v-model="formParameter.tname" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row> -->
				<el-row>
					<el-col :span="24">
						<el-form-item label="班级名称" prop="clname">
							<el-input v-model="formParameter.dbColumn_cid" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<!-- <el-row>
					<el-col :span="24">
						<el-form-item label="所属课程名称+版本" prop="courname">
							<el-input v-model="formParameter.courname" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row> -->
				<el-row>
					<el-col :span="24">
						<el-form-item label="带班老师" prop="teachname">
							<el-input v-model="formParameter.dbColumn_tid" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="该班班长" prop="monitor">
							<el-input v-model="formParameter.monitor" autocomplete="off"></el-input>
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
import api from '@/api/study/myclass/myclass.js';
import imageUpload from '@/components/biz/imageUpload/imageUpload.vue';
import selectRoleTreeDialog from '@/components/biz/selectRoleTreeDialog/selectRoleTreeDialog.vue';
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
import wangEdit from '@/components/wangEdit/wangEdit.vue';
export default {
	name: 'edit',
	components: {
		popup,
		selectRoleTreeDialog,
		selectOptionDictionary,
		imageUpload,
		wangEdit
	},
	data() {
		return {
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			parameter: {},
			formParameter: {},
			rules: {
				//头像
				head: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 10000000,
						trigger: 'blur'
					}
				],
				//学生名字
				dbColumn_sid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 50,
						trigger: 'blur'
					}
				],
				//手机号码
				mobile: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 16,
						trigger: 'blur'
					}
				],
				//老师姓名
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
				//班级名称
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
				//所属课程名称+版本
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
				//带班老师
				teachname: [
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
				//该班班长
				monitor: [
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
