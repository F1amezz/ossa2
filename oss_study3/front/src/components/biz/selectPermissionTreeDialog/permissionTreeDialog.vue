<template>
	<popup ref="popup" :loading="popupLoading" :append-to-body="true">
		<div slot="body" style="position: relative;height: 100%;margin-left: 5px; margin-right: 5px;">
			<div class="tab-body">
				<tree :filterable="filterable" v-loading="treeLoading" ref="tree" :items="data" :defaultExpandAll="true" :defaultParent="0" :showCheckbox="multiple"
				 :relationType="relationType"></tree>
			</div>
		</div>
		<div slot="footer">
			<el-button type="primary" @click="confirm">确 定</el-button>
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
	import popup from '@/components/popup/dialogPopup.vue';
	import api from '@/api/components/biz/selectPermissionTreeDialog/selectPermissionTreeDialog.js';
	import tree from '@/components/tree/tree.vue';
	export default {
		name: 'permissionTreeDialog',
		components: {
			tree,
			popup,
		},
		data() {
			return {
				parameter: {},
				treeLoading: false,
				popupLoading: false,
				data: [],
				selectedListData: [],
				defaultCheckedKeys: [],
			};
		},
		props: {
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
			//开启过滤
			filterable: {
				type: Boolean,
				default () {
					return false;
				}
			},
		},
		methods: {
			removeSelected(id) {
				let index = _.findIndex(this.defaultCheckedKeys, function(o) {
					return o == id;
				});
				this.defaultCheckedKeys.splice(index, 1);
			},
			confirm() {
				this.selectedListData = this.$refs.tree.getSelecteNodes();
				this.$emit("confirm", this.selectedListData);
				this.close();
			},
			list() {
				this.treeLoading = true;
				api.list(null, response => {
					this.data = response.data;
					this.treeLoading = false;
				});
			},
			//关闭弹框
			close() {
				this.selectedListData = [];
				this.defaultCheckedKeys = [];
				this.$refs.popup.close();
			},
			//打开弹框
			open(parameter, title, size) {
				this.parameter = parameter;
				this.$refs.popup.open(title, size);
				this.$nextTick(() => {
					this.init();
				})
			},
			init() {
				this.popupLoading = true;
				this.selectedListData = this.parameter;
				if (!_.isEmpty(this.selectedListData)) {
					for (let item of this.selectedListData) {
						this.defaultCheckedKeys.push(item.id)
					}
					this.$refs.tree.setSelectedIds(this.defaultCheckedKeys);
				} else {
					this.selectedListData = [];
					this.defaultCheckedKeys = [];
				}
				this.list();
				this.popupLoading = false;
			},
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
