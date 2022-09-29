<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="120px" :rules="rules" size="mini">
				<!-- <el-divider content-position="left">试卷信息</el-divider> -->
				<el-row>
					<el-col :span="10">
						<el-form-item label="试卷名称" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off">
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="总分" prop="score">
							<el-input v-model="formParameter.score" autocomplete="off" :disabled="true">
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="及格分数" prop="passScore">
							<el-input v-model.number="formParameter.passScore" autocomplete="off">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="10">
						<el-form-item label="类型" prop="type">
							<select-option-dictionary v-model="formParameter.type" :clearable="false" dkey="exp-type">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="考试时间(分钟)" prop="duration">
							<el-input v-model.number="formParameter.duration" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="状态" prop="status">
							<select-option-dictionary v-model="formParameter.status" :clearable="false"
								dkey="y-n-status">
							</select-option-dictionary>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="10">
						<el-form-item label="课程">
							<el-select v-model="formParameter.cid" filterable placeholder="请选择课程"
								@change="selectChange">
								<el-option v-for="(item, index) in optionsCourse" :key="index" :label="item.name"
									:value="item.id">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="模块">
							<el-select v-model="formParameter.mid" filterable placeholder="请选择模块"
								:disabled="optionsMidInput" @change="selectChange1(formParameter.mid)">
								<el-option v-for="item in optionsMid" :key="item.id" :label="item.name"
									:value="item.id">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="7">
						<el-form-item label="科目">
							<el-select v-model="formParameter.kid" filterable placeholder="请选择科目"
								:disabled="optionsSidInput">
								<el-option v-for="item in optionsSid" :key="item.id" :label="item.name"
									:value="item.id">
								</el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="备注" prop="mark">
							<el-input type="textarea" rows="1" autosize v-model="formParameter.mark" autocomplete="off">
							</el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<!-- ----------------选择题型分布---------------- -->
				<template>
					<el-divider content-position="left">选择题型分布</el-divider>
					<el-row>
						<el-col :span="17">
							<el-row style="margin-bottom:10px">
								<el-col :span="16" style="text-align: center;">
									<p style="color:red;font-size:12px;padding-left: 40px;">填入每种题型和每道题的分数后，可以为题型配题。</p>
								</el-col>
								<!-- <el-col :span="8">
									<el-button type="info" size="mini" @click="innerVisibleBut">
										选择题型</el-button>
								</el-col> -->
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="单选题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[0]" ref="inpT1" :disabled="this.qudingBut[0].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[0]" ref="oneChoice1"
												:disabled="this.qudingBut[0].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[0]" ref="inpS1" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[0] > 0 && inputS[0] > 0">
											<el-button type="success" size="mini" @click="edutT(1)"
												v-if="qudingBut[0].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(1)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="多选题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[1]" ref="inpT2" :disabled="this.qudingBut[1].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[1]" ref="oneChoice2"
												:disabled="this.qudingBut[1].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[1]" ref="inpS2" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[1] > 0 && inputS[1] > 0">
											<el-button type="success" size="mini" @click="edutT(2)"
												v-if="qudingBut[1].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(2)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="判断题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[2]" ref="inpT3" :disabled="this.qudingBut[2].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[2]" ref="oneChoice3"
												:disabled="this.qudingBut[2].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[2]" ref="inpS3" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[2] > 0 && inputS[2] > 0">
											<el-button type="success" size="mini" @click="edutT(3)"
												v-if="qudingBut[2].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(3)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="填空题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[3]" ref="inpT4" :disabled="this.qudingBut[3].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[3]" ref="oneChoice4"
												:disabled="this.qudingBut[3].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[3]" ref="inpS4" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[3] > 0 && inputS[3] > 0">
											<el-button type="success" size="mini" @click="edutT(4)"
												v-if="qudingBut[3].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(4)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="简答题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[4]" ref="inpT5" :disabled="this.qudingBut[4].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[4]" ref="oneChoice5"
												:disabled="this.qudingBut[4].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[4]" ref="inpS5" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[4] > 0 && inputS[4] > 0">
											<el-button type="success" size="mini" @click="edutT(5)"
												v-if="qudingBut[4].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(5)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="论述题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[5]" ref="inpT6" :disabled="this.qudingBut[5].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[5]" ref="oneChoice6"
												:disabled="this.qudingBut[5].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[5]" ref="inpS6" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[5] > 0 && inputS[5] > 0">
											<el-button type="success" size="mini" @click="edutT(6)"
												v-if="qudingBut[5].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(6)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row>
								<el-col :span="24">
									<el-form-item label="分析题" :inline="true">
										<el-col :span="5">
											<el-input v-model="inputT[6]" ref="inpT7" :disabled="this.qudingBut[6].but">
											</el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 道，每道 </el-col>
										<el-col :span="4">
											<el-input v-model="oneChoice[6]" ref="oneChoice7"
												:disabled="this.qudingBut[6].but"></el-input>
										</el-col>
										<el-col :span="3" style="text-align:center"> 分，共 </el-col>
										<el-col :span="4">
											<el-input v-model="inputS[6]" ref="inpS7" :disabled="true"></el-input>
										</el-col>
										<el-col :span="2" style="padding-left: 5px;">分。</el-col>
										<el-col :span="2" v-if="inputT[6] > 0 && inputS[6] > 0">
											<el-button type="success" size="mini" @click="edutT(7)"
												v-if="qudingBut[6].but == true">配置题目</el-button>
											<el-button type="danger" size="mini" @click="edutT(7)" v-else>配置题目
											</el-button>
										</el-col>
									</el-form-item>
								</el-col>
							</el-row>
						</el-col>
					</el-row>
					<!-- 选择题型 -->
					<el-dialog width="30%" top="3vh" title="选择题型" :visible.sync="innerVisible"
						:before-close="handleCloseTree" :destroy-on-close="true" append-to-body>
						<div class="contTree">
							<tree v-loading="listLoading" ref="permissionTree" :items="permissionList"
								:defaultParent="0" :show-checkbox="true" @check="checkData">
							</tree>
						</div>
						<div slot="footer" class="dialog-footer">
							<el-button type="primary" @click="confirmT()">确 定</el-button>
							<el-button @click="innerVisible = false">关 闭</el-button>
						</div>
					</el-dialog>

					<!-- ----------------配置题目----------------- -->
					<el-drawer title="配置题目" :visible.sync="drawer" :append-to-body="true" :before-close="handleClose"
						custom-class="demo-drawer" size="80%" :wrapperClosable="false" :close-on-press-escape="false">

						<div class="demo-drawer__content">
							<el-row>
								<el-col :span="24">
									<el-card shadow="never">
										<el-form ref="form" :model="searchParams" label-width="80px">
											<el-row>
												<el-col :span="4">
													<div class="cenIn">
														<span v-if="typeId == 1">
															<span>单选题：</span>
														</span>
														<span v-if="typeId == 2">
															<span>多选题：</span>
														</span>
														<span v-if="typeId == 3">
															<span>判断题：</span>
														</span>
														<span v-if="typeId == 4">
															<span>填空题：</span>
														</span>
														<span v-if="typeId == 5">
															<span>简答题：</span>
														</span>
														<span v-if="typeId == 6">
															<span>论述题：</span>
														</span>
														<span v-if="typeId == 7">
															<span>分析题：</span>
														</span>

														<span> {{ inputT[typeId - 1] }} </span>
														<b> / </b>
														<span> {{ multipleSelection.length }} </span>
														<span> 共{{ inputS[typeId - 1] }}分 </span>
													</div>
												</el-col>
												<el-col :span="2" style="margin-top: 5px;">
													<el-button type="info" size="mini" @click="innerVisibleBut">
														选择题型
													</el-button>
												</el-col>
												<el-col :span="4">
													<el-form-item label="试题名称" style="margin-bottom:0;">
														<el-input v-model="searchParams.name"></el-input>
													</el-form-item>
												</el-col>
												<el-col :span="4">
													<el-form-item label="试题来源" style="margin-bottom:0;">
														<select-option-dictionary v-model="searchParams.reference"
															@change="search()" dkey="exam-reference">
														</select-option-dictionary>
													</el-form-item>
												</el-col>
												<div class="search-btn">
													<el-button type="primary" icon="el-icon-search" @click="search"
														style="margin-left:20px">搜索
													</el-button>
													<el-button @click="clearSearchVal">清空</el-button>
												</div>
											</el-row>
										</el-form>

									</el-card>
								</el-col>
							</el-row>
							<div class="dataTable autoFlex" v-if="listData">
								<el-table class="table" :data="listData.records" stripe border v-loading="listLoading"
									:row-key="rowKey" ref="multipleTable" @selection-change="handleSelectionChange">
									<el-table-column type="selection" width="55" :reserve-selection="true">
									</el-table-column>
									<el-table-column prop="qname" label="试题名称" show-overflow-tooltip>
									</el-table-column>
									<el-table-column prop="sbname" width="220px" label="科目"></el-table-column>
									<el-table-column prop="qtype" width="110px" label="试题类型">
										<template slot-scope="scope">
											<!-- 试题 1-单选题、2多选题、3-判断题、4-填空题、5-、简答题、6-论述题、7-分析题 -->
											<p v-if="scope.row.qtype == 1">单选题</p>
											<p v-if="scope.row.qtype == 2">多选题</p>
											<p v-if="scope.row.qtype == 3">判断题</p>
											<p v-if="scope.row.qtype == 4">填空题</p>
											<p v-if="scope.row.qtype == 5">简答题</p>
											<p v-if="scope.row.qtype == 6">论述题</p>
											<p v-if="scope.row.qtype == 7">分析题</p>
										</template>
									</el-table-column>
									<el-table-column prop="exposeTimes" width="110px" label="出题次数">
										<template slot-scope="scope">
											<p>{{ scope.row.rightTimes }} / {{ scope.row.exposeTimes }} </p>
										</template>
									</el-table-column>
									<el-table-column prop="reference" width="110px" label="来源"></el-table-column>
								</el-table>
								<div class="pageBar">
									<el-pagination @size-change="sizeChange" @current-change="currentChange"
										:total="listData.total" :page-size="page.size" :current-page="page.current"
										:layout="this.$constant.page.layout"
										:page-sizes="this.$constant.page.pageSizes"></el-pagination>
								</div>
							</div>
							<div class="demo-drawer__footer">
								<el-button type="primary" @click="confirmE()">确认</el-button>
								<!-- <el-button @click="cancelForm">取 消</el-button> -->
							</div>
						</div>
					</el-drawer>

				</template>
			</el-form>
		</div>
		<div slot="footer">
			<el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定
			</el-button>
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
import popup from '@/components/popup/drawerPopup.vue';
import api from '@/api/exams/exampaper/exampaper.js';
import apiS from "@/api/edu/subject/subject.js";
import tree from "@/components/tree/tree.vue";
import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
export default {
	name: 'edit',
	components: {
		popup, selectOptionDictionary, tree
	},
	data() {
		return {
			searchParams: {},
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			parameter: {},
			formParameter: { type: 1, status: 1, cid: undefined, mid: undefined },
			page: new this.$constant.pageObj(),
			rules: {
				//试卷名称
				name: [
					{ required: true, message: '请输入试卷名称', trigger: 'blur' },
				],
				// 及格分数
				passScore: [
					{ required: true, message: '请输入及格分数', trigger: 'blur' },
					{ type: 'number', message: '及格分数必须为数字值' }
				],
				//考试时间（分钟）
				duration: [
					{ required: true, message: '请输入考试时间(分钟)', trigger: 'blur' },
					{ type: 'number', message: '及格分数必须为数字值' }
				],
			},

			listLoading: false,
			qudingBut: [{ but: false }, { but: false }, { but: false }, { but: false }, { but: false }, { but: false }, { but: false }],

			sum: 0,//总分
			inputT: [],
			inputS: [],
			oneChoice: [],

			//---树--选择题型 模态框弹出为false
			innerVisible: false,
			// 树 的最后一个的id集
			subIds: [],
			//课程-模块-科目信息 全部数据
			permissionList: [],
			//选中的全部节点
			selectedIds: [],

			//---抽屉--抽屉弹出
			drawer: false,
			typeId: undefined,
			listData: [],
			//题目复选款
			multipleSelection: [],

			content: {},
			con: [],

			optionsCourse: {},//课程
			optionsMid: {},//模块
			optionsSid: {},//科目

			optionsMidInput: true,
			optionsSidInput: true,

		};
	},
	watch: {
		//监听选择题型时的类型
		inputT: {
			handler: function (val, oldval) {
				if (this.inputT.length > 0) {
					let sum = 0;
					let types = [];
					let qnums = [];
					for (let i = 0; i < this.inputT.length; i++) {
						const element = this.inputT[i];
						if (element == '' || element == null || element == undefined) {
							element = 0;
						}
						if (!isNaN(element)) {
							if (element > 0) {
								types.push(parseInt(i + 1));
								qnums.push(this.inputT[i]);

								if (this.oneChoice[i] > 0) {
									let c = this.inputT[i] * 1 * this.oneChoice[i];
									this.inputS[i] = c;
								} else {
									this.inputS[i] = 0;
								}

							} else {
								this.inputS[i] = 0;
							}

						} else {
							element = 0;
							this.$utils.msg.warning("请输入数字。", 2000);
							this.inputT[i] = undefined;
						}
						sum += parseInt(this.inputS[i]);
					}
					this.content.types = types;
					this.content.qnums = qnums;
					this.formParameter.score = sum;
				} else {
					this.inputS = [];
				}
			},
			deep: true
		},
		//监听题型的每道题分数
		oneChoice: {
			handler: function (val, oldval) {
				if (this.oneChoice.length > 0) {
					let sum = 0;
					let oneC = [];//每道单选框分数
					let scores = [];//每种题总分
					for (let i = 0; i < this.oneChoice.length; i++) {
						const element = this.oneChoice[i];
						if (element == '' || element == null || element == undefined) {
							element = 0;
						}
						if (!isNaN(element)) {
							//判断如果有分数就把该索引+1加入数组中 scores
							if (element > 0) {
								oneC.push(element);
								if (this.inputT[i] > 0) {
									let c = this.inputT[i] * 1 * this.oneChoice[i];
									scores.push(c);
									this.inputS[i] = c;
								} else {
									this.inputS[i] = 0;
								}
							} else {
								this.inputS[i] = 0;
							}

						} else {
							element = 0;
							this.$utils.msg.warning("请输入数字。", 2000);
							this.oneChoice[i] = undefined;
						}
						sum += parseInt(this.inputS[i]);
					}
					this.content.scores = scores;
					//总分
					this.formParameter.score = sum;
				} else {
					this.inputS = [];
				}
			},
			deep: true
		},

		//题目数量选择监听
		multipleSelection: {
			handler(val, olVal) {
				if (this.multipleSelection.length > this.inputT[this.typeId - 1]) {
					this.$refs.multipleTable.toggleRowSelection(this.multipleSelection[this.multipleSelection.length - 1]);
					// console.log("this.multipleSelection",this.multipleSelection);
					this.$utils.msg.warning("选择已超过上限。");
				}

			},
			deep: true
		},

	},
	methods: {
		//打开弹框
		open(parameter, title, disabled, size) {
			this.parameter = parameter;
			this.disabled = disabled;
			this.$refs.popup.open(title, size);
			this.inputT = [];
			this.inputS = [];
			this.oneChoice = [];
			this.optionsCourse = {};//课程
			this.optionsMid = {};//模块
			this.optionsSid = {};//科目
			this.$set(this.formParameter, "cid", undefined);
			this.$set(this.formParameter, "mid", undefined);
			this.$set(this.formParameter, "kid", undefined);
			this.optionsMidInput = true;
			this.optionsSidInput = true;
			this.$nextTick(() => {
				this.init();
				this.getlistCourse();
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
				if (!this.formParameter.name) return;
				if (!this.formParameter.passScore) return;
				if (!this.formParameter.duration) return;
				if (!this.formParameter.kid) { this.$utils.msg.warning("请选择科目。"); return; }

				//单选题 分数 逻辑判断
				if (this.$refs.inpT1.value || this.$refs.inpT2.value || this.$refs.inpT3.value ||
					this.$refs.inpT4.value || this.$refs.inpT5.value || this.$refs.inpT6.value || this.$refs.inpT7.value) {
					//单选题 分数 逻辑判断
					if (this.$refs.inpT1.value) {
						if (!this.$refs.oneChoice1.value) {
							this.$utils.msg.warning("请单选题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice1.value) {
						if (!this.$refs.inpT1.value) {
							this.$utils.msg.warning("单选题填写有误。", 2000);
							return;
						}
					}
					//多选题 分数 逻辑判断
					if (this.$refs.inpT2.value) {
						if (!this.$refs.oneChoice2.value) {
							this.$utils.msg.warning("多选题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice2.value) {
						if (!this.$refs.inpT2.value) {
							this.$utils.msg.warning("多选题填写有误。", 2000);
							return;
						}
					}
					//判断题 分数 逻辑判断
					if (this.$refs.inpT3.value) {
						if (!this.$refs.oneChoice3.value) {
							this.$utils.msg.warning("判断题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice3.value) {
						if (!this.$refs.inpT3.value) {
							this.$utils.msg.warning("判断题填写有误。", 2000);
							return;
						}
					}
					//填空题 分数 逻辑判断
					if (this.$refs.inpT4.value) {
						if (!this.$refs.oneChoice4.value) {
							this.$utils.msg.warning("填空题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice4.value) {
						if (!this.$refs.inpT4.value) {
							this.$utils.msg.warning("填空题填写有误。", 2000);
							return;
						}
					}
					//简答题 分数 逻辑判断
					if (this.$refs.inpT5.value) {
						if (!this.$refs.oneChoice5.value) {
							this.$utils.msg.warning("简答题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice5.value) {
						if (!this.$refs.inpT5.value) {
							this.$utils.msg.warning("简答题填写有误。", 2000);
							return;
						}
					}
					//论述题 分数 逻辑判断
					if (this.$refs.inpT6.value) {
						if (!this.$refs.oneChoice6.value) {
							this.$utils.msg.warning("论述题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice6.value) {
						if (!this.$refs.inpT6.value) {
							this.$utils.msg.warning("论述题填写有误。", 2000);
							return;
						}
					}
					//分析题 分数 逻辑判断
					if (this.$refs.inpT7.value) {
						if (!this.$refs.oneChoice7.value) {
							this.$utils.msg.warning("分析题填写有误。", 2000);
							return;
						}
					} else if (this.$refs.oneChoice7.value) {
						if (!this.$refs.inpT7.value) {
							this.$utils.msg.warning("分析题填写有误。", 2000);
							return;
						}
					}
				} else {
					this.$utils.msg.warning("请选择至少一种题。");
					return;
				}
				//排序
				this.con.sort((a, b) => {
					console.log(a, b);
					return a.typeId - b.typeId;
				});
				//配置题目按钮完成变颜色 判断有没有全部配置好
				for (let i = 0; i < this.inputT.length; i++) {
					if (this.inputT[i] > 0) {
						for (let j = 0; j < this.qudingBut.length; j++) {
							const e = this.qudingBut[i];
							if (e.but == false) {
								this.$utils.msg.warning("请把配置题目选择完整，完成将变成绿色。");
								return;
							}
						}
					}

				}

				this.confirmLoading = true;

				let contents = [];
				for (let i = 0; i < this.con.length; i++) {
					const element = this.con[i];
					contents.push({ "qid": element.pid, "qscore": element.qscore });
				}
				this.content.contents = contents;
				if (!this.parameter.id) {
					//添加
					this.formParameter.content = JSON.stringify(this.content);
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
			this.formParameter = { type: 1, status: 1 };
			this.qudingBut = [{ but: false }, { but: false }, { but: false }, { but: false }, { but: false }, { but: false }, { but: false }],
				this.inputS = [],
				this.inputT = [],
				this.questionCart = [];
			this.oneChoice = [],
				this.con = [];
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
		},
		//改变每页显示数量
		sizeChange(val) {
			this.page.size = val;
			this.list(this.typeId);
		},
		//改变现在的页码
		currentChange(val) {
			this.page.current = val;
			this.list(this.typeId);
		},
		//搜索
		search() {
			this.list(this.typeId);
		},
		//清空搜索框
		clearSearchVal() {
			this.searchParams = {};
		},
		// ---------------选择题型按钮部分-------
		//为题目选择课程-模块-科目信息
		innerVisibleBut() {
			this.innerVisible = true;
			this.listLoading = true;
			apiS.list({}, (response) => {
				this.permissionList = response.data;
				this.listLoading = false;
			});
		},
		handleCloseTree(done) {
			this.$confirm('确定关闭吗？')
				.then(_ => {
					done();
				})
				.catch(_ => { });
		},
		//点击复选框
		checkData(data, checkedObj) {
			this.selectedIds = checkedObj;
		},
		//配置题目里的确认按钮
		confirmT() {
			this.innerVisible = false;
			this.listLoading = false;
			this.subIds = [];
			const c = this.selectedIds.filter(item => item.children.length == 0);
			this.selectedIds = [];
			if (c.length > 0) {
				for (let i = 0; i < c.length; i++) {
					const element = c[i];
					this.subIds.push(element.id);
				}
			}

		},
		// ------------------配置题目按钮部分-------
		// 配置题目按钮-打开抽屉
		edutT(id) {
			this.listData = [];
			this.drawer = true;
			this.listLoading = true;
			this.typeId = id;
			this.multipleSelection = [];
			this.list(id);

			if (this.con.length > 0) {
				for (let i = 0; i < this.con.length; i++) {
					const element = this.con[i];
					if (id == element.typeId) {
						(element.mul).forEach(row => {
							this.$refs.multipleTable.toggleRowSelection(row, true);
						});
					}
				}
			}

		},
		list(typeId) {
			let params1 = {
				typeId: typeId,
				subIds: this.subIds,
				name: this.searchParams.name,
				reference: this.searchParams.reference,
			};
			let params = this.$utils.merger(params1, this.page);
			api.listQuesExamp(params, response => {
				this.listData = response.data;
				this.listLoading = false;
			})
		},
		//配置题目弹出抽屉关闭回调
		handleClose(done) {
			this.$refs.multipleTable.clearSelection();
			done();
		},
		confirmE() {
			if (this.inputT[this.typeId - 1] != this.multipleSelection.length) {
				this.$utils.msg.warning("还缺少题目，请选择。");
				return;
			}

			let qscore = [];
			let qsc = Math.floor(parseInt(this.inputS[this.typeId - 1]) / parseInt(this.inputT[this.typeId - 1]));
			for (let i = 0; i < parseInt(this.inputT[this.typeId - 1]); i++) {
				qscore.push(qsc);
			}
			if (this.multipleSelection.length > 0) {
				let pid = [];
				for (let i = 0; i < this.multipleSelection.length; i++) {
					const element = this.multipleSelection[i];
					pid.push(element.id);
				}
				if (this.con.length > 0) {
					for (let i = 0; i < this.con.length; i++) {
						const element = this.con[i];
						if (this.typeId == element.typeId) {
							this.con[i] = this.con.push({ "typeId": this.typeId, "pid": pid, "mul": this.multipleSelection, "qscore": qscore });
							this.drawer = false;
							this.$refs.multipleTable.clearSelection();
							return;
						}
					}
				}

				this.con.push({ "typeId": this.typeId, "pid": pid, "mul": this.multipleSelection, "qscore": qscore });
				this.qudingBut[this.typeId - 1] = { but: true };
			};
			this.drawer = false;
			this.$refs.multipleTable.clearSelection();

		},
		rowKey(row) {
			return row.id;
		},
		handleSelectionChange(val) {
			this.multipleSelection = val;
		},

		// 查询课程
		getlistCourse() {
			api.listCourse({}, res => {
				// console.log(res.data);
				this.optionsCourse = res.data;
			});
		},
		// 课程下拉框选中的索引
		selectChange(value) {
			let courseNum = this.optionsCourse.findIndex((item, index) => {
				return item.id == value
			});
			this.optionsMid = {};
			this.optionsSid = {};
			if (this.formParameter.cid) {
				this.optionsMidInput = false;
				this.optionsSidInput = true;
				this.$set(this.formParameter, "mid", undefined);
				this.$set(this.formParameter, "kid", undefined);
				let mid = this.optionsCourse[courseNum].sid;
				api.listMid({ mid: mid }, res => {
					this.optionsMid = res.data;
				});
			}
		},
		selectChange1(mid) {
			this.$set(this.formParameter, "kid", undefined);
			if (mid) {
				api.listKid({ mid: mid }, res => {
					this.optionsSid = res.data;
					this.optionsSidInput = false;
				});
			}
		},

	}
};
</script>

<style lang="scss" scoped="scoped">
@import '~common/custom/css/common.scss';
@import '~common/custom/css/popup/popup.scss';

/deep/.el-card__body {
	padding: 5px;
}

/deep/.boxTextItem {
	height: 270px;
	overflow: auto;
}

/deep/.el-drawer__header {
	margin-bottom: 0px;
}

// -------------选择题型---------------
.contTree {
	height: calc(100vh - 260px);
	overflow: auto;
}

// --------------配置题目抽屉-------

.demo-drawer__content {
	padding: 0 20px;

}

.demo-drawer__footer {
	position: fixed;
	bottom: 20px;
}

// 题目选择表格高度
.table {
	height: calc(100vh - 250px);
	overflow: auto;
}

.cenIn {
	margin: 10px;
}
</style>

