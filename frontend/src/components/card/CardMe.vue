<template>
    <el-card>
        <h1 class="me-author-name">德哥blog</h1>
        <div class="me-author-description">
            <span><i class="el-icon-location-outline"></i> &nbsp;深圳&南山</span>
            <span><i class="me-icon-job"></i> &nbsp;java开发工程师</span>
        </div>
        <!-- <div class="my-author-tool">
            <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-qq"></i>
            <i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-bilibili-fill"></i>
        </div> -->
    </el-card>
</template>

<script>
export default {
    name:'CardMe',
    data() {
        return {
        //         qq: {title: 'QQ', message: '130823919'},
        //         bilibili: {
        //             title: '哔哩哔哩',
        //             message: '<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>'
        // }
        }
    },
    methods: {
        showTool(tool) {
            this.$message({
                    duration: 0,
                    showClose: true,
                    dangerouslyUseHTMLString: true,
                    message: '<strong>' + tool.message + '</strong>'
            });
        }
    }

}
</script>

<style scoped>
.me-author-name{
    text-align: center;
    font-size: 25px;
    border-bottom:1px solid #00aaff;
}
.me-author-description{
    padding: 4px 10px;
}
.me-icon-job {
    padding-left:16px;
}
.my-author-tool {
    text-align: center;
    padding-top: 10px;
}
.my-author-tool i{
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
}
</style>