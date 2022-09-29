<template>
	<div class="tab-header">
		<div class="header-left">
			<el-tabs v-model="currentId" type="card" @tab-remove="removeOneTab">
				<el-tab-pane v-for="(item, index) in tabsData" :label="item.name" :key="item.id" :name="item.id" :url="item.url"
				 :closable="item.closable"></el-tab-pane>
			</el-tabs>
		</div>
		<div class="header-right">
			<tab-options @optionClick="optionClick"></tab-options>
		</div>
	</div>
</template>

<script>
	import tabOptions from '../tabOptions/tabOptions.vue';

	export default {
		name: 'tab',
		components: {
			tabOptions
		},
		data() {
			return {
				currentId: this.id,
				tabsData: this.items
			};
		},
		props: {
			items: {
				type: Array,
				default () {
					return [];
				}
			},
			tabsLimit: {
				type: Number,
				default () {
					return 20;
				}
			}
		},
		methods: {
			//设置当前打开的tab
			setCurrentTabId(currentId) {
				this.currentId = currentId;
			},
			//获取打开的tab
			getCurrentTabId() {
				return this.currentId;
			},
			//根据id获取tab
			getTab(id) {
				let tabs = this.getTabs();
				let index = _.findIndex(tabs, function(o) {
					return o.id == id;
				});
				return index > -1 ? tabs[index] : null;
			},
			//获取全部tab
			getTabs() {
				return this.tabsData;
			},
			//添加一个tab
			addTab(item) {
				//遍历tabs
				let tabs = this.getTabs();
				//tabs是否已有tab
				let index = _.findIndex(tabs, function(o) {
					return o.id == item.id;
				});

				if (index <= -1) {
					//没有找到
					if (this.getTabs().length >= this.tabsLimit) {
						//判断Tabs长度超过长度
						return false;
					}
					//没有超过添加一个内容
					this.tabsData.push(item);
				}
				//修改当前页
				this.setCurrentTabId(item.id);
				return true;
			},
			//删除指定tab
			removeOneTab(id) {
				let tabs = this.getTabs();
				if (this.currentId == id) {
					this.setCurrentTabId('');
					let index = _.findIndex(tabs, function(o) {
						return o.id == id;
					});
					let nextTab = tabs[index + 1] || tabs[index - 1];
					if (nextTab) {
						this.setCurrentTabId(nextTab.id);
					}
				}
				//删除tab逻辑
				let index = _.findIndex(tabs, function(o) {
					return o.id == id && o.closable
				});
				if (index > -1) {
					this.tabsData.splice(index, 1);
				}
				this.$emit('removeTab', id);
			},
			//删除其他tab
			removeOtherTabs() {
				let activeTab = this.getCurrentTabId();
				let tabs = Array.from(this.getTabs());
				tabs.forEach((tab, index) => {
					if (tab.id != activeTab) {
						this.removeOneTab(tab.id);
					}
				});
			},
			//删除全部tab
			removeAllTabs() {
				let activeTab = this.getCurrentTabId();
				this.removeOtherTabs();
				this.removeOneTab(activeTab);
			},
			//删除左边tab
			removeLeftTabs() {
				let activeTab = this.getCurrentTabId();
				let tabs = Array.from(this.getTabs());
				//找到当前节点位置
				let index = _.findIndex(tabs, function(o) {
					return o.id == activeTab;
				});
				for (let i = 0; i < index; i++) {
					this.removeOneTab(tabs[i].id);
				}
			},
			//删除右边tab
			removeRigthTab() {
				let activeTab = this.getCurrentTabId();
				let tabs = Array.from(this.getTabs());
				//找到当前节点位置
				let index = _.findIndex(tabs, function(o) {
					return o.id == activeTab;
				});
				for (let i = index + 1; i < tabs.length; i++) {
					this.removeOneTab(tabs[i].id);
				}
			},
			//右侧选项点击事件
			optionClick(command) {
				if (command == 'removeOtherTabs') {
					this.removeOtherTabs();
				} else if (command == 'removeAllTabs') {
					this.removeAllTabs();
				} else if (command == 'refreshCurrentTab') {
					// window.location.reload();
					this.$emit('refresh');
				} else if (command == 'removeLeftTabs') {
					this.removeLeftTabs();
				} else if (command == 'removeRigthTab') {
					this.removeRigthTab();
				}
			},
			init() {
				this.tabsData = this.items;
				if (!this.tabsData) {
					this.tabsData = new Array();
				}
			},
			mounted() {
				this.init();
			}
		},
		watch: {
			items(val) {
				this.init();
			},
			currentId(val) {
				this.$emit('tabChange', val);
			}
		},
		mounted() {
			this.init();
		}
	};
</script>

<style lang="scss">
	.tab-header {
		display: flex;
		margin-bottom: 10px;

		.header-left {
			width: 95%;
		}

		.header-right {
			flex: 1;
		}

	}
</style>
