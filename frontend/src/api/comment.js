import request from '@/request';

export function publishComment(comment, token) {
    return request({
        headers: {'Authorization': token},
        method: 'post',
        url : '/comments/create/change',
        data: comment
    })
}

export function getCommentsByArticle(id) {
    return request({
        url: `/comments/article/${id}`,
        method: 'get'
    })
}
