<template>
    <div ref="scroll" id="scroll-page" style="overflow:hidden">
        <slot></slot>
        <div
        style="height: 40px;margin-top: 10px;z-index: 1"
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(245,245,245)"></div>
    </div>
</template>

<script>
export default {
    name:'ScrollPage',
    props: {
        loading:Boolean,
        noData:Boolean,
        offset:Number,
    },
    mounted() {
        // 绑定监听事件，在滚动时触发
        window.addEventListener('scroll',this.handleScroll, false);
    },
    beforeDestroy() {
        // 移出事件监听
        window.removeEventListener('scroll', this.handleScroll);
    },
    data() {
        return {
            scrollAction: {
                x:'undefined',
                y:'undefined'
            }
        }
    },
    methods: {
        handleScroll(e) {
        
            if ( !this.noData ) {
                let curHeight = document.documentElement.scrollTop || document.body.scrollTop;
                // 获取div区域
                var scrollPage = document.getElementById('scroll-page');
                if (((curHeight + window.innerHeight + 1) >= (this.$refs.scroll.offsetHeight + this.offset )) &&
                this.isDownDirection()) {
                     //判断是否到达底部
                    if (!this.loading) {
                        //调用load加载数据
                        this.$emit('load');
                    }
                }
            }

            
        },
        isDownDirection() {
            if (typeof this.scrollAction.x == 'undefined') {
                this.scrollAction.x = window.pageXOffset;
                this.scrollAction.y = window.pageYOffset;
            }
            var diffX = this.scrollAction.x  - window.pageXOffset;
            var diffY = this.scrollAction.y - window.pageYOffset;

            this.scrollAction.x = window.pageXOffset;
            this.scrollAction.y = window.pageYOffset;

            if (diffY < 0 ) {
                return true;
            }
            return false;

        }
    }

}
</script>

<style>

</style>