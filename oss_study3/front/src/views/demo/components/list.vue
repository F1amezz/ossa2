<template>
	<div class="popup ">
		<div slot="body">
			<el-form class="dataForm" ref="form" :inline="true" label-width="80px" >
				<el-row>
					<el-col :span="12">
						<el-form-item label="用户">
							<select-user-table-dialog v-model="selectuser">
							</select-user-table-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{selectuser}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="用户(多选)">
							<select-user-table-dialog multiple  v-model="selectusers" autocomplete="off">
							</select-user-table-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{selectusers}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="地区">
							<arae-cascader v-model="areas" autocomplete="off">
							</arae-cascader>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{areas}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="角色">
							<select-role-tree-dialog v-model="selectRoleTreeDialog" autocomplete="off">
							</select-role-tree-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{selectRoleTreeDialog}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="部门">
							<select-department-tree-dialog multiple v-model="selectDepartmentTreeDialog" autocomplete="off">
							</select-department-tree-dialog>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{selectDepartmentTreeDialog}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="角色树">
							<role-tree v-model="roleTree" autocomplete="off">
							</role-tree>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{roleTree}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="部门树">
							<department-tree v-model="departmentTree" autocomplete="off">
							</department-tree>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{departmentTree}}</el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="12">
						<el-form-item label="字典选项">
							 <select-option-dictionary v-model="selectOptionDictionary" dkey="sex"></select-option-dictionary>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-tag type="success">{{selectOptionDictionary}}</el-tag>
					</el-col>
				</el-row>
			</el-form>
		</div>

	</div>
</template>

<script>
	import selectUserTableDialog from '@/components/biz/selectUserTableDialog/selectUserTableDialog.vue';
	import araeCascader from '@/components/biz/araeCascader/araeCascader.vue';
	import selectRoleTreeDialog from '@/components/biz/selectRoleTreeDialog/selectRoleTreeDialog.vue';
	import selectDepartmentTreeDialog from '@/components/biz/selectDepartmentTreeDialog/selectDepartmentTreeDialog.vue';
	import roleTree from '@/components/biz/roleTree/roleTree.vue';
	import departmentTree from '@/components/biz/departmentTree/departmentTree.vue';
	import selectOptionDictionary from '@/components/biz/selectOptionDictionary/selectOptionDictionary.vue';
	export default {
		components: {
			selectUserTableDialog,
			araeCascader,
			selectRoleTreeDialog,
			selectDepartmentTreeDialog,
			roleTree,
			departmentTree,
			selectOptionDictionary
		},
		data() {
			return {
				selectuser: "",
				selectusers: "",
				areas: "",
				selectRoleTreeDialog: '',
				selectDepartmentTreeDialog: '',
				roleTree: '',
				departmentTree: '',
				selectOptionDictionary:''
			};
		},

		methods: {

		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
