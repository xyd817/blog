<template>
    <div class="me-ct-body" v-title :data-title="title">
        <el-container class="me-ct-container">
            <el-main>
                <div class="me-ct-title me-area">
                    <template v-if="this.$route.params.type == 'tag'">
                        <img class="me-ct-picture" :src = 'ct.avatar ? ct.avatar : defaultAvatar' />
                        <h3 class="me-ct-name">{{ct.tagName}}</h3>
                    </template>
                    <template v-else>
                        <img class="me-ct-picture" :src = 'ct.avatar ? ct.avatar : defaultAvatar' />
                        <h3 class="me-ct-name">{{ct.tagName}}</h3>
                        <p>{{ct.description}}</p>
                    </template>
                    <span class="me-ct-meta">{{ct.articles}}文章</span>
                </div>
                <div class="me-ct-articles" >
                    <article-scroll-page v-bind:query="article"></article-scroll-page>
                </div>
            </el-main>
        </el-container>

    </div>
</template>

<script>
import defaultAvatar from '@/assets/img/logo.png';
import ArticleScrollPage from '@/components/common/ArticleScrollPage';
import {getTagDetailById} from '@/api/tag';
import {getCategoryDetailById} from '@/api/category';
export default {
    name: 'BlogCategoryTag',
    data() {
        return {
            ct: {},
            defaultAvatar: defaultAvatar,
            article: {
                tagId: null,
                categoryId: null,
            }
        }
    },
    computed: {
        title() {
            if (this.$route.params.type == 'tag') {
                return '标签-码神之路';
            } else {
                return '文章分类-码神之路';
            }
        }
    },
    components: {
        'article-scroll-page':ArticleScrollPage,
    },
    created() {
        this.getCategoryOrTagAndArticles();
    },
    methods: {
        getCategoryOrTagAndArticles(){
            var type = this.$route.params.type;
            var id = this.$route.params.id;
            if (type == 'tag') {
                this.getTagDetailById(id);
                this.article.tagId = id;
            } else {
                this.getCategoryDetailById(id);
                this.article.categoryId = id;
            }
        },
        getTagDetailById(id) {
            getTagDetailById(id).then(
                (res) => {
                    if (res.data.success) {
                        this.ct = res.data.data;
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }
            ).catch(
                (err) => {
                    this.$message.error("系统错误！");
                }
            ).finally(
                () => {}
            )
        },
        getCategoryDetailById(id) {
            getCategoryDetailById(id).then(
            (res) => {
                    if (res.data.success) {
                        this.ct = res.data.data;
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }
            ).catch(
                (err) => {
                    this.$message.error("系统错误！");
                }
            ).finally(
                () => {}
            )
        }
    }

}
</script>

<style scoped>
.me-ct-body {
    margin: 60px auto 140px;
    min-width: 100%;
}
.el-main {
    padding: 0;
}
.me-ct-title {
    text-align: center;
    height: 150px;
    padding: 20px;
}
.me-ct-picture {
    width: 60px;
    height: 60px;
}
.me-ct-name {
    font-size: 28px;
}
.me-ct-meta {
    font-size: 12px;
    color: #969696;
}
.me-ct-articles {
    width: 640px;
    margin: 30px auto;
}
</style>