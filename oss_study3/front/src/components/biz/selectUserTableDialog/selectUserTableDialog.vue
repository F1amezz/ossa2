<template>
	<div style="display: flex;">
		<el-select style="flex:1" class="multipleInput" v-model="selectedValues" multiple @remove-tag=""
			:disabled="disabled" :popper-append-to-body="false" :placeholder="placeholder" @remove-tag="removeTag">
			<el-option v-for="item in selectedListData" :key="item[valueKey]" :label="item[lableKey]" :value="item.id">
			</el-option>
		</el-select>
		<el-button slot="append" @click="open" icon="el-icon-search" style="width: 80px;" :disabled="disabled">
		</el-button>
		<user-table-dialog ref="userTable" :multiple="multiple" @confirm="confirm"></user-table-dialog>
	</div>
</template>

<script>
	import userTableDialog from '@/components/biz/selectUserTableDialog/userTableDialog.vue';
	import api from '@/api/components/biz/selectUserTableDialog/selectUserTableDialog.js';
	export default {
		name: 'selectUserTableDialog',
		components: {
			userTableDialog,
		},
		data() {
			return {
				selectedValues: [],
				selectedListData: [],
			};
		},
		model: {
			prop: 'value',
			event: 'change'
		},
		props: {
			value: {
				type: String | Array | Object,
				required: false,
				default: () => {},
			},
			/**
			 * 显示
			 */
			disabled: {
				type: Boolean,
				required: false,
				default: false
			},
			/**
			 * 提示
			 */
			placeholder: {
				type: String,
				required: false,
				default: "请选择"
			},
			/**
			 * 获取值
			 */
			valueKey: {
				type: String,
				required: false,
				default: "id"
			},
			/**
			 * 显示值
			 */
			lableKey: {
				type: String,
				required: false,
				default: "name"
			},
			/**
			 * 多选
			 */
			multiple: {
				type: Boolean,
				default: false,
				required: false
			},
			/**
			 * 字符串模式,返回逗号分隔字符串，例如 "1,2,3,4"，否则是数组
			 */
			stringMode: {
				type: [Boolean],
				default: false,
				required: false
			},
		},
		watch: {
			value: {
				handler: function(val) {
					if (this.multiple) {
						if (!_.isEmpty(val)) {
							if (this.stringMode) {
								if (_.isArray(val)) {
									console.error("selectUserTable  数据为数组，请使用数组模式。");
									return;
								}
								val = val.split(",");
							} else {
								if (_.isString(val)) {
									console.error("selectUserTable  数据为字符串，请使用字符串模式。");
									return;
								}
							}
						}
					} else {
						//单选
						if (!_.isEmpty(val)) {
							if (_.isArray(val)) {
								console.error("departmentTree  数据类型不对，请使用字符串。");
								return;
							}
							val = [val];
						} else {
							val = [];
						}
					}
					if (!_.isEmpty(val)) {
						if (val != this.selectedValues) {
							this.selectedValues = val;
							//查询
							let parameter = {
								ids: this.selectedValues
							};
							api.getByIds(parameter, response => {
								if (!_.isEmpty(response.data)) {
									this.selectedListData = response.data;
								}
							});
						}
					} else {
						this.selectedValues = [];
						this.selectedListData = [];
					}
				},
				immediate: true,
				deep: true
			}
		},
		methods: {
			init() {

			},
			//选择框确认
			confirm(val) {
				this.selectedListData = val;
				this.selectedValues = [];
				for (var item of this.selectedListData) {
					this.selectedValues.push(item[this.valueKey]);
				}
				if (this.multiple) {
					if (!this.stringMode) {
						this.$emit("change", this.selectedValues);
					} else {
						this.$emit("change", this.selectedValues.join(','));
					}
				} else {
					if (!_.isEmpty(this.selectedValues)) {
						this.$emit("change", this.selectedValues[0]);
					} else {
						this.$emit("change", "");
					}
				}

			},
			open() {
				this.$refs.userTable.open(this.selectedListData, this.placeholder, '95%');
			},
			removeTag(value) {
				if (!_.isEmpty(this.selectedListData)) {
					this.selectedListData.forEach((val, index) => {
						if (val[this.valueKey] == value) {
							this.selectedListData.splice(index, 1);
						}
					});
				}
				this.confirm(this.selectedListData);
			}
		},
		created() {
			this.init();
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';

	.multipleInput {

		/deep/.el-select-dropdown,
		/deep/.el-input__suffix {
			display: none !important;
		}
	}
</style>
