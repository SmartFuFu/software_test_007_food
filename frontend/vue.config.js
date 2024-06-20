const { defineConfig } = require('@vue/cli-service')
const Components = require('unplugin-vue-components/webpack')
const NutUIResolver = require('@nutui/auto-import-resolver')
const { plugins } = require("eslint-plugin-vue/lib/configs/base");
// import globalData from "../../global.js"
const fs=require('fs')
// const BaseUrl = "http://119.3.153.217:8002"
// const BaseUrl = "http://124.70.156.23:8002"
const BaseUrl = "http://119.8.2.234:8082"
// const BaseUrl="http://localhost:8002"
// const BaseUrl = "http://100.80.74.33:8002"

// const BaseUrl = "http://127.0.0.1:4523/m1/4090306-0-default/api"
const imageURL = "https://food-bank.obs.cn-east-3.myhuaweicloud.com/"

module.exports = defineConfig({
  transpileDependencies: true,
  // configureWebpack:{
  //   plugins: [
  //     // 开启 unplugin 插件，自动引入 NutUI 组件
  //     Components({
  //       resolvers: [NutUIResolver()],
  //     })
  //   ],
  //   externals: {
  //     'BaiduMap': "BMapGL"
  //   }
  // },
  configureWebpack: config => {
    config.module.rules.push({
      test: /\.worker.js$/,
      use: {
        loader: 'worker-loader',
        options: { inline: true, name: 'workerName.[hash].js' }
      }
    })
    const plugin = [
      // 开启 unplugin 插件，自动引入 NutUI 组件
      Components({
        resolvers: [NutUIResolver()],
      })
    ];
    config.plugins.push(...plugin);
    config["externals"] = {
      'BaiduMap': "BMapGL"
    }
  },
  devServer: {
    port:80,
    // port:443,
    client: { //加上这个就好了
      overlay: false,
    },
    proxy: {
      '/api': {
        target: BaseUrl,
        changeOrigin: true,
      },
      "/gpt": {
        target: "http://119.8.11.44:8081",
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/gpt": "",
        },
      },
      "/alipay": {
        target: "http://124.70.156.23:3000",
        changeOrigin: true,ws: true,
        pathRewrite: {
          "^/alipay": "/",
        },
      },
    },
    // https:{
    //   key:fs.readFileSync('./server.key'),
    //   cert:fs.readFileSync('./server.cert'),
    // }
  },
  parallel:false
})