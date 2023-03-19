import axios from 'axios';
import store from '@/store';

const service = axios.create({
    baseURL:"https://degeblog.cn/api",
    // baseURL:"http://localhost:8888",
    timeout:3000
});

service.interceptors.request.use(function (config) {
    if (store.state.token) {
        config.headers['Authorization'] = localStorage.token;
    }
    return config;
}, function (error) {
    return  Promise.reject(error);
})

service.interceptors.response.use(function (response) {
    const res = response.data;
    if (res.code !== 200) {
        // 未登录
        if (res.code === 90002) {
            Message({
                type: 'warning',
                showClose: true,
                message: '未登录或登录超时，请重新登录哦'
                })
            return Promise.reject('error');
        }
    }
    return response;
}, function (error) {
    return Promise.reject(error);
})

export default service;


