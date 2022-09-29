<template>
    <popup ref="popup" :loading="popupLoading">
        <div slot="body">
            <el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
                label-width="120px" :rules="rules" size="mini">
                <el-row>
                    <el-col :span="10">
                        <el-form-item label="试卷名称" prop="name">
                            <el-input v-model="formParameter.name" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="总分" prop="score">
                            <el-input v-model="formParameter.score" autocomplete="off" :disabled="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="及格分数" prop="passScore">
                            <el-input v-model="formParameter.passScore" autocomplete="off"></el-input>
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
                            <el-input v-model="formParameter.duration" autocomplete="off"></el-input>
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
                            <el-select v-model="formParameter.cid" filterable placeholder="请选择课程" disabled>
                                <el-option :label="formParameter.dbColumn_cid" :value="formParameter.cid">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="模块">
                            <el-select v-model="formParameter.mid" filterable placeholder="请选择模块" disabled>
                                <el-option :label="formParameter.dbColumn_mid" :value="formParameter.mid">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="7">
                        <el-form-item label="科目">
                            <el-select v-model="formParameter.kid" filterable placeholder="请选择科目" disabled>
                                <el-option :label="formParameter.dbColumn_kid" :value="formParameter.kid">
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
            </el-form>
            <!-- ----------------------------试卷题型情况--------------------------- -->
            <div>
                <el-divider content-position="left"><b>试卷题型情况</b></el-divider>
            </div>
            <template>
                <el-row style="padding:0 40px;">
                    <el-col :span="4" v-for="(item, i) in exampContent" :key="i">
                        <div v-if="item.types == 1">
                            <span>单选题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 2">
                            <span>多选题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 3">
                            <span>判断题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 4">
                            <span>填空题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 5">
                            <span>简答题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 6">
                            <span>论述题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                        <div v-if="item.types == 7">
                            <span>分析题</span>
                            <el-divider direction="vertical"></el-divider>
                            <span>{{ item.qnums }}</span>
                        </div>
                    </el-col>
                </el-row>
            </template>
            <!-- ----------------------------试卷--------------------------- -->
            <div>
                <el-divider content-position="left"><b>试卷</b></el-divider>
            </div>

            <template>
                <el-tabs v-model="activeName" type="card" style="padding: 0 40px;">
                    <template v-for="(item, i) in exampContent">
                        <el-tab-pane :key="i" v-if="item.types == 1" label="单选题" name="1">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>单选题 / {{ item.scores }} 分</span>

                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(1, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div v-for="(list, i) in JSON.parse(otem.content).choiceList" :key="i"
                                        style="padding:5px 80px 0">
                                        <span>{{ list.o + ". " + list.n }}</span>
                                    </div>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 2" label="多选题" name="2">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>多选题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(2, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div v-for="(list, i) in JSON.parse(otem.content).choiceList" :key="i"
                                        style="padding:5px 80px 0">
                                        <span>{{ list.o + ". " + list.n }}</span>
                                    </div>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 3" label="判断题" name="3">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>判断题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(3, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div v-for="(list, i) in JSON.parse(otem.content).choiceList" :key="i"
                                        style="padding:5px 80px 0">
                                        <span>{{ list.o + ". " + list.n }}</span>
                                    </div>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 4" label="填空题" name="4">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>填空题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(4, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 5" label="简答题" name="5">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>简答题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(5, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 6" label="论述题" name="6">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>论述题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(6, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                        <el-tab-pane :key="item.types" v-if="item.types == 7" label="分析题" name="7">
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span>分析题 / {{ item.scores }} 分</span>
                                </div>
                                <div v-for="(otem, j) in item.quContent" :key="j">
                                    {{ '题目：' + (j + 1) + '、' + JSON.parse(otem.content).title + " " +
                                            item.contents.qscore[0] + " 分"
                                    }}
                                    <el-button v-if="disabled == false" style="float: right; padding: 3px 0" type="text"
                                        @click="bianji(7, i, otem.id)">
                                        替换
                                    </el-button>
                                    <div style="margin-top:10px">{{ '答案：' + otem.answer }}</div>
                                    <el-divider></el-divider>
                                </div>
                            </el-card>
                        </el-tab-pane>
                    </template>
                </el-tabs>
            </template>
            <!-- ----------试卷的替换按钮------配置题目----------------- -->
            <template>
                <el-drawer title="配置题目" :visible.sync="drawer" :append-to-body="true" :before-close="handleClose"
                    custom-class="demo-drawer" size="80%" :wrapperClosable="false" :close-on-press-escape="false">
                    <div class="demo-drawer__content">
                        <!-- 顶部多条件查询 -->
                        <el-row>
                            <el-col :span="24">
                                <el-card shadow="never">
                                    <el-form ref="form" :model="searchParams" label-width="80px">
                                        <el-row>
                                            <el-col :span="4">
                                                <div class="cenIn">
                                                    <span v-if="typeId == 1">
                                                        <span>单选题</span>
                                                    </span>
                                                    <span v-if="typeId == 2">
                                                        <span>多选题</span>
                                                    </span>
                                                    <span v-if="typeId == 3">
                                                        <span>判断题</span>
                                                    </span>
                                                    <span v-if="typeId == 4">
                                                        <span>填空题</span>
                                                    </span>
                                                    <span v-if="typeId == 5">
                                                        <span>简答题</span>
                                                    </span>
                                                    <span v-if="typeId == 6">
                                                        <span>论述题</span>
                                                    </span>
                                                    <span v-if="typeId == 7">
                                                        <span>分析题</span>
                                                    </span>
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
                        <!-- 显示列表 -->
                        <div class="dataTable autoFlex" v-if="listData">
                            <el-table class="table" :data="listData.records" border v-loading="listLoading"
                                ref="multipleTable" highlight-current-row>
                                <el-table-column label="序号" type="index" width="50"></el-table-column>
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
                                <el-table-column prop="reference" width="110px" label="来源">
                                </el-table-column>

                                <el-table-column label="操作" width="120px">
                                    <template slot-scope="scope">
                                        <div class="dataTable-operator">
                                            <el-button size="mini" v-if="!contentsQid.includes(scope.row.id)"
                                                icon="el-icon-search" type="danger"
                                                @click="toDetail(scope.$index, scope.row)">替 换
                                            </el-button>
                                        </div>

                                    </template>
                                </el-table-column>

                            </el-table>
                            <div class="pageBar">
                                <el-pagination @size-change="sizeChange" @current-change="currentChange"
                                    :total="listData.total" :page-size="page.size" :current-page="page.current"
                                    :layout="this.$constant.page.layout" :page-sizes="this.$constant.page.pageSizes">
                                </el-pagination>
                            </div>
                        </div>
                    </div>
                </el-drawer>

                <!-- 选择题型 -->
                <el-dialog width="30%" top="3vh" title="选择题型" :visible.sync="innerVisible"
                    :before-close="handleCloseTree" :destroy-on-close="true" append-to-body>
                    <div class="contTree">
                        <tree v-loading="listLoading" ref="permissionTree" :items="permissionList" :defaultParent="0"
                            :show-checkbox="true" @check="checkData">
                        </tree>
                    </div>
                    <div slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="confirmT()">确 定</el-button>
                        <el-button @click="innerVisible = false">关 闭</el-button>
                    </div>
                </el-dialog>
            </template>

        </div>

        <div slot="footer">
            <el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
            <el-button @click="close">关闭</el-button>
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
    name: 'edtails',
    components: {
        popup, selectOptionDictionary, tree
    },
    data() {
        return {
            disabled: false,
            popupLoading: false,
            confirmLoading: false,
            changeindex: 0,
            parameter: {},
            formParameter: { type: 1, status: 1, },
            rules: {
                //试卷名称
                name: [
                    {
                        required: true, validator: this.$validate.required, trigger: 'blur'
                    }
                ],
                //考试时间（分钟）
                duration: [
                    {
                        required: true, validator: this.$validate.required, trigger: 'blur'
                    }
                ],
                //总分
                score: [
                    {
                        required: true, validator: this.$validate.required, trigger: 'blur'
                    }
                ],
                //及格分数
                passScore: [
                    {
                        required: true, validator: this.$validate.required, trigger: 'blur'
                    },
                    { type: 'number', message: '及格分数必须为数字值' }
                ],
            },

            exampContent: [],//试题的内容字段
            activeName: '1',//题型选项卡选中 对应name

            //---
            page: new this.$constant.pageObj(),
            typeId: undefined,
            quIndex: null,
            quid: null,
            drawer: false,
            searchParams: {},
            listData: [],
            contentsQid: [],
            listLoading: false,

            //---树--选择题型 模态框弹出为false
            innerVisible: false,
            // 树 的最后一个的id集
            subIds: [],
            //课程-模块-科目信息 全部数据
            permissionList: [],
            //选中的全部节点
            selectedIds: [],


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
        //提交表单-确认修改试卷
        confirm() {
            this.$utils.checkForm(this.$refs.form, () => {

                this.$confirm('此操作将保存试卷修改, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.confirmLoading = true;
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
                }).catch(() => {
                    this.$utils.msg.info("已取消");
                });




            })
        },
        init() {
            this.popupLoading = true;
            //清除等待
            this.confirmLoading = false;
            //清空内容
            this.formParameter = { type: 1, status: 1 };
            this.exampContent = [];
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

                    let exampContents = JSON.parse(this.formParameter.content);
                    for (let i = 0; i < exampContents.types.length; i++) {
                        this.activeName = exampContents.types[0] + '';

                        this.exampContent.push({ types: exampContents.types[i], qnums: exampContents.qnums[i], scores: exampContents.scores[i], contents: exampContents.contents[i] })

                        let quIds = [];
                        const element = exampContents.contents[i].qid;
                        quIds.push(element);
                        //根据试卷中的题目id查出试题
                        let parameter2 = {
                            quIds: quIds
                        };
                        api.listQuestion(parameter2, response => {
                            // console.log("response.data", response.data);
                            this.$set(this.exampContent[i], "quContent", response.data);
                        });
                    }
                    //排序
                    this.exampContent.sort((a, b) => {
                        return a.types - b.types;
                    });

                });
                // console.log("this.exampContent", this.exampContent);
            } else {
                //添加
                this.popupLoading = false;
            }
        },
        //弹出编辑
        bianji(types, index, id) {
            this.drawer = true;//打开抽屉
            this.typeId = types;//类型ID
            this.list(types);//查询数据
            this.quid = id;//题目ID
            this.quIndex = index;//题型所在数组的索引
            // console.log("this.contentsQid1", this.contentsQid);
            this.contentsQid = JSON.parse(this.formParameter.content).contents[this.quIndex].qid;
            // console.log("this.contentsQid2", this.contentsQid);
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
            this.subIds = [];
        },
        //替换---按钮
        toDetail(id, row) {
            // console.log("row", row);
            //JSON
            let temp_json = JSON.parse(this.formParameter.content);
            this.contentsQid = temp_json.contents[this.quIndex].qid;
            let index = this.contentsQid.indexOf(this.quid);//选中需要替换的题所在数组的索引
            this.contentsQid.splice(index, 1, row.id);//替换选中的题目id

            //根据试卷中的题目id查出试题
            let parameter = {
                quIds: this.contentsQid
            };
            api.listQuestion(parameter, response => {
                this.$set(this.exampContent[this.quIndex], "quContent", response.data);
            })

            //  JSON.stringify将js对象转为json文本
            this.formParameter.content = JSON.stringify(temp_json);
            //关闭
            this.drawer = false;
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
            let c = this.selectedIds.filter(item => item.children.length == 0);
            this.selectedIds = [];
            if (c.length > 0) {
                for (let i = 0; i < c.length; i++) {
                    const element = c[i];
                    this.subIds.push(element.id);
                }
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

// .demo-drawer__content .contentT {
//     height: calc(100vh - 50px - 100px);
//     overflow: auto;
// }

.demo-drawer__footer {
    position: fixed;
    bottom: 20px;
}

// 题目选择表格高度
.table {
    height: calc(100vh - 215px);
    overflow: auto;
}

.cenIn {
    margin: 10px;
}
</style>

