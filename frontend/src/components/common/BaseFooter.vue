<template>
    <el-footer class="me-area">
        <div class="me-footer">
            <p style="margin-top:0px;line-height:0px">Designed by
                <el-button type="text" class="me-login-design-color" @click="tocsdn">
                    <strong>德哥是程序员</strong>
                </el-button><br>
                <span style="font-size:10px;">
                    <a href="https://beian.miit.gov.cn/" target="_blank">粤ICP备2023016818号-1</a>
                </span>
            </p>
            
            
        </div>
    </el-footer>
</template>

<script>
export default {
    name:"BaseFooter",
    data() {
        return {

        }
    },
    methods: {
        tocsdn() {
            window.open("https://blog.csdn.net/weixin_44335707", "_blank");
        }
    },
}
</script>

<style scoped>
.el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
}

.me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;

}

.me-login-design-color {
    color: #00aaff !important;
    font-size: 18px;
}
</style>