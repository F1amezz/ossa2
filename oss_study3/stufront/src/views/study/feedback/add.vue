<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					
						<el-col :span="12">
						<el-form-item label="科 目" prop="sbid">
							<el-input placeholder="请选择科目" disabled v-model="formParameter.sbname">
								<template slot="append">
									<el-button type="primary" @click="selectSub()">选择</el-button>
								</template>
							</el-input>
							<el-input v-model="formParameter.sbid" v-show="false"></el-input>
						</el-form-item>
					</el-col>
	                <el-col :span="12">
						<el-form-item label="学习时间" prop="subtime">
							<el-date-picker v-model="formParameter.subtime" align="right" type="date" placeholder="选择日期"
								:picker-options="pickerOptions">
							</el-date-picker>
						</el-form-item>
					</el-col>

			</el-row>
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
						 
								<wangEdit v-model="formParameter.content">
								</wangEdit>
						</el-form-item>
					</el-col>
				</el-row>

		    	<el-dialog title="选择绑定科目" :visible.sync="dialogVisible" width="60%" :append-to-body="true"
					 >

					<tree class="filter-tree" ref="permissionTree" check-strictly :items="subjectList" :data="subjectList"
						:defaultParent="0" @nodeClick="treeClick" :expand-on-click-node="true">
					</tree>
					<span slot="footer" class="dialog-footer">
						<el-button @click="dialogVisible = false">取 消</el-button>
						<el-button type="primary" @click="comfirmSub">确 定</el-button>
					</span>
			  </el-dialog>
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
import wangEdit from "@/components/wangEdit/wangEdit.vue";
import subApi from "@/api/edu/subject/subject.js";
import tree from "@/components/tree/tree.vue";

import api from '@/api/study/feedback/feedback.js';
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';

export default {
	name: 'edit',
	components: {
		popup,
		selectOptionDictionary,
		tree,
		wangEdit
	},
	data() {
		return {
			dialogVisible:false,
			subjectList:[],
			subData:[],
			treeRef: "permissionTree",
			treeExpand: false,
			currentNode: {},

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
						min: 10,
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
		treeClick(data){	this.subData = data;  },
		
		comfirmSub(){
			if (this.subData.children && this.subData.children.length != 0) {
				this.$alert('请选择最后一级科目', '错误', {
					confirmButtonText: '确定'
				});
			} else {
				console.log(this.subData);
				this.formParameter.sbname = this.subData.name;
				this.formParameter.sbid = this.subData.id;
				console.log(this.formParameter.sbid);
				this.dialogVisible = false;
			}
		},

		selectSub() {
			if(this.subData.length==0){
		        this.dialogVisible = true;
				subApi.list({}, (response) => {
					
				    this.subjectList = response.data;
					console.log(   this.subjectList )
			   });
		  }
		}, 

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
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
