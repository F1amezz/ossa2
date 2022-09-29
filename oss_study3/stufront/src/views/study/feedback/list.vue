<template>
  <div class="tab-body">
    <div class="operator">
      <el-button type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table class="table" :data="listData.records" stripe height="auto" @selection-change="selectionChange"
        v-loading="listLoading">
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="subtime" label="提交时间">
          <template slot-scope="scope">
            {{ getMyDate(scope.row.subtime) }}
          </template>
        </el-table-column>
        <el-table-column prop="course" label="课程+章节"></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        
        <el-table-column prop="content" label="内容">
          <template slot-scope="scope">
            <!-- <el-tooltip :content="scope.row.content" placement="top"> -->
            <el-button type="text" @click="content(scope.row)">详情</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="score" label="评分">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.score * 1" :colors="colors" :disabled="true">
            </el-rate>
          </template>
        </el-table-column>

        <el-table-column prop="tname" label="审阅人"></el-table-column>
        <!-- <el-table-column prop="score" label="评分">
          
        </el-table-column> -->



        <!-- <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">

              <el-button size="mini" @click="toUpdate(scope.$index, scope.row)">查看</el-button>
            </div>
          </template>
        </el-table-column> -->
      </el-table>
      <div class="pageBar">
        <el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
          :page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"></el-pagination>
      </div>
    </div>
    <edit ref="edit"></edit>
    <edit2 ref="edit2"></edit2>
  </div>
</template>
<script>
import edit from "./edit.vue";
import edit2 from "./add.vue";
import api from "@/api/study/feedback/feedback.js";
import apiExams from "@/api/exams/exams/exams.js";

import qlhTable from "@/components/qlhTable/qlhTable.vue";
import qlhTableColumn from "@/components/qlhTable/qlhTableColumn.vue";
// import edit from './add.vue';
export default {
  name: "feedback",
  components: {
    edit,
    edit2,
    qlhTable,
    qlhTableColumn,
  },
  data() {
    return {
      state: '未读',
      colors: { 3: "#99A9BF", 5: "#FF9900" },
      checked: true,
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
      postsList: [],
      clazzList: {},
    };
  },
  watch: {
    "searchParams.sname": function (newVal, oldVal) {
      this.searchParams.sname = newVal;
      this.search();
    },
  },
  methods: {

    // 时间节点
    getMyDate(str) {
      var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oTime = oYear + "-" + this.getzf(oMonth) + "-" + this.getzf(oDay); //最后拼接时间
      return oTime;


    },
    // //补0操作
    getzf(num) {
      if (parseInt(num) < 10) {
        num = "0" + num;
      }
      return num;
    },
    content: function (row) {

       apiExams.isCanReadFdbck({sid:row.sid},res=>{
          console.log(res)
           if(res.data){
                this.$alert(row.content, "详情", {
                dangerouslyUseHTMLString: true,
              });
           }else{
               this.$alert("考试中禁止查看笔记", "警告"  );
           }
       });

     
    },

    search() {
      this.list();
    },
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        this.listLoading = false;
      });
    },

    saveReadst(index, row) {
      let parameter = {
        id: row.id,
      };
      api.readst2(
        parameter,
        (response) => {
          this.$utils.msg.success(response.msg);
          this.list();
        },
        (response) => {
          this.$utils.msg.warning(response.msg);
        }
      );
    },
    //去添加
    toSave() {
      this.$refs.edit2.open({}, "添加");
    },
    // //去添加
    // toSave(index, row) {
    //   let editParameter = {
    //     id:  123,
    //     // cid: row.cid,
    //   };
    //   console.log(editParameter.id);
    //   this.$refs.edit.open(editParameter, "添加");
    //   console.log(213123123);
    // },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.edit.open(editParameter, "编辑");
    },

    //改变选择项
    selectionChange(val) {
      //清空
      this.selected = [];
      for (let item of val) {
        this.selected.push(item.id);
      }
    },
    //改变每页显示数量
    sizeChange(val) {
      this.page.size = val;
      this.list();
    },
    //改变现在的页码
    currentChange(val) {
      this.page.current = val;
      this.list();
    },

    //搜索
    search() {
      if (this.state == '未读') {
        this.searchParams.readst = 0;
      }
      if (this.state == '已读') {
        this.searchParams.readst = 1;
      }
      if (this.state == '全部') {
        this.searchParams.readst = undefined;
      }
      this.list();
    },
    //清空搜索框
    clearSearchVal() {
      this.searchParams = {};
      this.list();
    },
  },
  mounted() {
    this.list();
    // this.listCname();
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>

