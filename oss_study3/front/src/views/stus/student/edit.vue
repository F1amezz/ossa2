<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-row>
          <el-col :span="6">
            <el-form-item label="学生名字" prop="name">
              <el-input
                v-model="formParameter.name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="专业" prop="professional">
              <el-input
                v-model="formParameter.professional"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="籍贯" prop="nativeplace">
              <el-input
                v-model="formParameter.nativeplace"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="formParameter.email"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="手机号码" prop="mobile">
              <el-input
                v-model="formParameter.mobile"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="上课时间" prop="schooltime">
              <el-date-picker
                v-model="formParameter.schooltime"
                type="datetime"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学校" prop="school">
              <el-input
                v-model="formParameter.school"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="报到时间" prop="cometime">
              <el-date-picker
                v-model="formParameter.cometime"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="头像" prop="head">
              <image-upload
                v-model="formParameter.head"
                model="base64"
                :size="128"
                :type="String"
              ></image-upload>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="描述" prop="describes">
              <el-input
                v-model="formParameter.describes"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="备注" prop="remarks">
              <el-input
                v-model="formParameter.remarks"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="地址" prop="adress">
              <el-input
                v-model="formParameter.adress"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-form-item label="身份证" prop="idcard">
              <el-input
                v-model="formParameter.idcard"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="登录名" prop="lname">
              <el-input
                v-model="formParameter.lname"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学生密码" prop="pwd">
              <el-input
                v-model="formParameter.pwd"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="生日" prop="birth">
              <el-date-picker
                v-model="formParameter.birth"
                type="date"
                placeholder="选择日期时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="性别" prop="sex">
              <select-option-dictionary
                v-model="formParameter.sex"
                dkey="sex"
                :multiple="false"
                :stringMode="true"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="紧急联系人关系：" prop="relationship">
              <select-option-dictionary
                v-model="formParameter.relationship"
                dkey="relationship"
                :multiple="false"
                :stringMode="true"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="紧急联系人名字" prop="rname">
              <el-input
                v-model="formParameter.rname"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="紧急联系人电话" prop="rphone">
              <el-input
                v-model="formParameter.rphone"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态" prop="state">
              <select-option-dictionary
                v-model="formParameter.state"
                dkey="state"
                :multiple="false"
                :stringMode="true"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="班级" prop="cid">
              <el-select v-model="formParameter.cid" placeholder="请选择班级" >
                <el-option
                  v-for="item in clazzList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
              <!-- <el-input
                v-model="formParameter.cid"
                autocomplete="off"
              ></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职务" prop="groupleader">
              <select-option-dictionary
                v-model="formParameter.groupleader"
                dkey="group-leader"
              >
              </select-option-dictionary>
            </el-form-item>
          </el-col>
          

          <el-col :span="6" v-show="show">
            <el-form-item label="禁用" prop="isdisable">
              <el-input
                v-model="formParameter.isdisable"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="show">
          <el-col :span="6">
            <el-form-item label="转班时间" prop="historytime">
              <el-date-picker
                v-model="formParameter.cometime"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="转班历史班级" prop="historyclass">
              <el-input
                v-model="formParameter.historyclass"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="微信小程序unionID" prop="wxappuid">
              <el-input
                v-model="formParameter.wxappuid"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="微信小程序openID" prop="wxappopenid">
              <el-input
                v-model="formParameter.wxappopenid"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button
        v-if="!disabled"
        type="primary"
        :loading="confirmLoading"
        @click="confirm()"
        >确 定</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/stus/student/student.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
import imageUpload from "@/components/biz/imageUpload/imageUpload.vue";

