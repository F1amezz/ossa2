<template>
	<div class="tab-body">
		<div class="errFrame">
			<img src="~assets/gif/404.gif" class="errFrame-img" />
			<div class="errFrame-text">抱歉，没有找到页面</div>
		</div>
	</div>
</template>

<script></script>

<style lang="scss">
	@import '~common/custom/css/common.scss';

	.errFrame {
		position: relative;
		margin: auto;

		.errFrame-img {
			width: 50%;
			height: 80%;
			display: block;
			position: relative;
			left: 0;
			right: 0;
			top: 0;
			bottom: 0;
			margin: auto;
		}

		.errFrame-text {
			text-align: center;
			color: darkgrey;
		}
	}
</style>
