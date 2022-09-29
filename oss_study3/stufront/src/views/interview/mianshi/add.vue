<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :model="interview"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-row>
          <el-col :span="18">
            <el-form-item label="企业名称" prop="cmpname">
              <el-autocomplete
                v-model="interview.cmpname"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入内容"
                @select="handleSelect"
              ></el-autocomplete>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="岗位" prop="postid">
              <el-select
                filterable 
                remote
                :remote-method="remoteMethod"
                v-model="interview.postid"
                @change="postSelected"
                placeholder="请选择"
              >
                <el-option
                  v-for="(itm,i) in postList"
                  :key="i"
                  :label="itm.name"
                  :value="i"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="面试时间" prop="mstm">
              <el-date-picker
                v-model="interview.mstm"
                type="datetime"
                placeholder="选择面试时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="面试准备" prop="mszbqk">
              <el-rate v-model="interview.mszbqk" :colors="colors"> </el-rate>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-dialog
        append-to-body
        title="企业-岗位信息维护"
        :visible.sync="dialogFormVisible"
      >
        <el-form :model="company" label-width="120px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="名    称" prop="name"
                ><el-input v-model="company.name" autocomplete="off"></el-input
              ></el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="官    网" prop="url"
                ><el-input v-model="company.url" autocomplete="off"></el-input
              ></el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="规    模" prop="maxemps">
                <select-option-dictionary
                  v-model="company.maxemps"
                  dkey="cmpygm"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="来源类型" prop="source">
                <select-option-dictionary
                  v-model="company.source"
                  dkey="jycmp-ly"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="电  话" prop="tel"
                ><el-input v-model="company.tel" autocomplete="off"></el-input
              ></el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="联系人/电话" prop="mobile"
                ><el-input
                  v-model="company.mobile"
                  autocomplete="off"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="地址" prop="address"
                ><el-input
                  v-model="company.address"
                  autocomplete="off"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="公司描述" prop="descb"
                ><el-input
                  type="textarea"
                  :rows="2"
                  v-model="company.descb"
                  autocomplete="off"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="公司备注" prop="remark"
                ><el-input
                  type="textarea"
                  :rows="1"
                  v-model="company.remark"
                  autocomplete="off"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="岗位名称" prop="name"
                ><el-input v-model="post.name" autocomplete="off"></el-input
              ></el-form-item>
            </el-col>
            <el-col :span="16">
              <el-form-item label="开始时间" prop="bgntm">
                <el-date-picker
                  v-model="dateArr"
                  type="daterange"
                  value-format="yyyy-MM-dd"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="招聘人数" prop="maxneed"
                ><el-input
                  v-model="post.maxneed"
                  autocomplete="off"
                  placeholder="单位个"
                ></el-input
              ></el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="学历" prop="xueli">
                <select-option-dictionary
                  v-model="post.xueli"
                  dkey="xueli"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="工作经验" prop="exps"
                ><el-input
                  v-model="post.exps"
                  autocomplete="off"
                  placeholder="例如:1年"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="待遇" prop="sarry"
                ><el-input
                  v-model="post.sarry"
                  autocomplete="off"
                  placeholder="例如:6.5k-8k"
                ></el-input
              ></el-form-item>
            </el-col>

            <el-col :span="16">
              <el-form-item label="岗位要求" prop="tech"
                ><el-input
                  v-model="post.tech"
                  autocomplete="off"
                  placeholder="精通echarts、前端666"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="岗位备注" prop="remark"
                ><el-input
                  type="textarea"
                  :rows="1"
                  v-model="post.remark"
                  autocomplete="off"
                ></el-input
              ></el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirm()">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div slot="footer">
      <el-button type="primary" :loading="confirmLoading" @click="toNext()"
        >下一步</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/interview/mianshi/mianshi.js";
import apiCmp from "@/api/interview/company/company.js";
import apiPost from "@/api/interview/posts/posts.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      popupLoading: false,
      confirmLoading: false,

      cmpyList: [],
      postList: [],
      dialogFormVisible: false,
      company: {},
      post: {},
      interview: {},
      dateArr: [],
      formLabelWidth: 200,

      colors: { 3: "#CC3366", 4: "#00CC33", 5: "#00CC33" },
      rules: {
        //面试准备情况：ABCD
        cmpname: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        mstm: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        mszbqk: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    remoteMethod(postnm){
        this.postList = [ {name:postnm}  ]
    },
    showSelect() {
      console.log(this.interview)
      if (this.postList) {
        if (this.postList.length > 0) {
          return true;
        }
      }
      return false;
    },
    querySearchAsync(queryString, cb) {
      //去后端取回数据
      if (queryString && queryString.length > 0) {
        apiCmp.listIntra(
          { name: queryString },
          (res) => {
            let cmpys = res.data;
            if (cmpys && cmpys.length > 0) {
              cmpys.forEach((v) => {
                let itm = v;
                itm.value = v.name;
                this.cmpyList.push(itm);
              });
              cb(cmpys);
            } else {
              this.postList = [];
              this.interview.postid = "";
            }
          },
          (err) => {}
        );
      }
    },
    handleSelect(item) {
      //console.log(item);
      this.company = item;
      apiPost.list({ cname: item.name }, (res) => {
        this.postList = res.data.records;
      });
    },postSelected(i){
         this.post =  this.postList[i]
    },
    //打开弹框
    open(parameter, title, disabled, size) {
      this.interview = parameter;
      this.$refs.popup.open(title, "750px");
    },

    //关闭弹框
    close() {
      this.$refs.popup.close();
      this.interview = {};
      this.company = {};
      this.post = {};
      this.postList=[]
      this.cmpyList=[]
    },
    toNext() {
      console.log("interview.postid")
      console.log(this.interview.postid)
      this.$utils.checkForm(this.$refs.form, () => {
           // 转换成岗位名称
      this.interview.postid = this.postList[this.interview.postid ]

      this.dialogFormVisible = true;

      });
    },

  //提交表单
  confirm() {
  
    console.log(this.company);
    console.log(this.post);
    console.log(this.interview);

     api.saveDTO(
          { intv:this.interview,cmp:this.company,post:this.post  },
          (res) => {
            this.$utils.msg.success(res.msg);
            this.confirmLoading = false;
            this.close();
            this.$parent.list();
            this.dialogFormVisible = false;
          },
          (err) => {
            this.$utils.msg.warning(err.msg);
            this.confirmLoading = false;
          }
        );
     }
   }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
