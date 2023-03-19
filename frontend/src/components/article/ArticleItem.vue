<template>
    <el-card class="me-area" :body-style="{padding: '16px'}">
        <div class="me-article-header">
            <a @click="view(id)" class="me-article-title">{{title}}</a>
            <!-- <el-button v-if="weight > 0" class="me-article-icon" type="text">置顶</el-button> -->
            <span class="me-pull-right me-article-count">
                <i class="me-icon-comment"></i>&nbsp;{{commentCounts}}
            </span>
            <span class="me-pull-right me-article-count">
                <i class="el-icon-view"></i>&nbsp;{{viewCounts}}
            </span>
            
        </div>

        <div class="me-article-description">
            {{summary}}
        </div>

        <div class="me-article-footer">
            
            <span class="me-article-author">
                &nbsp;作者:<el-tag size="mini" type="success">{{author}}</el-tag>
            </span>
            
            <span class="me-article-tags">
                文章标签:
                <el-tag v-for="t in tags" :key="t.tagName" size="mini" type="success">{{t.tagName}}</el-tag>
            </span>
            
            <span class="me-pull-right me-article-count">
                <i class="el-icon-time"></i>&nbsp;{{createDate | format}}
            </span>
    
            
        </div>

    </el-card>

</template>

<script>

import {formatTime} from '../../utils/time';

export default {
    name:'ArticleItem',
    props: {
        id:String,
        weight:Number,
        title:String,
        commentCounts: Number,
        viewCounts:Number,
        author:String,
        tags:Array,
        createDate:String,
        summary: String,
    },
    data() {
        return {

        }
    },
    methods : {
        view(id) {
            this.$router.push({path:`/view/${id}`});
        }
    }
}
</script>

<style scoped>
.me-article-header {
    padding-bottom: 10px;
}
.me-article-title {
    font-size:600;
}
.me-article-icon {
    padding: 3px 8px;
}
.me-pull-right {
    float:right;
}
.me-article-count {
    color: #76c588;
    padding-left:14px;
    font-size:15px;
}
.me-article-description {
    font-size:13px;
    line-height: 24px;
    margin-bottom: 10px;
    
}
.me-article-author{
    color: #76c588;
    padding-right: 20px;
    font-size:13px;
}
.me-article-tags {
    color: #76c588;
    font-size: 13px;
}
.el-tag {
    margin-left:6px;
    color:#ffffff;
    background-color: #76c588;
}
</style>>
