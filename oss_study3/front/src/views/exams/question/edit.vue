<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="100px" :rules="rules">
				<el-row>
					<el-col :span="12">
						<el-form-item label="试题类型" prop="type">
							<select-option-dictionary v-model="formParameter.type" dkey="exam-types"
								:disabled="bjdisabled">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="是否主观题" prop="isSubjective">
							<select-option-dictionary v-model="formParameter.isSubjective" dkey="is-subjective"
								:disabled="bjdisabled">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="科 目" prop="sbid">
							<el-input placeholder="请选择科目" readonly v-model="formParameter.sbname">
								<template slot="append">
									<el-button v-if="title == '添加'" type="primary" @click="selectSub()">选择</el-button>
								</template>
							</el-input>
							<el-input v-model="formParameter.sbid" v-show="false"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="来 源" prop="reference">
							<select-option-dictionary v-model="formParameter.reference" dkey="exam-reference">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="考 点" prop="examingPoint">
							<el-input v-model="formParameter.examingPoint" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<!-- <el-col :span="12">
						<el-form-item label="关键字" prop="keyword">
							<el-input v-model="formParameter.keyword" autocomplete="off"></el-input>
						</el-form-item>
					</el-col> -->
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="题 目" prop="name">
							<el-input type="textarea" :autosize="{ minRows: 3 }" v-model="formParameter.name">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="题目解释" prop="analysis">
							<el-input type="textarea" v-model="formParameter.analysis" autocomplete="off">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<!-- -----------单选题----------- -->
				<template v-if="(formParameter.type) == 1">
					<el-divider content-position="left">单选题</el-divider>
					<!-- 单选题选项 -->
					<el-row v-for="(item, index) in options" v-bind:key="index">
						<el-col :span="24">
							<el-form-item :label="item.o">
								<el-input v-model="item.n"> </el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<!-- 单选题答案 -->
					<el-row>
						<el-col :span="24">
							<el-form-item label="答案" prop="answer">
								<el-radio-group v-model="formParameter.answer" v-if="!disabled">
									<el-radio label="A"></el-radio>
									<el-radio label="B"></el-radio>
									<el-radio label="C"></el-radio>
									<el-radio label="D"></el-radio>
								</el-radio-group>
								<div v-show="disabled">{{ daAn }}</div>
							</el-form-item>
						</el-col>
					</el-row>
				</template>
				<!-- 多选题 -->
				<template v-else-if="(formParameter.type) == 2">
					<el-divider content-position="left">多选题</el-divider>
					<!-- 多选题选项 -->
					<el-row v-for="(item, index) in options" v-bind:key="index">
						<el-col :span="24">
							<el-form-item :label="item.o">
								<el-input v-model="item.n">

								</el-input>
								<el-button type="primary" icon="el-icon-circle-plus-outline"
									v-show="(options.length - 1) == index" @click="addSelectItem()"></el-button>
								<el-button type="primary" icon="el-icon-remove-outline"
									v-show="(options.length - 1) == index" @click="removeSelectItem()"></el-button>
							</el-form-item>
						</el-col>
					</el-row>


					<!-- 多选题答案 -->
					<el-row>
						<el-col :span="24">
							<el-form-item label="答案" prop="answer">
								<el-checkbox-group v-model="checkList" v-show="!disabled">
									<el-checkbox v-for="(item, index) in options" v-bind:key="index" :label="item.o">
									</el-checkbox>
								</el-checkbox-group>
								<div v-show="disabled">{{ daAn }}</div>

							</el-form-item>
						</el-col>
					</el-row>
				</template>
				<template v-else-if="(formParameter.type) == 3">
					<el-divider content-position="left">判断题</el-divider>
					<el-row>
						<el-col :span="24">
							<el-form-item label="答案">
								<el-radio-group v-model="formParameter.answer" size="medium">
									<el-radio border label="正确"></el-radio>
									<el-radio border label="错误"></el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
					</el-row>
				</template>
				<template v-else-if="(formParameter.type) == 4">
					<el-divider content-position="left">填空题</el-divider>
					<el-row>
						<el-col :span="24">
							<el-form-item label="答案" prop="answer">
								<el-input type="textarea" :rows="1" v-model="formParameter.answer" autocomplete="off">
								</el-input>
							</el-form-item>
						</el-col>
					</el-row>
				</template>
				<template v-else>
					<el-divider content-position="left">简答题、编程题、逻辑题</el-divider>
					<!-- 简答题 -- 答案填写   --答案回显 -->
					<el-row v-if="this.title != '详情'">
						<el-col :span="24" v-if="(formParameter.type) == 5">
							<el-form-item label="答案" prop="answer">
								<el-input type="textarea" :rows="4" v-model="formParameter.answer" autocomplete="off"
									placeholder="请输入答案" :autosize="{ minRows: 4 }">
								</el-input>
							</el-form-item>
						</el-col>
						<!-- ----------编程题、逻辑题----------- -- 答案填写 -->
						<el-col :span="24" v-if="(formParameter.type) > 5">
							<el-form-item label="答案" prop="answer">
								<wangEditQu v-model="formParameter.answer">
								</wangEditQu>
							</el-form-item>
						</el-col>
					</el-row>
					<!-- 答案回显 -->
					<!-- 简答题 -- 答案 -->
					<el-row v-else>
						<el-col :span="24" v-if="(formParameter.type) == 5">
							<el-form-item label="答案" prop="answer">
								<el-input type="textarea" :rows="4" v-model="formParameter.answer" autocomplete="off">
								</el-input>
							</el-form-item>
						</el-col>
						<!-- 编程题、逻辑题 -- 答案 -->
						<el-col :span="24" v-if="(formParameter.type) > 5">
							<el-form-item label="答案" prop="answer">
								<div v-html="formParameter.answer" class="content-html"></div>
							</el-form-item>
						</el-col>
					</el-row>

				</template>

				<el-dialog title="选择绑定科目" :visible.sync="dialogVisible" width="60%" :append-to-body="true">

					<tree class="filter-tree" ref="permissionTree" check-strictly :items="permissionList"
						:data="permissionList" :defaultParent="0" @nodeClick="treeClick" :expand-on-click-node="true">
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
import api from '@/api/exams/question/question.js';
import wangEditQu from "@/components/wangEdit/wangEditQu.vue";
import subApi from "@/api/edu/subject/subject.js";
import tree from "@/components/tree/tree.vue";
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
import { log } from 'console';
export default {
	name: 'edit',
	components: {
		popup,
		selectOptionDictionary,
		wangEditQu,
		tree
	},
	data() {
		return {
			subData: [],
			dialogVisible: false,
			treeRef: "permissionTree",
			treeExpand: false,
			permissionList: [],
			currentNode: {},

			title: '',
			bjdisabled: false,
			daAn: '',
			radio: 1,
			checkList: [],
			menus: [],
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			parameter: {},
			options: [
				{ o: 'A', n: '' },
				{ o: 'B', n: '' },
				{ o: 'C', n: '' },
				{ o: 'D', n: '' }],
			formParameter: { answer: '', type: 1, isSubjective: 2, reference: '自编题' },

			rules: {
				name: [
					{ required: true, message: '请填写试题内容', trigger: 'blur' }
				],
				answer: [
					{ required: true, message: '请填写答案', trigger: 'blur' }
				],


			}

		};
	},
	watch: {
		"formParameter.type": function (newVal, oldVal) {
			if (this.title == '添加') {
				if (newVal < 5) {
					// this.formParameter.isSubjective=1;
					this.formParameter = { answer: '', type: newVal, isSubjective: 2, reference: '自编题' };
				} else {
					// this.formParameter.isSubjective = 2;
					this.formParameter = { answer: '', type: newVal, isSubjective: 1, reference: '自编题' };
				}
			}
		},

		"checkList": function (newVal, oldVal) {

			let str = '';
			for (let index = 0; index < newVal.length; index++) {
				if (index != newVal.length - 1) {
					str = str + newVal[index] + ',';
				} else {
					str = str + newVal[index]
				}
			}
			this.formParameter.answer = str;

		},

	},
	methods: {
		//打开弹框b
		open(parameter, title, disabled, size) {
			this.parameter = parameter;
			console.log(parameter);
			this.title = title;
			this.disabled = disabled;
			this.bjdisabled = disabled;
			if (title == '编辑') {
				this.bjdisabled = !disabled;
			}

			this.$refs.popup.open(title, size);
			this.$nextTick(() => {

				this.init();

			});

		},
		treeClick(data) {
			// this.formParameter.sbid=data.
			this.subData = data;
			console.log(this.subData);

		},
		selectSub() {
			this.dialogVisible = true;
			subApi.list({}, (response) => {
				this.permissionList = response.data;
			});

			// if (this.subData.length == 0 || this.subData == []) {
			// 	subApi.list({}, (response) => {
			// 		this.permissionList = response.data;
			// 		this.dialogVisible = true;
			// 	});
			// }
		},
		comfirmSub() {
			if (this.subData.children.length != 0) {
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
		//关闭弹框
		close() {
			this.disabled = false;
			this.bjdisabled = false;
			this.$refs.popup.close();
			this.options = [
				{ o: 'A', n: '' },
				{ o: 'B', n: '' },
				{ o: 'C', n: '' },
				{ o: 'D', n: '' }];
			this.checkList = [];
		},
		//提交表单
		confirm() {
			this.$utils.checkForm(this.$refs.form, () => {
				this.confirmLoading = true;
				if (this.formParameter.type == 1 || this.formParameter.type == 2) {
					this.formParameter.content = JSON.stringify({ "title": this.formParameter.name, "choiceList": this.options });
				} else {
					this.formParameter.content = JSON.stringify({ "title": this.formParameter.name, "choiceList": {} });
				}
				if (!this.parameter.id) {
					//添加
					// if (this.formParameter.type == 1 || this.formParameter.type == 2) {
					// 	this.formParameter.content = JSON.stringify({ "title": this.formParameter.name, "choiceList": this.options });
					// } else {
					// 	this.formParameter.content = JSON.stringify({ "title": this.formParameter.name, "choiceList": {} });
					// }

					let parameter = this.$utils.merger(this.formParameter);
					console.log(parameter);
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
					console.log(parameter, 2);
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
			this.formParameter = { answer: '', type: 1, isSubjective: 2, reference: '自编题' };
			//清楚校验
			this.$refs.form.clearValidate();

			if (this.parameter.id) {
				//编辑查询数据
				let parameter = {
					id: this.parameter.id
				};
				api.toUpdate(parameter, response => {
					this.formParameter = response.data;
					this.formParameter.sbname = this.parameter.sbname;
					console.log(response.data.sbid);
					this.options = JSON.parse(response.data.content).choiceList;
					this.daAn = response.data.answer;
					this.checkList = this.daAn.split(',');
					this.popupLoading = false;

				});
			} else {
				//添加
				this.popupLoading = false;
			}
		},
		addSelectItem() {
			let len = this.options.length - 1;

			let last = this.options[len].o.charCodeAt() + 1;
			console.log(last);
			this.options.push({ o: String.fromCharCode(last), n: '' });
			console.log(this.options);
		},
		removeSelectItem() {
			let rc = this.options.pop();
			this.checkList.splice(this.checkList.indexOf(rc), 1);
		}
	}
};
</script>

<style lang="scss" scoped="scoped">
@import '~common/custom/css/common.scss';
@import '~common/custom/css/popup/popup.scss';

/deep/.el-drawer__header {
	margin-bottom: 0px;
}

.content-html {
	border: 1px solid #c9d8db !important;
	padding: 0px 20px;
}
</style>
