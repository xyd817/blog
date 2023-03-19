import request from '@/request';

export  function getHotTags() {
    return request({
        url:'/tags/hot',
        method:'get'
    })
}

export function listTagsDetail() {
    return request({
        url:'/tags/detail',
        method:'get'
    })
}
export function getTagDetailById(id) {
    return request({
        url:`tags/detail/${id}`,
        method: 'get',
        param: id
    })
}

export function getAllTags() {
    return request({
        url: '/tags',
        method: 'get',
    })
}