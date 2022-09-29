<template>
  <div>
    <el-row>
      <el-col :span="12" class=".el-col-frame-left">
        <el-card class="box-card userInfo">
          <div slot="header" class="userInfo-header clearfix">
            <span>班级信息</span>
          </div>
          <el-row class="userInfo-body">
            <el-col :span="12" class="userHead">
              <image-upload v-model="myclass.head" model="base64" :size="128"></image-upload>
              <div style="font-weight: bold"></div>
            </el-col>
            <el-col :span="12" class="info">
              <div>
                <label>班级名称:</label>
                <span> {{ myclass.dbColumn_cid }}</span>
              </div>
              <div>
                <label>带班老师:</label>
                <span> {{ myclass.dbColumn_tid }}</span>
              </div>
              <div>
                <label>该班班长:</label>
                <span> {{ myclass.dbColumn_sid }}</span>
              </div>

              <div>
                <label>手机号码:</label>
                <span> {{ myclass.mobile }}</span>
              </div>
              <div>
                <label>所属课程:</label>
                <span> {{ myclass.dbColumn_courseid }}</span>
              </div>

              <div class="operation">
                <el-select v-model="currentClassCid" placeholder="请选择">
                  <el-option v-for="item in classList" :key="item.cid" :label="item.dbColumn_cid" :value="item.cid">
                  </el-option>
                </el-select>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="12" class="el-col-frame-right">
        <el-card class="head_card">
          <!-- 
 <el-table
    :data="tableData"
    height="260"
    border
    style="width: 100%">
      <el-table-column
      label="头像"
      width="60">
      <template slot-scope="scope">
        <el-avatar :size="40" :src="scope.row.head"></el-avatar>
      </template>
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="80">
    </el-table-column>
    <el-table-column
      prop="dictionary_sex"
      label="性别"
      width="50">
    </el-table-column>
    <el-table-column
      prop="mobile"
      label="电话"
      width="110">
    </el-table-column>
    <el-table-column
      prop="email"
      label="邮箱"
      width="130">
    </el-table-column>
    <el-table-column
      prop="birth"
      label="生日"
      width="110">
    </el-table-column>
    <el-table-column
      prop="adress"
      label="住址"
      width="130">
    </el-table-column>
  </el-table> -->
          <el-row>
            <el-col :span="8" v-show="tableData" v-for="(i, index) in tableData" :key="i.id">
              <el-card class="box-card userInfo title">
                <el-avatar :size="120" fit="fill">
                  <img :src="i.head" style="width:120px;">
                </el-avatar>
                <div>{{ index + 1 }}.{{ i.name }}</div>
              </el-card>
            </el-col>
            <el-col v-show="!tableData.length" :span="24">
              <el-card class="box-card userInfo title">
                无学生
              </el-card>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :span="24">
        <el-card class="box-card userInfo">
          <div id="statistical-chart" style="height: 300px"></div>
          <el-button type="primary" @click="getSummarizeAll(1)">左滑动</el-button>
          <el-button type="primary" @click="getSummarizeAll(-1)">右滑动</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
  <!-- <div id="main">

		</div> -->
</template>
<script>
import "echarts/lib/chart/bar";
import api from "@/api/study/myclass2/myclass2.js";
import imageUpload from "@/components/biz/imageUpload/imageUpload.vue";
export default {
  name: "myclass",
  components: {
    imageUpload,
  },
  data() {
    return {
      myclass: {},            //当前班级
      currentClassCid: "",     //选中的班级cid
      dateFlag0: 0,
      classList: [],          //班级列表
      SummarizeAll: {},
      tableData: [],        //当前班级的全部学生
    };
  },
  watch: {
    // 选择班级时的cid 变化
    currentClassCid(newVal, oldVal) {
      this.classList.forEach(item => {
        if (newVal === item.cid) {
          this.myclass = item;
        }
      });
      this.getStudengtList()
      this.dateFlag=0;
      this.getSummarizeAll();
    }
  },
  methods: {
    //初始化班级数据
    getClassOne() {
      api.getClassOne({}, (response) => {
        this.myclass = response.data;
        this.currentClassCid = this.myclass.cid;
        // console.log(response);
        this.getSummarizeAll(0);
        this.getStudengtList();
      });

      api.getAllClass({}, (res) => {
        this.classList = res.data;
        console.log('班级列表', res);
      })
    },

    //获取学生列表
    getStudengtList() {
      let params = { cid: this.myclass.cid };
      api.getStudentList(params, (response) => {
        this.tableData = response.data;
        console.log(this.tableData, "tableData");
      });
    },

    //获得统计图数据all
    getSummarizeAll(i=0) {
      this.dateFlag0 = this.dateFlag0 + i;
      let params = { dateFlag: this.dateFlag0, cid: this.myclass.cid };
      api.getSummarizeAll(params, (response) => {
        this.SummarizeAll = response.data;
        console.log(this.SummarizeAll, "SummarizeAll");
        this.drawStatisticalChart();
      });
    },

    //每日反馈统计图画图
    drawStatisticalChart() {
      const statisticalChart = this.$echarts.init(
        document.getElementById("statistical-chart")
      );
      const option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {},
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: this.SummarizeAll.dateArray,
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            name: "每日反馈已提交",
            type: "bar",
            stack: "Ad",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.submitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },
          {
            name: "每日反馈未提交",
            type: "bar",
            stack: "Ad",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.notSubmitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },

          {
            name: "自我评价已提交",
            type: "bar",
            stack: "selfevaluation",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.selfevaluationSubmitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },
          {
            name: "自我评价未提交",
            type: "bar",
            stack: "selfevaluation",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.selfevaluationNotSubmitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },

          {
            name: "每日总结已提交",
            type: "bar",
            stack: "feedback",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.feedbackSubmitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },
          {
            name: "每日总结未提交",
            type: "bar",
            stack: "feedback",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.feedbackNotSubmitStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },

          {
            name: "每日总结及格",
            type: "bar",
            stack: "feedbackPass",
            emphasis: {
              focus: "series",
            },
            data: this.SummarizeAll.feedbackSubmitPassStudentArray,
            itemStyle: {
              normal: {
                label: {
                  show: true, //开启显示数值
                  textStyle: {
                    //数值样式
                    color: "#D1DBFF", //字体颜色
                    fontSize: 14, //字体大小
                  },
                },
              },
            },
          },
      
        ],
      };

      statisticalChart.setOption(option);
    },
  },

  mounted() {
    this.getClassOne();
  },
};
</script>

<style lang="scss" scoped="scoped">
.el-col-frame-left {
  background-color: rgb(212, 248, 248);
}

.head_card {
  height: 302px !important;
  overflow: auto;

  .title {
    text-align: center;
  }
}

@import "~common/custom/css/common.scss";
@import "~common/custom/css/main.scss";
</style>

