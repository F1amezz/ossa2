const webpack = require('webpack')

module.exports = {
	configureWebpack: {
		plugins: [
			new webpack.ProvidePlugin({
				$: "jquery",
				jQuery: "jquery",
				"windows.jQuery": "jquery"
			})
		],
		resolve: {
			alias: {
				'assets': '@/assets',
				'common': '@/common',
				'store': '@/store',
				'components': '@/components',
				'directives': '@/directives',
				'views': '@/views',
				'util': '@/util',
				'router': '@/router',
				'vue$': 'vue/dist/vue.esm.js'
			}
		}
	},
	publicPath: '/',

	devServer: {
		host: '0.0.0.0',
		port: 8099,
		https: false,
		hotOnly: false,
		disableHostCheck: true,
		before: app => {}
	},

}
