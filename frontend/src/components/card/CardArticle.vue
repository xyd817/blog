<template>
    <el-card body-style="{padding: '8px 18px'}">
            <div slot="header" class="me-category-header">
                    <span>{{cardHeader}}</span>
            </div>
            <ul class="me-category-list">
                <li v-for="article in articles" :key="article.id" :style="itemStyle"
                    @click="view(article.id)" class="me-category-item">
                    <a>{{article.title}}</a>
                    </li>
            </ul>
    </el-card>
</template>

<script>
export default {
    name:'CardArticle',
    props: {
        cardHeader:{
            type:String,
            required: true,
        },
        articles: {
            type: Array,
            required: true,
        },
        itemStyle: Object,
    },
    methods: {
        view(id) {
            this.$router.push({path:`/view/${id}`});
        }
    }

}
</script>

<style scoped>
.me-category-header {
    font-weight:600;
}
.me-category-list {
    list-style-type: none;
}
.me-category-item {
    padding: 4px;
    font-size: 14px;
    color: #5FB878;
}
.me-category-item a:hover {
    text-decoration: underline;
}
</style>