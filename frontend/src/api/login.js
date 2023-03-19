import request from '@/request';

export function getUserInfo(token) {
	return request({
        headers: {'Authorization': token},
		method: 'get',
		url: '/users/currentUser'
	})
}

export function register(user) {
    return request({
        method: 'post',
        url: '/register',
        data: user
    })
}

export function logout(token) {
    return request({
        method: 'get',
        url: '/logout',
        headers: {'Authorization': token}
    })
}

export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data:data
    })
}
