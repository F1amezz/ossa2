<template>
	<el-table-column v-bind="$attrs" v-on="$listeners" :class-name="ignored?'ignored':''" v-if="$scopedSlots.default&&isShow">
		<template slot-scope="scope">
			<slot :row="scope.row"></slot>
		</template>
	</el-table-column>
	<el-table-column v-bind="$attrs" v-on="$listeners" :class-name="ignored?'ignored':''" v-else-if="!$scopedSlots.default&&isShow">
	</el-table-column>
</template>

<script>
	export default {
		name: 'qlhTableColumn',
		components: {},
		inheritAttrs: false,
		data() {
			return {
				isShow: true
			};
		},
		props: {
			show: {
				type: Boolean,
				default () {
					return true
				}
			},
			editable: {
				type: Boolean,
				default () {
					return true
				}
			},
			ignored: {
				type: Boolean,
				default () {
					return false
				}
			}
		},
		methods: {
			changeShow() {
				this.isShow = false;
				var showedCol = this.$parent.$parent.getAllShowedCol();
				//如果列不可编辑，则必定显示
				if (!this.editable) {
					this.isShow = true;
					return;
				}
				for (var item of showedCol) {
					if (this.$attrs.prop != null) {
						if (this.$attrs.prop == item.value) {
							this.isShow = true;
						}
					} else {
						if (this.$attrs.label == item.value) {
							this.isShow = true;
						}
					}
				}
			},
			init() {
				if (this.$parent.$attrs.id) {
					return;
				}
				var editableCol = this.$parent.$parent.getAllEditableCol();
				var showedCol = this.$parent.$parent.getAllShowedCol();
				if (this.editable) {
					var obj = {
						label: this.$attrs.label,
						value: this.$attrs.prop != null ? this.$attrs.prop : this.$attrs.label,
					}
					editableCol.push(obj);
					if (this.show) {
						showedCol.push(obj);
					}
				}
				this.changeShow();
			}
		},
		mounted() {
			this.init();
		},
		watch: {

		}
	};
</script>

<style>
</style>
