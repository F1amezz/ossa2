<template>
	<div>
		<tree :filterable="filterable" v-loading="treeLoading" ref="tree" :items="data" :defaultExpandAll="true" :defaultParent="0" :showCheckbox="multiple"
		 :relationType="relationType" @check="check" :label="lableKey" @nodeClick="click"></tree>
	</div>
</template>

<script>
	import api from '@/api/components/biz/departmentTree/departmentTree.js';
	import tree from '@/components/tree/tree.vue';
	export default {
		name: 'departmentTree',
		components: {
			tree,
		},
		data() {
			return {
				treeLoading: false,
				selectedValues: [],
				selectedListData: [],
				data: [],
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
			 * 多选
			 */
			multiple: {
				type: Boolean,
				default: false,
				required: false
			},
			/**
			 * 关联类型  Y表示勾选时 N表示取消勾选时 p表示父关联子 s表示子关联父
			 */
			relationType: {
				type: Object,
				default () {
					return {
						Y: 'ps',
						N: 'ps'
					};
				},
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
			//开启过滤
			filterable: {
				type: Boolean,
				default () {
					return false;
				}
			},
		},
		methods: {
			click(data) {
				if (!this.multiple) {
					this.selectedListData = data;
					this.selectedValues = data[this.valueKey];
					this.$emit("change", this.selectedValues);
				}
			},
			check(data, checkedObj) {
				if (this.multiple) {
					//多选
					this.selectedListData = checkedObj;
					this.selectedValues = [];
					if (!_.isEmpty(this.selectedListData)) {
						for (var item of this.selectedListData) {
							this.selectedValues.push(item[this.valueKey]);
						}
					}
					if (!this.stringMode) {
						this.$emit("change", this.selectedValues);
					} else {
						this.$emit("change", this.selectedValues.join(','));
					}
				} else {
					val = [];
				}
			},
			getSelectedData() {
				return this.selectedListData;
			},
			list() {
				this.treeLoading = true;
				api.list(null, response => {
					this.data = response.data;
					this.$refs.tree.setSelectedIds(this.selectedValues);
					this.treeLoading = false;
				});
			},
			init() {
				this.list();
			},
		},
		watch: {
			value: {
				handler: function(val) {
					if (this.multiple) {
						if (!_.isEmpty(val)) {
							if (this.stringMode) {

								if (_.isArray(val)) {
									console.error("departmentTree  数据为数组，请使用数组模式。");
									return;
								}
								val = val.split(",");
							} else {
								if (_.isString(val)) {
									console.error("departmentTree  数据为字符串，请使用字符串模式。");
									return;
								}
							}
						}
					} else {
						if (!_.isEmpty(val)) {
							//单选
							if (_.isArray(val)) {
								console.error("departmentTree  数据类型不对，请使用字符串。");
								return;
							}
							val = [val];
						} else {
							val = [];
						}
					}
					this.selectedValues = val;
					this.$refs.tree.setSelectedIds(this.selectedValues);
				},
				immediate:true,
				deep: true
			},

		},
		created() {
			this.$nextTick(() => {
				this.init();
			})
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
