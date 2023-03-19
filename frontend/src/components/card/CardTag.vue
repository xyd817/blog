<template>
    <el-card :body-style="{padding:'8px 18px'}">
        <div slot="header" class="me-tag-header">
            <span>最热标签</span>
            <a @click="moreTags" class="me-pull-right me-tag-more">查看全部</a>
        </div>
        <ul class="me-tag-list">
            <li class="me-tag-item" v-for="tag in tags" :key="tag.id">
                <el-button @click="tag(tag.id)"  size="mini" type="primary" round plain 
                style="background-color: #76c588;color: #fff">
                    {{tag.tagName}}
                </el-button>
            </li>
        </ul>
    </el-card>
</template>

<script>
export default {
    name:'CardTag',
    props: {
        tags:Array
    },
    data() {
        return {

        }
    },
    methods: {
        moreTags() {
            this.$router.push('/tag/all');
        },
        tag(id) {
            this.$router.push({path:`/tag/${id}`});
        }
    }

}
</script>

<style scoped>
.me-tag-header {
    font-weight: 600;
}
.me-tag-more {
    font-size: 14px;
    color: #78b6f7;
}
.me-tag-list{
    list-style-type: none;
}
.me-tag-item {
    display: inline-block;
    padding: 4px;
    font-size: 14px;
    color: #fff;
}

.me-tag-item a:hover {
    text-decoration: underline;
}
</style>