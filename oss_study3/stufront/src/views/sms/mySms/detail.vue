<template>
	<popup ref="popup" :loading="popupLoading" @closed="closed">
		<div slot="body">
			<el-form class="dataForm" ref="form" :model="formParameter" :inline="true" label-width="80px">
				
				<el-row>
					<el-col :span="12">
						<el-form-item label="发送者">
							<el-input v-model="formParameter.sname" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="发送时间">
							<el-input v-model="formParameter.stime" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				 <el-row>
				 	<el-col :span="12">
				 		<el-form-item label="内容级别">
				 			<el-select v-model="formParameter.level" placeholder="请选择" disabled>
				 				<el-option label="普通" :value="1"></el-option>
				 				<el-option label="一般" :value="2"></el-option>
				 				<el-option label="紧急" :value="3"></el-option>
				 			</el-select>
				 		</el-form-item>
				 	</el-col>
				 	<el-col :span="12">
				 		<el-form-item label="内容类型">
				 			<el-select v-model="formParameter.type" placeholder="请选择" disabled>
				 				<el-option label="公告" :value="1"></el-option>
				 				<el-option label="新闻" :value="2"></el-option>
				 				<el-option label="会议" :value="3"></el-option>
				 				<el-option label="其它" :value="3"></el-option>
				 			</el-select>
				 		</el-form-item>
				 	</el-col>
				 </el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="阅读状态">
							<el-select v-model="formParameter.rstate" placeholder="请选择" disabled>
								<el-option label="未读" :value="1"></el-option>
								<el-option label="已读" :value="2"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="标题">
							<el-input v-model="formParameter.title" autocomplete="off" disabled></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="内容">
							<!-- <el-input v-model="formParameter.content" autocomplete="off" disabled></el-input> -->
							<div v-html="formParameter.content"></div>
						</el-form-item>
					</el-col>
				</el-row>
				
				
			</el-form>
		</div>
		<div slot="footer">
			<el-button @click="close">取 消</el-button>
		</div>
	</popup>
</template>

<script>
	import popup from '@/components/popup/drawerPopup.vue';
	import api from '@/api/sms/mySms/mySms.js';
	export default {
		name: 'detail',
		components: {
			popup
		},
		data() {
			return {
				popupLoading: false,
				confirmLoading: false,
				parameter: {},
				formParameter: {},
			};
		},
		methods: {
			//打开弹框
			open(parameter, title, size) {
				this.parameter = parameter;
				this.$refs.popup.open(title, size);
				this.$nextTick(() => {
					this.init();
				})
			},
			//关闭弹框
			close() {
				this.$refs.popup.close();
			},
			closed() {
				this.$parent.list();
			},

			init() {
				this.popupLoading = true;
				//清除等待
				this.confirmLoading = false;
				//清空内容
				this.formParameter = {};
				if (this.parameter.id) {
					//编辑查询数据
					let parameter = {
						id: this.parameter.id
					};
					api.toRead(parameter, response => {
						this.formParameter = response.data;
						this.popupLoading = false;
					});
				}
			}
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/popup/popup.scss';
</style>
