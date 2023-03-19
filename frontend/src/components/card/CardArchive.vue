<template>
    <el-card :style="{padding:'8px 18px'}" >
        <div slot="header" class="me-category-header">
            <span>{{cardHeader}}</span>
        </div>
        <ul class="me-category-list">
            <li v-for="archive in archives" :key="archive.year + archive.month" 
                @click="view(archive.year,archive.month)" class="me-category-item">
                <a>{{`${archive.year}年${archive.month}月(${archive.count})`}}</a>
            </li>
        </ul>
    </el-card>
</template>

<script>
export default {
    name:'CardArchive',
    props: {
        cardHeader: {
            type:String,
            required: true,
        },
        archives: {
            type:Array,
            required: true,
        }
    },
    methods: {
        view(year, month) {
            this.$router.push({path:`/archives/${year}/${month}`});
        }
    }

}
</script>

<style scoped>
.me-category-header {
    font-weight: 600;
}
.me-category-list {
    list-style-type: none;
}
.me-category-item {
    display: inline-block;
    width: 45%;
    padding: 4px;
    font-size: 14px;
    color: #5FB878;
}
.me-category-item a:hover {
    text-decoration: underline;
}

</style>