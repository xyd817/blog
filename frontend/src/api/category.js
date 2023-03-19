import request from '@/request';

export function listCategoryDetail() {
    return request({
        method:'get',
        url:'/categories/detail'
    })
}

export function getCategoryDetailById(id) {
    return request({
        url:`categories/detail/${id}`,
        method: 'get',
        param: id
    })
}

export function getAllCategorys() {
    return request({
        url: '/categories',
        method: 'get',
    })
}