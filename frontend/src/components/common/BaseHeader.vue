<template>
    <el-header class="me-area">
        <el-row class="me-header">
            <el-col :span="4" class="me-header-left">
                <router-link to='/' class="me-title">
                    <img src="../../assets/img/blog_logo.jpg" alt="logo" height="60px" width="330px" />
                </router-link>
            </el-col>
            <el-col :span="16" v-if="!simple">
                <el-menu :router="true" menu-trigger="click" active-text-color="#00aaff" :default-active="activeIndex"
                mode="horizontal">
                    <el-menu-item index="/">首页</el-menu-item>
                    <el-menu-item index="/category/all">文章分类</el-menu-item>
                    <el-menu-item index="/tag/all">标签</el-menu-item>
                    <el-menu-item index="/archives">文章归档</el-menu-item>
                    <el-col :span="4" :offset="4">
                        <el-menu-item index="/write?isEdit=flase"><i class="el-icon-edit"></i>写文章</el-menu-item>
                    </el-col>
                </el-menu>
            </el-col>
            <template v-else>
                <slot></slot>
            </template>
            <el-col :span="4">
                <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#76c588">
                    <template v-if="!user.login">
                            <el-menu-item index="/login">
                                <el-button type="text">登录</el-button>
                            </el-menu-item>
                            <el-menu-item index="/register">
                                <el-button type="text">注册</el-button>
                            </el-menu-item>
                    </template>
                    <template v-else>
                        <el-submenu index>
                            <template slot="title">
                                <img class="me-header-picture" :src="user.avatar"/>
                            </template>
                            <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
                        </el-submenu>
                    </template>
                </el-menu>
            </el-col>
        </el-row>
    </el-header>
</template>

<script>
export default {
    name:"BaseHeader",
    props: {
        activeIndex: String,
        simple: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {

        }
    },
    computed: {
        user() {
            let login = this.$store.state.account.length != 0;
            let avatar = this.$store.state.avatar;
            return {
                login,
                avatar
            }
        }
    },
    methods: {
        logout() {
            this.$store.dispatch("logout").then(
                () => {
                    this.$router.push({'path': '/login'});
                }
            ).catch(
                err => {
                    if (err !== 'error') {
                        console.log("==========================>>>>>>>>>>>>>>>");
                        this.$message({message: err, type: 'error', showClose: true});
                }
            }
            )
        }
    }
}
</script>

<style scoped>
.el-button{
    color: #00aaff;
}

.el-header {
    padding: 0px;
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
}
.me-header-left {
    margin-top: 0px;
}
.me-title {
    margin-top: 0px;
    font-size: 24px;
}
.me-title img {
    height: 60px;
    max-width: 100%;
}

.me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #76c588;
}
</style>