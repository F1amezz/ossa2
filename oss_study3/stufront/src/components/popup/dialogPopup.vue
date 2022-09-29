<template>
	<el-dialog v-dialogDrag class="popup" :title="title" :append-to-body="appendToBody" :before-close="close"
		:visible="show" v-if="show" :width="size">
		<div class="dialog-body" v-loading="loading">
			<div class="dialog-body-main">
				<slot name="body"></slot>
			</div>
			<div class="dialog-body-footer">
				<slot name="footer"></slot>
			</div>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		name: 'dialogPopup',
		data() {
			return {
				title: '',
				show: false,
				size: "50%"
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
			}
		}
	};
</script>

<style></style>
