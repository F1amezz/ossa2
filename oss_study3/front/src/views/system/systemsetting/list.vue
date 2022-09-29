<template>
	<div class="tab-body">
		<el-form ref="form" label-width="150px">
			<el-row>
				<el-col :span="12">
					<div style="font-size: 30px;font-weight: bold;margin: 30px 0 30px 150px;">网站设置</div>
					<el-form-item label="网站标题">
						<el-input v-model="systemSetting.title"></el-input>
					</el-form-item>
					<el-form-item label="系统名称">
						<el-input v-model="systemSetting.systemName"></el-input>
					</el-form-item>
					<el-form-item label="网站图标">
						<el-upload class="avatar-uploader" action="#" :show-file-list="false" :auto-upload="false" :on-change="changeImg">
							<img v-if="systemSetting.icon" :src="systemSetting.icon" class="avatar" style="width: 70px;height: 70px;" />
							<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
					</el-form-item>
					<el-form-item label="备案号">
						<el-input v-model="systemSetting.icp"></el-input>
					</el-form-item>
					<el-form-item label="底部内容">
						<el-input v-model="systemSetting.footer"></el-input>
					</el-form-item>
					<!-- 	<el-form-item :label="item.name" v-for="(item, index) in listData" v-if="item.type == 3">
						<el-upload v-if="item.dkey == 'icon'" class="avatar-uploader" action="#" :show-file-list="false" :auto-upload="false"
						 :on-change="changeImg">
							<img v-if="item.dvalue" :src="item.dvalue" class="avatar" style="width: 70px;height: 70px;" />
							<i v-else class="el-icon-plus avatar-uploader-icon"></i>
						</el-upload>
						<el-input v-else v-model="item.dvalue"></el-input>
					</el-form-item> -->
				</el-col>
				<el-col :span="12">
					<div style="font-size: 30px;font-weight: bold;margin:  30px 0 30px 150px;">邮箱设置</div>
					<el-form-item label="邮箱服务器">
						<el-input v-model="emailSetting.host"></el-input>
					</el-form-item>
					<el-form-item label="邮箱服务器端口">
						<el-input v-model="emailSetting.port"></el-input>
					</el-form-item>
					<el-form-item label="发送邮件">
						<el-input v-model="emailSetting.mailSender"></el-input>
					</el-form-item>
					<el-form-item label="邮箱服务用户">
						<el-input v-model="emailSetting.user"></el-input>
					</el-form-item>
					<el-form-item label="邮箱服务授权码">
						<el-input v-model="emailSetting.pass"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-form-item>
				<el-button type="primary" :loading="confirmLoading" @click="update">保存</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import api from '@/api/system/systemsetting/systemsetting.js';
	export default {
		name: 'systemsetting',
		components: {},
		data() {
			return {
				confirmLoading: false,
				searchParams: {},
				listLoading: false,
				systemSetting: {},
				emailSetting: {},
				page: new this.$constant.pageObj(),
			};
		},
		methods: {
			//获取数据
			list() {
				this.listLoading = true;
				let params = this.$utils.merger(this.searchParams, this.page);
				api.list(params, response => {
					this.systemSetting = response.data.systemSetting;
					this.emailSetting = response.data.emailSetting;
					this.listLoading = false;
				})
			},
			update() {
				this.confirmLoading = true;
				let parameter = this.$utils.merger(this.systemSetting, this.emailSetting);
				//编辑
				api.update(
					parameter,
					response => {
						this.$utils.msg.success(response.msg);
						//清空系统设置立马生效
						this.$caches.systemSetting.remove();
						this.confirmLoading = false;
					},
					response => {
						this.$utils.msg.warning(response.msg);
						this.confirmLoading = false;
					}
				);
			}, //选择图片后
			changeImg(file, fileList) {
				this.getBase64(file.raw).then(res => {
					this.systemSetting.icon = res;
				});
			},
			getBase64(file) {
				return new Promise(function(resolve, reject) {
					let reader = new FileReader();
					let imgResult = '';
					reader.readAsDataURL(file);
					reader.onload = function() {
						imgResult = reader.result;
					};
					reader.onerror = function(error) {
						reject(error);
					};
					reader.onloadend = function() {
						resolve(imgResult);
					};
				});
			},
		},
		mounted() {
			this.list();
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
</style>
