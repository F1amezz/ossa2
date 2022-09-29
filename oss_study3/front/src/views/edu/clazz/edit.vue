<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true" label-width="80px"
        :rules="rules">
        <el-row>
          <el-col :span="8">
            <el-form-item label="班级" prop="name">
              <el-input v-model="formParameter.name" placeholder="请输入班级名称" autocomplete="off">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属课程" prop="cid">
              <el-select v-model="formParameter.cid" placeholder="请选择课程">
                <el-option v-for="item in courseName" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
           <el-form-item label="默认密码" prop="deftpwd">
              <el-input v-model="formParameter.deftpwd" placeholder="请输入密码" autocomplete="off">
              </el-input>
            </el-form-item>
          </el-col>
         </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="班长" prop="monitor">
              <el-input v-model="formParameter.monitor" placeholder="请输入班长名称" autocomplete="off">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类型" prop="state">
              <select-option-dictionary v-model="formParameter.types" dkey="clazz-types">
              </select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态" prop="state">
              <select-option-dictionary v-model="formParameter.state" dkey="clazz-state">
              </select-option-dictionary>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="预科时间" prop="preptime">
              <el-date-picker v-model="formParameter.preptime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开班时间" prop="starttime">
              <el-date-picker v-model="formParameter.starttime" type="date" value-format="yyyy-MM-dd"
                placeholder="选择日期"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结课时间" prop="endtime">
              <el-date-picker v-model="formParameter.endtime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" placeholder="请输入..." :autosize="{ minRows: 5, maxRows: 8 }"
                v-model="formParameter.remark" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
          <el-row v-for="(htr,idx) in historyteacher" :key="idx">
          <el-col :span="6">
            <el-form-item :label="'带班老师'+(idx+1)" >
              <el-input  readonly   v-model="htr.teacher" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
              <el-col :span="4">
            <el-input readonly  v-model="htr.time" autocomplete="off"></el-input>
          </el-col>
           <el-col :span="4">
            <el-input readonly  v-model="htr.time2" autocomplete="off"></el-input>
          </el-col>
           <el-col v-if="!disabled" :span="4">
             <el-button @click="delhTcr(idx)">删除</el-button>
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
import popup from "@/components/popup/drawerPopup.vue";
import clazzapi from "@/api/edu/clazz/clazz.js";
import userapi from "@/api/system/user/user.js";
// import teacherapi from "@/api/edu/teacher/teacher.js";
import courseapi from "@/api/edu/course/course.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      // checkAll: false,
      // checkedCourse: [],
      // isIndeterminate: true,
      prepTime: "",
      flag: "",
      disabled: false,
      teacherName: [],
      courseName: [],
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      historyteacher:[],
      rules: {
        //班级名称
        name: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //老师id
        tid: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //课程id
        cid: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //类型：1-线上推广班，2-线下推广班，3-培训班
        types: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 16,
            trigger: 'blur'
          }
        ],
        //状态：预科，授课
        state: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 16,
            trigger: 'blur'
          }
        ],
        //该班班长
        monitor: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //预科时间
        preptime: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //开班时间
        starttime: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //结课时间
        endtime: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
        //班级默认密码
        deftpwd: [
          {
            validator: this.$validate.required,
            trigger: 'blur'
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: 'blur'
          }
        ],
      },
    };
  },
  watch: {
    checkedCourse: function (a, b) { },
  },
  methods: {
    delhTcr(idx){
       this.historyteacher.splice(idx,1);
    },
    //打开弹框
    open(parameter, title, disabled, row) {

      this.formParameter = parameter;
      console.log(parameter)
      if(this.formParameter.historyteacher){
           this.historyteacher = JSON.parse(this.formParameter.historyteacher)  
      }
     
      this.disabled = disabled;
      this.flag = row.tid;
      this.prepTime = row.preptime;
      this.$refs.popup.open(title, "72%");
     
    },
    open2(parameter, title, disabled) {
      this.parameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, "72%");
      this.$nextTick(() => {
        this.init();
      });
    },
    getTeaName(item) { },

    //获取老师名字
    toTeachNm() {
      this.listLoading = true;
      userapi.list2({}, (res) => {
        this.teacherName = res.data.records;
        this.listLoading = false;
      });
    },
    //获取课程名称
    toCourseName() {
      this.listLoading = true;
      courseapi.list({}, (res) => {
        this.courseName = res.data.records;
        this.listLoading = false;
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },

    //提交表单
    confirm() {
      let tname;
      let tid = this.formParameter.tid;
      for (const item2 of this.teacherName) {
        if (tid == item2.id) {
          tname = item2.name;
        }
      }
      this.$utils.checkForm(this.$refs.form, () => {
        this.confirmLoading = true;
        if (!this.formParameter.id) {
          //添加
          let array = [];
          array.push({
            time: this.formParameter.preptime,
            time2: "现在",
            teacher: tname,
          });
          this.formParameter.historyteacher = array;
          let parameter = this.$utils.merger(this.formParameter);
          clazzapi.save(
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
          /**
           * 是否修改老师判断
           */
          if (!(this.flag == tid)) {
            /**
             * 添加老师记录
             */
            let time = new Date().format("yyyy-MM-dd");
            let array = [];
            let array2 = [];
            if (this.formParameter.historyteacher == undefined) {
              array2.push(
                {
                  time: this.prepTime,
                  time2: time,
                  teacher: this.formParameter.dbColumn_tid,
                },
                {
                  time: time,
                  time2: "现在",
                  teacher: tname,
                }
              );
            } else {
              array = JSON.parse(this.formParameter.historyteacher);
              for (const item in array) {
                if (array.length - 1 == item) {
                  array2.push(
                    {
                      time: array[item].time,
                      time2: time,
                      teacher: this.formParameter.dbColumn_tid,
                    },
                    {
                      time: time,
                      time2: "现在",
                      teacher: tname,
                    }
                  );
                } else {
                  array2.push({
                    time: array[item].time,
                    time2: array[item].time2,
                    teacher: array[item].teacher,
                  });
                }
              }
            }
            this.formParameter.historyteacher = array2;
          }
          //历史老师记录同步
          this.formParameter.historyteacher = JSON.stringify(this.historyteacher)

          let parameter = this.$utils.merger(this.formParameter);
          clazzapi.update(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (err) => {
              this.$utils.msg.warning(err.msg);
              this.confirmLoading = false;
            }
          );
        }
      });
    }

  },
  mounted() {
    this.toTeachNm();
    this.toCourseName();
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>
