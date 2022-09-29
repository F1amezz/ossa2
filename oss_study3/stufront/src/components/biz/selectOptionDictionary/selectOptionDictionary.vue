<template>
	<el-select v-model="selectedValues" :multiple="multiple" :placeholder="placeholder" :clearable="clearable"  @change="change">
		<el-option v-for="item in selectedListData" :key="item[valueKey]" :label="item[lableKey]"
			:value="item[valueKey]">
		</el-option>
	</el-select>
</template>

<script>
	import utils from 'util/utils.js';
	import api from '@/api/components/biz/selectOptionDictionary/selectOptionDictionary.js';
	export default {
		name: 'selectComponents',
		//上面是父组件可传入参数
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
			clearable: {
				type: Boolean,
				required: false,
				default () {
					return true;
				}
			},
			/**多选**/
			multiple: {
				type: Boolean,
				required: false,
				default () {
					return false;
				}
			},
			//查询参数
			dkey: {
				type: String,
				required: true,
				default () {
					return "";
				}
			},
			/**
			 * 获取值
			 */
			valueKey: {
				type: String,
				required: false,
				default: "dvalue"
			},
			/**
			 * 显示值
			 */
			lableKey: {
				type: String,
				required: false,
				default: "dkey"
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
			/**
			 * 提示
			 */
			placeholder: {
				type: String,
				required: false,
				default: "请选择"
			},
			value: {
				type: String | Array | Object | Number,
				required: false,
				default: () => {},
			},

		},
		methods: {
			init() {
				//查询
				let parameter = {
					dkey: this.dkey
				};
				api.list(parameter, response => {
					if (!_.isEmpty(response.data)) {
						this.selectedListData = response.data;
					}
				});
			},
			change(val) {
				this.$emit("change", val);
			},
		},
		created() {
			this.$nextTick(() => {
				this.init();
			})
		},
		watch: {
			value: {
				handler: function(val) {
					//转换字符串类型（如果非字符串，可能会导致命中问题，例如: 1==="1"）
					if (_.isNumber(val)) {
						val = val.toString();
					}
					if (_.isArray(val)) {
						let valArr = [];
						_.forEach(val, function(value) {
							if (_.isNumber(value)) {
								valArr.push(value.toString());
							} else {
								valArr.push(value);
							}
						});
						val = valArr;
					}
					if (this.multiple) {
						if (!_.isEmpty(val)) {
							if (this.stringMode) {
								if (_.isArray(val)) {
									console.error("selectOptionDictionary  数据为数组，请使用数组模式。");
									return;
								}
								val = val.split(",");
							} else {
								if (_.isString(val)) {
									console.error("selectOptionDictionary  数据为字符串，请使用字符串模式。");
									return;
								}
							}
						}
					} else {
						//单选
						if (!_.isEmpty(val)) {
							if (_.isArray(val)) {
								console.error("selectOptionDictionary  数据类型不对，请使用字符串。");
								return;
							}
							val = [val];
						} else {
							val = [];
						}
					}
					if (!_.isEmpty(val)) {
						if (this.multiple) {
							this.selectedValues = val;
						} else {
							this.selectedValues = val[0];
						}
					} else {
						if (this.multiple) {
							this.selectedValues = [];
						} else {
							this.selectedValues = "";
						}
					}
				},
				immediate: true,
				deep: true
			}
		}
	};
</script>
