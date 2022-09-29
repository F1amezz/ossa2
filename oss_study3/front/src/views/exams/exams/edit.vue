<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true" label-width="80px"
        :rules="rules">
        <el-row>
          <el-col :span="8">
            <el-form-item label="考试名称" prop="name">
              <el-input v-model="formParameter.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="试卷名称" prop="expid">
              <el-select v-model="formParameter.expid" :clearable="true" :filterable="true" placeholder="请选择试卷/可搜索">
                <el-option v-for="item in listExam" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生效时间" prop="startTime">
              <el-date-picker v-model="formParameter.startTime" type="datetime" placeholder="选择生效时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="截止时间" prop="endTime">
              <el-date-picker v-model="formParameter.endTime" type="datetime" placeholder="选择结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="考试班级" prop="cid">
              <el-select v-model="formParameter.cid" :clearable="true" :filterable="true" placeholder="请选择班级/可搜索"
                @change="chgClzz(formParameter.cid)">
                <el-option v-for="item in listClass" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="disabled ? 24 : 20">
            <el-form-item label="考试学生" prop="sid">
              <el-select v-model="formParameter.sid" :clearable="true" :filterable="true" multiple
                placeholder="请选择学生/可搜索" @change="changeSelect">
                <el-option v-for="item in listStu" :key="item" :label="item" :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-checkbox v-if="!disabled" v-model="checked" @change="selectAll">全选</el-checkbox>
          </el-col>
        </el-row>
        <el-row v-if="this.disabled">
          <el-col :span="9">
            <el-card class="box-card">
              <div id="numbEch" style="height:300px;"></div>
            </el-card>
          </el-col>
          <el-col :span="15">
            <el-card class="box-card">
              <div id="fsEch" style="height:300px;"></div>
            </el-card>
          </el-col>
          <el-col :span="24">
            <el-card class="box-card">
              <div id="fsEchOne" style="height:300px;"></div>
            </el-card>
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
import * as echarts from 'echarts';
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/exams/exams/exams.js";
import examApi from "@/api/exams/exampaper/exampaper.js";
import classApi from "@/api/edu/clazz/clazz.js";
import stuapi from "@/api/stus/student/student5.js";
import examsEchApi from "@/api/exams/exams/examsEch.js";

export default {
  name: "edit",
  components: {
    popup,
  },
  data() {
    return {
      checked: false,
      listClass: [],
      listExam: [],
      listStu: [],
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: { sid: [] },

      optionData: {},
      examsEchOneDataName: [],
      examsEchOneDataScore: [],

      rules: {
        //学生id
        sid: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //考试班级id
        cid: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //试卷id
        expid: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //生效时间
        startTime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 50,
            trigger: "blur",
          },
        ],
        //截止时间
        endTime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 50,
            trigger: "blur",
          },
        ],
        //考试名称
        name: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    selectAll() {
      this.formParameter.sid = [];
      if (this.checked) {
        this.listStu.map((item) => {
          this.formParameter.sid.push(item);
        });
      } else {
        this.formParameter.sid = [];
      }
    },
    changeSelect(val) {
      if (val.length === this.listStu.length) {
        this.checked = true;
      } else {
        this.checked = false;
      }
    },
    //打开弹框
    open(parameter, title, disabled) {
      this.listStu = [];
      this.parameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, "70%");
      this.$nextTick(() => {
        this.init();
        if (disabled) {
          this.getExamsEch(parameter.id);
        }
      });


    },
    examsNumbEch() {
      var chartDom = document.getElementById('numbEch');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: `已考 ${this.optionData.couex}人`,
          subtext: `应考人数 ${(this.optionData.sid).length}人`,
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          formatter: function (name) {
            let data = option.series[0].data;
            let tarValue;
            for (let i = 0; i < data.length; i++) {
              if (data[i].name == name) {
                tarValue = data[i].value
              }
            }
            let v = tarValue + '人';
            return `${name}  ${v}`;
            //name是名称，v是数值
          },
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              { value: this.optionData.couex - this.optionData.jige, name: '未及格人数' },
              { value: this.optionData.jige, name: '及格人数' },
            ],
            label: {
              normal: {
                formatter: '{b}',
                textStyle: {
                  fontWeight: '300',
                  fontSize: 15
                }
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          },
        ]
      };

      myChart.setOption(option);
    },
    examsFSEch() {
      var chartDom = document.getElementById('fsEch');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '考试分数情况图'
        },
        legend: {
          data: ['分数']
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '3%',
          right: '5%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['最高分', '平均分', '最低分']

        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '分数',
            data: [this.optionData.maxscore, this.optionData.avgscore, this.optionData.minscore],
            type: 'line',
            smooth: true,
            stack: 'Total',
          }
        ]
      };

      myChart.setOption(option);
    },
    examsFSEchOne() {
      var chartDom = document.getElementById('fsEchOne');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '考试个人分数图'
        },
        legend: {
          data: ['分数']
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '3%',
          right: '5%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.examsEchOneDataName,
          axisLabel: {
            //  让x轴文字方向为竖向
            interval: 0,
            formatter: function (value) {
              return value.split('').join('\n')
            }
          }
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '分数',
            data: this.examsEchOneDataScore,
            type: 'line',
            smooth: true,
            stack: 'Total',
          }
        ]
      };

      myChart.setOption(option);
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
          api.save(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        } else {
          //编辑
          let parameter = this.$utils.merger(this.formParameter);
          api.update(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        }
      });
    },
    chgClzz(cid) {
      console.log(this.formParameter.sid);
      let parameter = {
        cid: cid,
      };
      stuapi.stuInClass(parameter, (response) => {
        this.listStu = response.data;
        delete this.formParameter.sid;
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

      examApi.list3({}, (response) => {
        this.listExam = response.data.records;
      });
      classApi.list({}, (response) => {
        console.log(response.data);
        this.listClass = response.data.records;
      });

      if (this.parameter.id) {
        //编辑查询数据
        let parameter = {
          id: this.parameter.id,
        };
        api.toUpdate(parameter, (response) => {
          this.formParameter = response.data;
          let parameter = {
            cid: this.formParameter.cid,
          };
          stuapi.stuInClass(parameter, (response) => {
            this.listStu = response.data;
          });
          this.formParameter.sid = JSON.parse(response.data.sid);
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    },

    getExamsEch(expid) {
      let id = expid + "";
      this.examsEchOneDataName = [];
      this.examsEchOneDataScore = [];
      examsEchApi.getExamsEch({ id: id }, (response) => {
        response.data.sid = JSON.parse(response.data.sid);
        this.optionData = response.data;
        this.examsNumbEch();
        this.examsFSEch();
      });

      examsEchApi.getExamsEchOne({ id: id }, (response) => {

        for (let i = 0; i < response.data.length; i++) {
          this.examsEchOneDataName.push(response.data[i].name);
          this.examsEchOneDataScore.push(response.data[i].score);
        }
        this.examsFSEchOne();
      });
    },


  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";

/deep/.el-drawer__header {
  margin-bottom: 0px;
}

/deep/.el-card__body {
  padding: 0px;
}
</style>
