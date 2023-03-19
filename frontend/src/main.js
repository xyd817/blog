// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import {formatTime} from './utils/time';
import axios from 'axios';
import VueAxios from 'vue-axios';
import store from './store';
// 自定义指令
Vue.directive('title', function(el, bingding) {
  document.title = el.dataset.title;
} )
// 格式化时间
Vue.filter('format', formatTime);
Vue.use(VueAxios, axios);

Vue.use(ElementUI)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
