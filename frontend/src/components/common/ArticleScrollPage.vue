<template>
    <scroll-page :loading="loading" :no-data="noData" :offset="offset" @load="load">
        <article-item v-for="article in articles" :key="article.id" v-bind="article">
        </article-item>
    </scroll-page>
</template>

<script>

import ArticleItem from "../article/ArticleItem.vue";
import ScrollPage from "../scrollpage/ScrollPage.vue";
import {getArticles} from "@/api/article";
export default {
    name:"ArticleScrollPage",
    props: {
        offset: {
            type:Number,
            default: 100
        },
        query: {
            type: Object,
            default() {
                return {};
            }
        },
        page: {
            type: Object,
            default() {
                return {};
            }
        }
    },
    data() {
        return {
            loading: false,
            noData: false,
            // 分页信息
            innerPage: {
                pageSize: 5,
                page:1,
            },
            // 文章列表
            articles:[]
        }
    },
    watch: {
        'query': {
            handler() {
                
                this.noData = false;
                this.articles = [];
                this.innerPage.page = 1;
                this.getArticles();
            },
            deep:true
        },
        'page': {
            handler() {
                this.noData = false;
                this.articles = [];
                this.innerPage = this.page;
                this.getArticles();
            },
            deep: true
        }
    },
    created() {
        this.getArticles();
    },
    methods: {
        load() {
            //alert("触发分页");
            this.getArticles();
        },
        getArticles() {
            this.loading = true;
            getArticles(this.innerPage, this.query).then(
                res => {
                    if (res.data.success) {
                        if (res.data.data && res.data.data.length > 0) {
                            this.innerPage.page += 1;
                            this.articles = this.articles.concat(res.data.data);
                        } else {
                            this.noData = true;
                        }
                        
                    } else {
                        this.$message({
                            type:"error",
                            message: res.data.data,
                            showClose:true
                        })
                    }
                }
            ).catch(err => {
                this.$message({
                    type:"error",
                    message:"文章加载失败！",
                    showClose:true
                })
            }).finally(
                () => {
                    this.loading = false;
                }
            )
        
        }
    },
    components: {
        'article-item': ArticleItem,
        'scroll-page': ScrollPage
    }
}
</script>

<style scoped>
.el-card {
    border-radius: 0;
}

.el-card:not(:first-child) {
    margin-top: 10px;

}
</style>