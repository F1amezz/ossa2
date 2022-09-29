<template>
	<el-drawer :wrapperClosable="false" class="popup" :title="title" :append-to-body="appendToBody"
		:before-close="close" :visible="show" direction="rtl" ref="drawer" :size="size">
		<div class="drawer-body" v-loading="loading">
			<div class="drawer-body-main">
				<slot name="body"></slot>
			</div>
			<div class="drawer-body-footer">
				<slot name="footer"></slot>
			</div>
		</div>
	</el-drawer>
</template>

<script>
	export default {
		name: 'drawerPopup',
		data() {
			return {
				title: '',
				show: false,
				size: "60%"
			};
		},
		props: {
			appendToBody: {
				type: Boolean,
				default () {
					return false;
				}
			},
			loading: {
				type: Boolean,
				default () {
					return false;
				}
			}
		},
		methods: {
			//打开弹框
			open(title, size) {
				if (title) {
					this.title = title;
				}
				if (size) {
					this.size = size;
				}
				this.show = true;
			},
			//关闭弹框
			close() {
				//关闭前回调
				this.$emit('beforeClose');
				this.show = false;
				//关闭后回调
				this.$emit('closed');

				// 给“学习管理=>每日总结”的“批量评分”的重置操作
				this.$parent.isBatch=false;
				this.$parent.idArrIndex=0;
				this.$parent.percentage=0;
			}
		}
	};
</script>

<style></style>
