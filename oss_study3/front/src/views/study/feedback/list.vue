<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="4">
            <el-form-item label="姓名">
              <el-input v-model="searchParams.sname" @click="search"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="状态">
              <el-select v-model="state" @change="search" placeholder="请选择">
                <el-option v-for="(i) in ['未读', '已读', '全部']" :key="i" :label="i" :value="i">
                </el-option>
              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span="5">
            <el-form-item label="班级">
              <el-select v-model="searchParams.cname" placeholder="请选择班级">
                <el-option v-for="item in clazzList" :key="item.id" :label="item.name" :value="item.name"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-collapse-transition>
            <div v-if="isShowMoreSearch">
              <!--隐藏区域-->
            </div>
          </el-collapse-transition>
          <div class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button @click="clearSearchVal">清空</el-button>
            <el-button type="text" v-if="!isShowMoreSearch" @click="isShowMoreSearch = !isShowMoreSearch">
              更多
              <i class="el-icon-arrow-down"></i>
            </el-button>
            <el-button type="text" v-else @click="isShowMoreSearch = !isShowMoreSearch">
              收起
              <i class="el-icon-arrow-up"></i>
            </el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="operator">
      <el-button type="primary" icon="el-icon-plus" @click="toSave()">添加</el-button>
      <el-button type="primary" icon="el-icon-finished" @click="batchUpdateReadstAndToRate()">批量评分</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
        v-loading="listLoading">
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="sname" label="学生名字"></el-table-column>
        <el-table-column prop="cname" label="班级名字"></el-table-column>
        <el-table-column prop="tname" label="老师"></el-table-column>
        <el-table-column prop="course" label="课程"></el-table-column>
        <el-table-column prop="title" label="标题"></el-table-column>
        <el-table-column prop="content" label="内容">
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.content" placement="top">
              <el-button type="text" @click="content(scope.row.content)">详情</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="score" label="评分">
          <template slot-scope="scope">
            <el-rate v-model="scope.row.score*1" :colors="colors" :disabled="true">
            </el-rate>
          </template>
        </el-table-column>

        <!-- <div class="dataTable autoFlex" v-if="listData">
				<qlh-table-column prop="subtime" label="学生提交时间" sortable :editable="false"></qlh-table-column>
				</div> -->
        <el-table-column prop="subtime" label="学生提交时间">
        </el-table-column>
        <el-table-column prop="chetime" label="老师查看时间">
        </el-table-column>
        <el-table-column prop="readst" label="已读状态">
          <template slot-scope="scope">
            <el-switch v-if="scope.row.readst == 1" :disabled="true" :value="true"></el-switch>
            <el-switch v-if="scope.row.readst == 0" :disabled="false" @change="updateReadst(scope.$index, scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button size="mini" @click="toDetail(scope.$index, scope.row)">详情</el-button>
              <el-button size="mini" @click="toRate(scope.$index, scope.row)">评分</el-button>
            </div>
          </template>
        </el-table-column>
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
import edit2 from "./edit2.vue";
import api from "@/api/study/feedback/feedback.js";
import qlhTable from "@/components/qlhTable/qlhTable.vue";
import qlhTableColumn from "@/components/qlhTable/qlhTableColumn.vue";
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
    // getMyDate(str) {
    //   var oDate = new Date(str),
    //     oYear = oDate.getFullYear(),
    //     oMonth = oDate.getMonth() + 1,
    //     oDay = oDate.getDate(),
    //     oTime = oYear + "-" + this.getzf(oMonth) + "-" + this.getzf(oDay); //最后拼接时间
    //   return oTime;
    // },
    // //补0操作
    // getzf(num) {
    //   if (parseInt(num) < 10) {
    //     num = "0" + num;
    //   }
    //   return num;
    // },
    content: function (content) {
      this.$alert(content, "详情", {
        dangerouslyUseHTMLString: true,
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
    //获取班级
    listCname() {
      this.listLoading = true;

      api.listCname({}, (response) => {
        this.postsList = response.data;
        this.clazzList = response.data.clazzList;
        this.listLoading = false;
      });
    },
    //去添加
    toSave() {
      this.$refs.edit2.open({}, "添加", false);
    },
    //去评分
    toRate(index, row) {
      let editParameter = {
        id: row.id,
      };
      // console.log(editParameter); return
      this.$refs.edit2.open(editParameter, "评分");
    },
    updateReadst(index, row) {
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
    //批量评分
    batchUpdateReadstAndToRate() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      // let parameter = {
      //   ids: this.selected, //这是一个ID数组
      // };
      let IdsArr = []; //选中的所有学生的ID   {id:id}[]
      this.selected.forEach(IDitem => {
        IdsArr.push({ id: IDitem });
      });
      // console.log('feedback,selected，已选择的id,批量已读按钮', this.selected);
      this.$refs.edit2.open(IdsArr, "批量评分");
      return
      api.readst(
        {
          ids: this.selected,
        },
        (response) => {
          this.$utils.msg.success(response.msg);
          this.list();
        },
        (response) => {
          this.$utils.msg.warning(response.msg);
        }
      );
    },
    //去详情
    toDetail(index, row) {
      let detailParameter = {
        id: row.id,
      };
      this.$refs.edit2.open(detailParameter, "详情", true);
    },
    //删除
    remove(index, row) {
      let parameter = {
        ids: row.id,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          parameter,
          (response) => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
          }
        );
      });
    },
    //批量删除
    batchRemove() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      let parameter = {
        ids: this.selected,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          {
            ids: this.selected,
          },
          (response) => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
          }
        );
      });
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
    this.listCname();
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>

