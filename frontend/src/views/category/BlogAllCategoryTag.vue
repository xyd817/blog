<template>
    <div class="me-allct-body" v-title :data-title="categoryTagTitle">
        <el-container class="me-allct-allct">
        <el-main>
            <el-tabs v-model="activeName" >
                <el-tab-pane label="文章分类" name="category">
                    <ul class="me-allct-items">
                    <li v-for="c in categories" :key="c.id" class="me-allct-item" @click="view(c.id)">
                        <div me="me-allct-content">
                        <a class="me-allct-info">
                            <img class="me-allct-img" :src="c.avatar ? c.avatar : defaultAvatar" />
                            <h4 class="me-allct-name">{{c.categoryName}}</h4>
                            <p class="me-allct-description">{{c.description}}</p>
                        </a>
                        <div class="me-allct-meta">
                            <span>{{c.articles}}文章</span>
                        </div>
                        </div>
                    </li>
                    </ul>
                </el-tab-pane>
                <el-tab-pane label="标签" name="tag">
                    <ul class="me-allct-items">
                        <li v-for="tag in tags" :key="tag.id" class="me-allct-item" @click="view(tag.id)">
                            <div me="me-allct-content">
                            <a class="me-allct-info">
                                <img class="me-allct-img" :src="tag.avatar ? tag.avatar : defaultAvatar" />
                                <h4 class="me-allct-name">{{tag.tagName}}</h4>
                            </a>
                            <div class="me-allct-meta">
                                <span>{{tag.articles}}文章</span>
                            </div>
                            </div>
                        </li>
                    </ul>
                </el-tab-pane>
            </el-tabs>
        </el-main>
        </el-container>
    </div>
</template>

<script>
import defaultAvatar from "@/assets/img/logo.png";
import { listCategoryDetail } from "../../api/category";
import { listTagsDetail } from "../../api/tag";
export default {
    name: "BlogAllCategoryTag",
    data() {
        return {
        categories: [],
        tags: [],
        defaultAvatar: defaultAvatar,
        currentActiveName: 'category'
        };
    },
    computed: {
        activeName: {
            get() {
                return (this.currentActiveName = this.$route.params.type)
            },
            set(newValue) {
                this.currentActiveName = newValue
            }
        },
        categoryTagTitle() {
        if (this.activeName == "category") {
            return "文章分类-德哥blog";
        } else {
            return "标签-德哥blog";
        }
        }
    },
    watch: {
        activeName:function(oldVal, newVal){
            console.log("=======================");
            if (newVal === "category") {
                this.listCategoryDetail();
            } else {
                this.listTagsDetail();
            }
        }

    },
    mounted() {
        this.listCategoryDetail();
        this.listTagsDetail();
    },

    methods: {
        
        view(id) {
            var type = this.activeName;
            this.$router.push({ path: `/${type}/${id}` });
        },
        listCategoryDetail() {
        listCategoryDetail()
            .then(res => {
            if (res.data.success) {
                this.categories = res.data.data;
            } else {
                this.$message.error(res.data.msg);
            }
            })
            .catch(err => {
                this.$message.error("系统异常!");
            })
            .finally(() => {});
        },
        listTagsDetail() {
        listTagsDetail()
            .then(res => {
            if (res.data.success) {
                this.tags = res.data.data;
            } else {
                this.$message.error(res.data.msg);
            }
            })
            .catch(err => {
                this.$message.error("系统异常!");
            })
            .finally(() => {});
        }
    }
};
</script>

<style scoped>
.me-allct-body {
    margin: 60px auto 140px;
    }
.me-allct-allct {
    width: 1000px;
    }
.me-allct-items {
    padding-top: 2rem;
    }
.me-allct-item {
    width: 25%;
    display: inline-block;
    margin-bottom: 2.4rem;
    padding: 0 0.7rem;
    box-sizing: border-box;
    }
.me-allct-content {
    display: inline-block;
    width: 100%;
    background-color: #fff;
    border: 1px solid #f1f1f1;
    transition: border-color 0.3s;
    text-align: center;
    padding: 1.5rem 0;
}
.me-allct-info {
    cursor: pointer;
}
.me-allct-img {
    margin: -40px 0 10px;
    width: 60px;
    height: 60px;
    vertical-align: middle;
}
.me-allct-name {
    font-size: 21px;
    font-weight: 150;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 4px;
}
.me-allct-description {
    min-height: 50px;
    font-size: 13px;
    line-height: 25px;
}
.me-allct-meta {
    font-size: 12px;
    color: #969696;
}
</style>