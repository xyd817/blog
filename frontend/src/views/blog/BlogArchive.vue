<template>
    <div v-title :data-title = 'title'>
        <el-container>
            <el-aside class="me-area">
                <ul class="me-month-list">
                    <li v-for = "a in archives" :key="a.year + a.month" class="me-month-item">
                        <el-badge :value="a.count">
                            <el-button @click="changeArchive(a.year, a.month)" size="small">
                                {{a.year + '年' + a.month + '月'}}
                            </el-button>
                        </el-badge>
                    </li>
                </ul>
            </el-aside>
            <el-main class="me-articles">
                <div class="me-month-title">
                    {{currentAchive}}
                </div>
                <article-scroll-page v-bind="article" ref="articlescrollpage"></article-scroll-page>
            </el-main>
        </el-container>
        
    </div>
</template>

<script>
import ArticleScrollPage from '@/components/common/ArticleScrollPage.vue';
import {listArchives} from '@/api/article';
export default {
    name: 'BlogArchive',
    data() {
        return {
            article: {
                query: {
                    year: this.$route.params.year,
                    month: this.$route.params.month
                }
            },
            archives: []
        }
    },
    computed: {
        currentAchive() {
            if (this.article.query.year && this.article.query.month) {
                return `${this.article.query.year}年${this.article.query.month}月`;
            }
            return '全部';
        },
        title() {
            return this.currentAchive + '-文章归档-德哥blog';
        }
    },
    components: {
        'article-scroll-page':ArticleScrollPage
    },
    watch: {
        '$route'() {
            
            if (this.$route.params.year && this.$route.params.month) {
                this.article.query.year = this.$route.params.year;
                this.article.query.month = this.$route.params.month;
            }
        }
    },
    mounted() {
        this.listArchives();
    },
    methods: {
        changeArchive(year, month) {
            this.$router.push({path: `/archives/${year}/${month}`});
        },
        listArchives() {
            listArchives().then(
                res => {
                    this.archives = res.data.data;
                }
            ).catch(
                err => {
                    this.$message({
                        type:'error',
                        message: '文章归档失败！',
                        showClose: true
                    })
                }
            )
        }

    }
}
</script>

<style scoped>
.el-container {
    width: 640px;
}

.el-aside {
    position: fixed;
    left: 200px;
    margin-right: 50px;
    width: 150px !important;
}

.el-main {
    padding: 0px;
    line-height: 16px;
}

.me-month-list {
    margin-top: 10px;
    margin-bottom: 10px;
    text-align: center;
    list-style-type: none;
}

.me-month-item {
    margin-top: 18px;
    padding: 4px;
    font-size: 18px;
    color: #5FB878;
}

.me-order-list {
    float: right;
}

.me-month-title {
    margin-left: 4px;
    margin-bottom: 12px;
}
</style>