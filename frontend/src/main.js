import { createApp } from 'vue'
import App from './App.vue'
import '@nutui/touch-emulator'
import router from '../router/index.js'; // 导入路由实例
const app = createApp(App)
import { Toast } from '@nutui/nutui'
app.use(Toast)
app.use(router).mount('#app')
// app.use((req, res, next) => {
//     res.setHeader('Access-Control-Allow-Origin', 'http://119.8.11.44:8081');
//     next();
// });

window.addEventListener('unhandledrejection', event => {
    // 阻止默认的处理方式（即将错误信息输出到控制台）
    event.preventDefault();

    // 打印错误详情
    console.error('Unhandled promise rejection:', event.reason);
});

app.config.errorHandler = function(err) {
    console.log("global", err);
};

window.onerror = function () {
    return true;
};