<template>
    <div v-title data-title="德哥blog">
        <el-container >
            <el-main class="me_articles">
                <ArticleScrollPage></ArticleScrollPage>
            </el-main>
            <el-aside>
                <CardMe></CardMe>
                <card-tag :tags="hotTags"></card-tag>
                <card-article cardHeader="最热文章" :articles="hotArticles"></card-article>
                <card-archive cardHeader="文章归档" :archives="archives"></card-archive>
                <card-article cardHeader="最新文章" :articles="newArticles"></card-article>
            </el-aside>
        </el-container>
    </div>
</template>

<script>

import ArticleScrollPage from '@/components/common/ArticleScrollPage.vue';
import CardMe from '@/components/card/CardMe.vue';
import CardTag from '@/components/card/CardTag.vue';
import CardArticle from '@/components/card/CardArticle.vue';
import CardArchive from '@/components/card/CardArchive.vue';
import {getHotTags} from '@/api/tag.js';
import {getHotArticles, getNewArticles, getArchives} from '../api/article';
export default {
    name:"Index",
    data() {
        return {
            hotTags:[],
            hotArticles:[],
            newArticles:[],
            archives:[]
        }
    },
    components: {
        ArticleScrollPage,
        CardMe,
        'card-tag':CardTag,
        'card-article': CardArticle,
        'card-archive': CardArchive
    },
    created() {
        this.getHotTags();
        this.getHotArticles();
        this.getNewArticles();
        this.getArchives();
    },
    methods: {
        getHotTags() {
            getHotTags().then(
                (res) => {
                    if (res.data.success) {
                        this.hotTags = res.data.data;
                        
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }
            ).catch(
                (err) => {
                    this.$message.error("系统异常");
                }
            ).finally(
                () => {

                }
            )
        },
        getHotArticles(){
            getHotArticles().then(
                (res) => {
                    
                    this.hotArticles = res.data.data;
                }
            ).catch(
                (err) => {
                    this.$message.error("系统异常");
                }
            ).finally(
                () => {}
            )
        },
        getNewArticles(){
            getNewArticles().then(
                (res) => {
                    this.newArticles = res.data.data;
                }
            ).catch(
                (err) => {
                    this.$message.error("系统异常");
                }
            ).finally(
                () => {}
            )
        },
        getArchives() {
            getArchives().then(
                (res) => {
                    this.archives = res.data.data;
                }
            ).catch(
                (err) => {
                    this.$message.error("系统异常");
                }
            ).finally(
                () => {}
            )
        }
    }
}
</script>

<style scoped>
.el-container {
    width:960px;
}
.el-aside {
    margin-left: 20px;
    width:260px;
}
.el-main {
    padding:0px;
    line-height: 16px;
}
.el-card {
    border-radius: 0px;
}
.el-card:not(:first-child) {
    margin-top: 20px;
}

</style>