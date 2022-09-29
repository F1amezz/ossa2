<template>
	<div class="tab-body">
		<el-row>
			<el-col :span="24">
				<el-card class="box-card" :body-style="{height:'80%'}">
					<div slot="header" class="clearfix"><span>服务器信息</span></div>
					<div style="height: 100%;display: flex;" class="osInfo">
						<div>
							<div id="cpu"></div>
							<div>
								<div id="cpuUse"></div>
								<div>cpu使用情况</div>
							</div>
						</div>
						<div>
							<div id="OsMemory"></div>
							<div>
								<div id="OsMemUse"></div>
								<div>系统内存使用情况</div>
							</div>
						</div>
						<div>
							<div id="JvmMemory"></div>
							<div>
								<div id="JvmMemUse"></div>
								<div>jvm内存使用情况</div>
							</div>
						</div>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="12">
				<el-card class="box-card" :body-style="{height:'80%',overflow:'auto'}">
					<div slot="header" class="clearfix"><span>操作系统信息</span></div>
					<div style="height:95%;">
						<table class="el-table el-table--fit el-table--striped el-table--border el-table--enable-row-transition">
							<tbody>
								<tr>
									<td>操作系统名</td>
									<td id="systemName">{{dashboardVo.systemName}}</td>
								</tr>
								<tr>
									<td>操作系统架构</td>
									<td id="systemArch">{{dashboardVo.systemArch}}</td>
								</tr>
								<tr>
									<td>处理器</td>
									<td id="cpuName">{{dashboardVo.cpuName}}</td>
								</tr>
								<tr>
									<td>处理器物理核心</td>
									<td id="cpuPhysicalCount">{{dashboardVo.cpuPhysicalCount}}</td>
								</tr>
								<tr>
									<td>处理器逻辑核心</td>
									<td id="cpuPhysicalCount">{{dashboardVo.cpuLogicalCount}}</td>
								</tr>
								<tr>
									<td>物理内存</td>
									<td id="memoryTotal">{{(dashboardVo.memoryTotal/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
								<tr>
									<td>已使用的物理内存</td>
									<td id="memoryUsed">{{(dashboardVo.memoryUsed/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
								<tr>
									<td>空闲的物理内存</td>
									<td id="memoryIdle">{{(dashboardVo.memoryIdle/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card class="box-card" :body-style="{height:'80%',overflow:'auto'}">
					<div slot="header" class="clearfix"><span>jvm信息</span></div>
					<div style="height:95%;">
						<table class="el-table el-table--fit el-table--striped el-table--border el-table--enable-row-transition">
							<tbody>
								<tr>
									<td>java运行版本</td>
									<td id="javaVersion">{{dashboardVo.javaVersion}}</td>
								</tr>
								<tr>
									<td>java位数</td>
									<td id="javaArch">{{dashboardVo.javaArch}}</td>
								</tr>
								<tr>
									<td>jvm运行版本</td>
									<td id="jvmVersion">{{dashboardVo.jvmVersion}}</td>
								</tr>
								<tr>
									<td>jvm使用Cpu数量</td>
									<td id="jvmCpuCount">{{dashboardVo.jvmCpuCount}}</td>
								</tr>
								<tr>
									<td>jvm初始的总内存</td>
									<td id="jvmMemoryInit">{{(dashboardVo.jvmMemoryInit/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
								<tr>
									<td>jvm最大可用内存</td>
									<td id="jvmMemoryTotal">{{(dashboardVo.jvmMemoryTotal/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
								<tr>
									<td>jvm已使用的内存</td>
									<td id="jvmMemoryUsed">{{(dashboardVo.jvmMemoryUsed/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
								<tr>
									<td>jvm空闲的内存</td>
									<td id="jvmMemoryIdle">{{(dashboardVo.jvmMemoryIdle/1024/1024/1024).toFixed(2)+" GB"}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>
<script>
	import api from '@/api/monitor/dashboard/dashboard.js';
	import 'echarts/lib/chart/bar';
	export default {
		name: 'systeminfo',
		data() {
			return {
				dashboardVo: {},
				cpuInfo: [{
						value: 0,
						name: '已使用'
					},
					{
						value: 0,
						name: '未使用'
					}
				],
				osMemory: [{
						value: 0,
						name: '已使用'
					},
					{
						value: 0,
						name: '未使用'
					}
				],
				jvmInfo: [{
						value: 0,
						name: '已使用'
					},
					{
						value: 0,
						name: '未使用'
					}
				],
				//定时器对象
				t: null,
			};
		},
		methods: {
			//获取数据
			list() {

				// this.makePiePicture('OsMemory', '系统内存使用情况', 1);
				// this.makePiePicture('JvmMemory', 'jvm内存使用情况', 1);
				api.getInfo(null, response => {
					this.dashboardVo = response.data;
					this.makePiePicture('cpu', 'cpu使用情况', [{
							value: this.dashboardVo.cpuUsed,
							name: '使用'
						},
						{
							value: this.dashboardVo.cpuIdle,
							name: '空闲'
						}
					]);
					this.makePiePicture('OsMemory', '系统内存使用情况', [{
							value: this.dashboardVo.memoryUsed,
							name: '使用'
						},
						{
							value: this.dashboardVo.memoryIdle,
							name: '空闲'
						}
					]);
					this.makePiePicture('JvmMemory', 'jvm内存使用情况', [{
							value: this.dashboardVo.jvmMemoryUsed,
							name: '使用'
						},
						{
							value: this.dashboardVo.jvmMemoryIdle,
							name: '空闲'
						}
					]);
				});
			},
			makePiePicture(dom, title, data) {
				// 基于准备好的dom，初始化echarts实例
				var myChart = this.$echarts.init(document.getElementById(dom));
				var title = title ? title : '';
				var used = ((data[0].value / (data[0].value + data[1].value)) * 100).toFixed(2);
				//初始绿色
				var color = '#20a53a';
				if (used > 50) {
					color = '#f7f145';
				}
				if (used > 75) {
					color = '#bd3333';
				}
				// 指定图表的配置项和数据
				var option = {
					//鼠标悬停显示信息框
					title: [{
						text: '{name|' + data[0].name + '}\n ' + used + '%',
						top: 'center',
						left: 'center',
						textStyle: {
							rich: {
								name: {
									fontSize: 14,
									fontWeight: 'normal',
									color: '#666666',
									padding: [10, 0]
								}
							}
						}
					}],
					tooltip: {
						trigger: 'item',
						formatter: '{a} <br/>{b}:  {d}%'
					},
					//饼状图
					series: [{
						name: title,
						type: 'pie',
						radius: ['80%', '90%'],
						//数据
						data: data,
						//默认外部显示
						label: {
							show: false,
							position: 'center',
							fontSize: '24',
							formatter: '{b}\n{d}%'
						},
						//默认外部线显示
						labelLine: {
							show: false
						}
					}],
					//扇形颜色
					color: [color, '#cccccc']
				};
				myChart.setOption(option);
			}
		},
		mounted() {

			this.list();
			this.t = setInterval(this.list, 3000);
		},
		beforeDestroy() {
			// 组件销毁前调用
			clearInterval(this.t);
		}
	};
</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
	@import '~common/custom/css/systeminfo/list.scss';
</style>
