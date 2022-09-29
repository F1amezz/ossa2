<template>
	<el-dropdown @command="handleCommand">
		<el-button>选项</el-button>
		<el-dropdown-menu slot="dropdown">
			<el-dropdown-item  icon="el-icon-refresh" command="refreshCurrentTab">刷新</el-dropdown-item>
			<el-dropdown-item  icon="el-icon-back" command="removeLeftTabs">关闭左边</el-dropdown-item>
			<el-dropdown-item  icon="el-icon-right" command="removeRigthTab">关闭右边</el-dropdown-item>
			<el-dropdown-item  icon="el-icon-circle-close" command="removeOtherTabs">关闭其他</el-dropdown-item>
			<el-dropdown-item  icon="el-icon-error" command="removeAllTabs">关闭全部</el-dropdown-item>
		</el-dropdown-menu>
	</el-dropdown>
</template>

<script>
export default {
	name: 'selectionBtn',
	data() {
		return {};
	},

	methods: {
		//右侧选项操作
		handleCommand(command) {
			this.$emit('optionClick', command);
		}
	}
};
</script>

<style></style>
