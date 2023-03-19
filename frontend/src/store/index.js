import Vuex from 'vuex'
import Vue from 'vue'
import {register, getUserInfo, logout, login} from '@/api/login'
import {getToken, setToken, removeToken} from '@/request/token' 
Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        id: '',
        account: '',
        name: '',
        avatar: '',
        token: localStorage.token,
    },
    mutations: {
        SET_ID: (state, id) => {
            state.id = id;
        },
        SET_ACCOUNT: (state, account) => {
            state.account = account;
        },
        SET_NAME: (state, name) => {
            state.name = name;
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar;
        },
        SET_TOKEN: (state, token) => {
            state.token = token;
        }
    },
    actions: {
        login({commit}, user) {
            return new Promise((resolve, reject) => {
                login(user).then(res => {
                    if(res.data.success){
                        commit('SET_TOKEN', res.data.data)
                        localStorage.token=res.data.data;
                        resolve()
                    }else{
                        reject(res.data.msg)
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        },
        logout({commit, state}) {
            return new Promise((resolve, reject) => {
                logout(state.token).then(
                    res => {
                        console.log("******************************");
                        console.log(res);
                        if (res.data.success) {
                            commit('SET_ID', '');
                            commit('SET_ACCOUNT', '');
                            commit('SET_NAME', '');
                            commit('SET_AVATAR', '');
                            removeToken();
                            resolve(res);
                        }
                    }
                ).catch(
                    err => {
                        reject(err);
                    }
                )
            })
        },
        register({commit}, user) {
            return new Promise((resolve, reject) => {
                register(user).then(
                    res => {
                        if (res.data.success) {
                            commit('SET_TOKEN', res.data.data);
                            localStorage.token = res.data.data;
                            resolve();
                        } else {
                            reject(res.data.msg);
                        }
                    }
                ).catch(err => {
                    reject(err);
                })
            })
        },
        getUserInfo({commit, state}) {
            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(
                    res => {
                        if (res.data.success) {
                            commit('SET_ID', res.data.data.id);
                            commit('SET_ACCOUNT', res.data.data.account);
                            commit('SET_NAME', res.data.data.nickname);
                            commit('SET_AVATAR', res.data.data.avatar);
                            resolve(res);
                        } else {
                            commit('SET_ID', '');
                            commit('SET_ACCOUNT', '');
                            commit('SET_NAME', '');
                            commit('SET_AVATAR', '');
                            removeToken();
                            resolve(res);
                        }
                    }
                ).catch(
                    err => {
                        commit('SET_ID', '');
                        commit('SET_ACCOUNT', '');
                        commit('SET_NAME', '');
                        commit('SET_AVATAR', '');
                        removeToken();
                        reject(err);
                    }
                )
            })
        }
    }
})