export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary,
    imageUpload,
  },
  data() {
       var checkEmail = (rule, value, callback) => {
      	const regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      if (regEmail.test(value)) {
        return callback();
      }
      callback(new Error("请输入合法的验证码"));
    };
      var checkMobile = (rule, value, callback) => {
      const regMobile =/^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
      if (regMobile.test(value)) {
        return callback();
      }
      callback(new Error("请输入合法的手机号"));
    };
     var checkRphone = (rule, value, callback) => {
      const regRphone =/^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
      if (regRphone.test(value)&&this.formParameter.rphone!==this.formParameter.mobile) {
        return callback();
      }else if(this.formParameter.rphone===this.formParameter.mobile){
        callback(new Error("紧急联系电话和手机号一致"))
      }else{
        callback(new Error("请输入合法的紧急联系人手机号"));
      }
      
    };
    return {
      flag: "",
      flag2: [],
      clazzList: {},
      show: false,
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {relationship:1,state:1},
      rules: {
        //手机号码
        mobile: [
           { required: true, message: "请输入手机号", trigger: "blur" },
          {
            validator: checkMobile,
            trigger: "blur",
          },
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 16,
            trigger: "blur",
          },
          // default:"",
        ],
        //备注
        remarks: [
          {
            validator: this.$validate.length,
            max: 200,
            trigger: "blur",
          },
        ],
        //学校
        school: [
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
        //专业
        professional: [
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
        //籍贯
        nativeplace: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 200,
            trigger: "blur",
          },
        ],
        //报到时间
        cometime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //紧急联系人关系：1-父亲；2-母亲；3-兄弟；4-姐妹；5-亲戚；6-朋友
        relationship: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 11,
            trigger: "blur",
          },
        ],
        //紧急联系人名字
        rname: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 255,
            trigger: "blur",
          },
        ],
        //紧急联系人电话
        rphone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            validator: checkRphone,
            trigger: "blur",
          },
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 16,
            trigger: "blur",
          },
        ],
        //上课时间
        schooltime: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //身份证
        idcard: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 20,
            trigger: "blur",
          },
        ],
        //描述
        describes: [
          {
            validator: this.$validate.length,
            max: 200,
            trigger: "blur",
          },
        ],
        //状态：1-在读；2-休学；3-毕业
        state: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 11,
            trigger: "blur",
          },
        ],
        //班级id
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
        //转班时间
        historytime: [
          {
            trigger: "blur",
          },
        ],
        //转班历史班级
        historyclass: [
          {
            validator: this.$validate.length,
            max: 255,
            trigger: "blur",
          },
        ],
        //职务1.班长0组员2组长
        groupleader: [
          {
            validator: this.$validate.length,
            max: 11,
            trigger: "blur",
          },
        ],
        //地址
        adress: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 255,
            trigger: "blur",
          },
        ],
        //微信小程序unionID
        wxappuid: [
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //微信小程序openID
        wxappopenid: [
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
        //头像
        head: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //登录名
        lname: [
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
        //学生名字
        name: [
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
        //学生密码
        // pwd: [
        //   {
        //     validator: this.$validate.required,
        //     trigger: "blur",
        //   },
        //   {
        //     validator: this.$validate.length,
        //     max: 32,
        //     trigger: "blur",
        //   },
        // ],
        //生日
        birth: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        //性别：1-男；2-女
        sex: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 1,
            trigger: "blur",
          },
        ],
        //email
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 255,
            trigger: "blur",
          },
        ],
        //isdisable
        isdisable: [
          {
            validator: this.$validate.length,
            max: 2,
            trigger: "blur",
          },
        ],
      },
    };
  },

  methods: {

    //打开弹框
    open(parameter, title, disabled, size) {
      this.parameter = parameter;
      this.disabled = disabled;
      this.flag = size;
      this.$refs.popup.open(title, "80%");
      this.$nextTick(() => {
        this.init();
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },
    //提交表单
    confirm() {
      this.$utils.checkForm(this.$refs.form, () => {
        if (!this.parameter.id) {
          this.confirmLoading = true;
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
          /**
           * 修改班级，添加班级纪录
           */
          let array2 = [];
          if (!(this.flag == this.formParameter.cid)) {
            if (this.formParameter.historyclass != undefined) {
              array2 = JSON.parse(this.formParameter.historyclass);
            }
            let array = [];
            this.formParameter.historytime = new Date().format("yyyy-MM-dd");
            let i = this.formParameter.cid;
            let name1;
            for (var p1 in this.clazzList) {
              if (this.flag == this.clazzList[p1].id) {
                name1 = this.clazzList[p1].name;
              }
            }
            for (var p1 in this.clazzList) {
              if (i == this.clazzList[p1].id) {
                array.push({
                  name1: name1,
                  name: this.clazzList[p1].name,
                  time: this.formParameter.historytime,
                });
              }
            }

            let array3 = [];
            if (array2.length != 0) {
              for (let i = 0; i < array2.length; i++) {
                const el = array2[i];
                array3.push(el);
              }
              // console.log("又转班级22");
            }
            for (let i = 0; i < array.length; i++) {
              const el = array[i];
              array3.push(el);
            }
            this.formParameter.historyclass = array3;
          }
          /**
           * 修改学生信息
           */
          //编辑
          let parameter = this.$utils.merger(this.formParameter);
          if (!(this.flag == this.formParameter.cid)) {
            this.$utils.confirm.warning("提示", "确定转班吗？", () => {
              this.confirmLoading = true;
              api.update(
                parameter,
                (response) => {
                  this.$utils.msg.success(response.msg);
                  // this.confirmLoading = false;
                  this.close();
                  this.$parent.list();
                },
                (response) => {
                  this.$utils.msg.warning(response.msg);
                  this.confirmLoading = false;
                }
              );
            });
          } else {
            this.confirmLoading = true;
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
        }
      });
    },
    init() {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;
      //清空内容
      //清楚校验
      this.$refs.form.clearValidate();

      let parameter = {
        id: this.parameter.id,
      };
      api.toUpdate(parameter, (response) => {
        this.formParameter = response.data.student;
        this.clazzList = response.data.clazzList;
        this.popupLoading = false;
      });
      if (this.parameter.id) {
        //编辑查询数据
      } else {
        
      }
    },
  },
     
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
