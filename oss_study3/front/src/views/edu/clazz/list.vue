<template>
  <div class="tab-body">
    <div class="search" v-has-permission-code-and="'003000150030'">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="班级名称">
              <el-input
                v-model="searchParams.name"
                placeholder="请输入班级名称"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="类型" prop="state">
              <select-option-dictionary
                v-model="searchParams.types"
                dkey="clazz-types"
              >
              </select-option-dictionary>
            </el-form-item>
          </el-col>

          <el-collapse-transition>
            <div v-if="isShowMoreSearch">
              <!--隐藏区域-->
            </div>
          </el-collapse-transition>
          <div class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="search"
              >搜索</el-button
            >
            <el-button @click="clearSearchVal">清空</el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="operator">
      <el-button
        v-has-permission-code-and="'003000150005'"
        type="primary"
        icon="el-icon-plus"
        @click="toSave()"
        >添加
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-copy-document"
        @click="batchOutports()"
        >信息导出</el-button
      >
      <el-button
        v-has-permission-code-and="'003000150015'"
        type="danger"
        icon="el-icon-delete"
        @click="batchRemove()"
      >
        批量删除</el-button
      >
    </div>

    <div class="dataTable autoFlex" v-if="listData">
      <el-table
        class="table"
        :data="listData.records"
        stripe
        border
        height="auto"
        @selection-change="selectionChange"
        v-loading="listLoading"
      >
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
        ></el-table-column>
        <el-table-column prop="name" width="260" label="班级/开班">
         <template slot-scope="scope">
         {{scope.row.name}} <br/>
            <el-tag size="mini" type="danger">{{scope.row.starttime}}</el-tag >
          </template>

        </el-table-column>
        <el-table-column prop="dbColumn_cid"   label="课程">
          <!-- <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.cname == undefined">无</el-tag>
            <el-tag size="mini" v-else @click="showCourse(scope.row.cname)"
              >查看</el-tag
            >
          </template> -->
        </el-table-column>
        <el-table-column prop="dbColumn_tid"  width="90" label="带班老师"></el-table-column>
       
        <el-table-column prop="types"   width="110" label="类型"></el-table-column>
        <el-table-column prop="state" width="75" label="状态"></el-table-column>
        <el-table-column prop="stucnt" width="75" label="人数"></el-table-column>
        <el-table-column prop="monitor"  width="90" label="班长"></el-table-column>
        <el-table-column prop="preptime" width="200" label="预科/结课时间">
          <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.preptime" type="info">{{scope.row.preptime}}</el-tag>
            <span v-if="scope.row.endtime">&nbsp;-&nbsp;</span>
            <el-tag size="mini" type="success" v-if="scope.row.endtime">{{scope.row.endtime}}</el-tag ></template>
        </el-table-column>
         <el-table-column prop="historyteacher" width="120" label="带班老师纪录">
          <template slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.historyteacher == undefined"
              >无</el-tag>
            <el-tag
              size="mini"
              v-else
              @click="showHistoryClass(scope.row.historyteacher)"
              >查看</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="280px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button
                v-has-permission-code-and="'003000150020'"
                size="mini"
                icon="el-icon-view"
                @click="toDetail(scope.$index, scope.row)"
              >
              </el-button>
              <el-button
                v-has-permission-code-and="'003000150010'"
                size="mini"
                icon="el-icon-edit"
                @click="toUpdate(scope.$index, scope.row)"
              >
              </el-button>
              <!-- <el-button size="mini" icon="el-icon-delete" type="danger" @click="remove(scope.$index, scope.row)">
              </el-button> -->
              <el-button size="mini" @click="toStudent(scope.$index, scope.row)"
                >学生</el-button
              >
              <el-button
                v-has-permission-code-and="'003000150025'"
                size="mini"
                @click="toPwd(scope.$index, scope.row)"
              >
                重置密码</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        :title="dialogClassName"
        :visible.sync="dialogTableVisible"
        width="90%"
      >
        <el-table
          :data="stusData"
          v-loading="stulistLoading"
          height="400"
          stripe
        >
          <el-table-column prop="name" label="学生名字"></el-table-column>
          <el-table-column prop="head" label="头像">
            <template slot-scope="scope">
              <el-avatar
                size="large"
                :src="scope.row.head"
                v-if="scope.row.head"
              ></el-avatar>
            </template>
          </el-table-column>
          <el-table-column prop="sex" label="性别">
            <template slot-scope="scope">
              <div v-if="scope.row.sex == 1">男</div>
              <span v-if="scope.row.sex == 2">女</span>
            </template>
          </el-table-column>
          <el-table-column prop="nativeplace" label="籍贯"></el-table-column>
          <el-table-column prop="phone" label="手机号码"></el-table-column>
          <el-table-column prop="adress" label="地址"></el-table-column>
          <el-table-column prop="cometime" label="报到/上课时间">
            <template slot-scope="scope">
              <el-tag size="mini" type="success">{{
                scope.row.cometime
              }}</el-tag>
              <el-tag size="mini">{{ scope.row.schooltime }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="groupleader" label="职务">
            <template slot-scope="scope">
              <el-tag
                v-if="scope.row.name == dlg_lbz"
                size="mini"
                type="success"
                >班长</el-tag
              >
              <el-tag v-if="scope.row.groupleader == 2" size="mini"
                >组员</el-tag
              >
              <el-tag
                v-if="scope.row.groupleader == 1"
                size="mini"
                type="warning"
                >组长</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column prop="historytime" label="是否转班">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.historytime" size="mini" type="danger"
                >是</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column prop="school" label="学校/专业">
            <template slot-scope="scope">
              <el-tag size="mini" type="success">{{ scope.row.school }}</el-tag>
              <el-tag size="mini">{{ scope.row.professional }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="state" label="状态">
            <template slot-scope="scope">
              <div v-if="scope.row.state == 1">在读</div>
              <span v-if="scope.row.state == 2">休学</span>
              <span v-if="scope.row.state == 3">毕业</span>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <div class="pageBar">
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :total="listData.total"
          :page-size="page.size"
          :current-page="page.current"
          :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"
        ></el-pagination>
      </div>
    </div>
    <div>
      <el-dialog
        title="转班纪录查看"
        :visible.sync="dialogVisible2"
        width="30%"
      >
        <span :key="index" v-for="(tag, index) in urlList"
          >{{ tag.teacher }}带班时间：从<el-tag>{{ tag.time }}</el-tag
          >到<el-tag>{{ tag.time2 }} </el-tag> <br />
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible2 = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
    </div>
    <!-- <el-dialog title="课程查看" :visible.sync="dialogVisible3" width="30%">
      课程列表:<span :key="index" v-for="(tag, index) in urlList2">
        <el-tag>{{ tag.cname }}</el-tag>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible3 = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible3 = false">确 定</el-button>
      </span>
    </el-dialog> -->
    <edit ref="edit"></edit>
  </div>
</template>


<script>
import edit from "./edit.vue";
import api from "@/api/edu/clazz/clazz.js";
import stuapi from "@/api/stus/student/student5.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "clazz",
  components: {
    edit,
    selectOptionDictionary,
  },
  data() {
    return {
      dialogClassName: "",
      urlList: {},
      dialogVisible2: false,
      // urlList2: {},
      // dialogVisible3: false,
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      stulistLoading: false,
      stusData: [],
      selected: [],
      page: new this.$constant.pageObj(),
      dialogTableVisible: false,
      dlg_lbz: "",
    };
  },
  methods: {
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        this.listData = response.data;
        this.listLoading = false;
      });
    },
    //去添加
    toSave(index, row) {
      this.$refs.edit.open2({}, "班级添加", false);
    },
    //去编辑
    toUpdate(index, row) {
      this.$refs.edit.open(row, row.name + " - 调整信息", false, row);
    },
    //去详情
    toDetail(index, row) {
      this.$refs.edit.open(
        row,
        row.name + " - 查看详情",
        true,
        row
      );
    },
    //带班老师记录
    showHistoryClass(t) {
      this.urlList = JSON.parse(t);
      this.dialogVisible2 = true;
    },

    //查看该班学生
    toStudent(index, row) {
      this.dialogTableVisible = true;
      this.stulistLoading = true;
      this.dlg_lbz = row.monitor;
      this.dialogClassName = row.name;
      let parameter = {
        id: row.id,
      };
      stuapi.toStus(parameter, (response) => {
        this.stusData = response.data;
        this.stulistLoading = false;
      });
    },
    //重置密码
    toPwd(index, row) {
      let parameter = {
        deftpwd: row.deftpwd,
        id: row.id,
      };
      this.$utils.confirm.warning(
        "提示",
        "确定重置 ' " + row.name + " ' 学生密码吗？",
        () => {
          stuapi.updStuPwd(
            parameter,
            (response) => {
              this.list();
              this.$utils.msg.success(response.msg);
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              alert("操作失败，该班级没有学生");
            }
          );
        }
      );
    },
    //删除
    remove(index, row) {
      let parameter = {
        ids: row.id,
      };
      this.$utils.confirm.warning(
        "提示",
        "确定删除 ' " + row.name + " ' 吗？",
        () => {
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
        }
      );
    },
    //班级学生信息导出
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
    batchOutports() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      let parameter = {
        ids: this.selected,
      };
      let ids = Array.from(this.selected);
      console.log(ids.toString());
      var url =
        "http://127.0.0.1:8036/study3/edu/clazz/exportScore2?ids=" + ids;
      axios({
        method: "get",
        url: url,
        responseType: "blob",
      })
        .then((response) => {
          const content = response.data;
          const blob = new Blob([content], {
            type: "application/vnd.ms-excel",
          });
          const fileName = "学生信息.xlsx";
          if ("download" in document.createElement("a")) {
            // 非IE下载
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink);
          } else {
            // IE10+下载
            navigator.msSaveBlob(blob, fileName);
          }
        })
        .catch((err) => {
          console.log(err);
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
      this.listLoading = true;
      let params = {
        name: this.searchParams.name,
        types: this.searchParams.types,
      };
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
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>

