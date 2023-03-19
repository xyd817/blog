import request from '@/request';

export function getArticles(page, query) {
    return request({
        method:'post',
        url:'/articles/findArticlePage',
        data:{
            page: page.page,
            pageSize: page.pageSize,
            year: query.year,
            month: query.month,
            tagId: query.tagId,
            categoryId: query.categoryId
        }
    })
}

export function getHotArticles() {
    return request({
        method:'post',
        url:'/articles/hot'
    })
}
export function getNewArticles() {
    return request({
        method:'post',
        url:'/articles/new'
    })
}

export function getArchives() {
    return request({
        method: 'post',
        url:'/articles/listArchives'
    })
}

export function viewArticle(id) {
    return request({
        url: `/articles/view/${id}`,
        method: 'post'
    })
}

export function listArchives() {
    return request({
        url: '/articles/listArchives',
        method: 'post'
    })
}

export function publishArticle(article,token) {
    return request({
        headers: {'Authorization': token},
        url: '/articles/publish',
        method: 'post',
        data: article
    })
}

export function getArticleById(id) {
    return request({
        url: `/articles/view/${id}`,
        method: 'post'
    })
}

export function updateArticle(articleUpdate, token) {
    return request({
        headers: {'Authorization': token},
        url: '/articles/updateArticle',
        method: 'post',
        data: articleUpdate
    })
}
