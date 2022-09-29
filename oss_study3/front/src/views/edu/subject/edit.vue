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
          <el-col :span="24">
            <el-form-item :label="$parent.Subflag + '名称'" prop="name"
              ><el-input
                v-model="formParameter.name"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="等级" prop="level"
              ><select-option-dictionary
                v-model="formParameter.level"
                dkey="level"
              ></select-option-dictionary
            ></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="排序" prop="sort"
              ><el-input
                v-model="formParameter.sort"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="8" v-show="$parent.Subflag == '科目'">
            <el-form-item :label="$parent.Subflag + '天数'" prop="days"
              ><el-input
                v-model="formParameter.days"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remk"
              ><el-input
                v-model="formParameter.remk"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-divider content-position="left"
              ><el-button
                size="mini"
                icon="el-icon-plus"
                @click="addSelectItem()"
              ></el-button>
              {{ this.$parent.Subflag }}视频地址
              <!-- v-show="options.length > 1" -->
              <el-button
                size="mini"
                icon="el-icon-delete"
                @click="removeSelectItem()"
              ></el-button
            ></el-divider>
            <!-- 视频路径选项 -->
            <el-row v-for="(item, index) in options" v-bind:key="index">
              <el-col :span="6">
                <el-form-item label="知识点">
                  <el-input v-model="item.k"> </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="18">
                <el-form-item label="视频url">
                  <el-input v-model="item.v"> </el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            @click="add(urlNum++)"
          ></el-button>
        </el-row> -->
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
import api from "@/api/edu/subject/subject.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";
export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      urlNum: 1,
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      options: [{ v: "" }],
      urlList: [],
      rules: {
        //名称
        name: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 100,
            trigger: "blur",
          },
        ],
        //等级
        level: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 2,
            trigger: "blur",
          },
        ],
        //排序
        sort: [
          {
            validator: this.$validate.length,
            max: 2,
            trigger: "blur",
          },
        ],
        //备注
        remk: [
          // {
          //   validator: this.$validate.required,
          //   trigger: "blur",
          // },
          {
            validator: this.$validate.length,
            max: 200,
            trigger: "blur",
          },
        ],
        //课程天数
        days: [
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
        //视频路径
        url: [
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
      },
    };
  },
  methods: {
    addSelectItem() {
      // let len = this.options.length - 1;
      // let last = this.options[len].o.charCodeAt() + 1;
      // console.log(last);
      this.options.push({ k: "", v: "" });
      console.log(this.options);
    },
    removeSelectItem() {
      let rc = this.options.pop();
      this.checkList.splice(this.checkList.indexOf(rc), 1);
    },
    //打开弹框
    open(parameter, title, disabled, size) {
      this.parameter = parameter;
      this.disabled = disabled;
      this.$refs.popup.open(title, size);
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
        this.formParameter.url = this.options;
        this.confirmLoading = true;
        if (!this.parameter.id) {
          //添加
          // JSON.parse(response.data.content)
          this.formParameter.pid = this.parameter.pid;
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
    init() {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;
      //清空内容
      this.formParameter = {};
      //清楚校验
      this.$refs.form.clearValidate();

      if (this.parameter.id) {
        //编辑查询数据
        let parameter = {
          id: this.parameter.id,
        };
        api.toUpdate(parameter, (response) => {
          this.formParameter = response.data;
          this.options = JSON.parse(response.data.url);
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>