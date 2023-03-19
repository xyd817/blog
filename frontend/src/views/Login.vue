<template>
    <div id="login" v-title data-title="登录 - 德哥Blog">
        <!--<video preload="auto" class="me-video-player" autoplay="autoplay" loop="loop">
            <source src="../../static/vedio/sea.mp4" type="video/mp4">
        </video>-->

        <div class="me-login-box me-login-box-radius">
        <h1>德哥Blog &nbsp;登录</h1>

        <el-form ref="userForm" :model="userForm" :rules="rules">
            <el-form-item prop="account">
            <el-input placeholder="用户名" v-model="userForm.account"></el-input>
            </el-form-item>

            <el-form-item prop="password">
            <el-input placeholder="密码" type="password" v-model="userForm.password"></el-input>
            </el-form-item>
            <!-- 随机验证码 输入框 -->
			<el-form-item prop="verifycode">
                <div class="verify_class">
                    
                    <el-input v-model="userForm.verifycode" placeholder="请输入验证码" class="identifyinput"
                        style="width:150px;margin-top: 10px;"
                    ></el-input>
                    
                    <div>
                        <div @click="refreshCode">
                                <s-identify :identifyCode="identifyCode"></s-identify>
                        </div>
                        <!-- 刷新验证码 -->
                        <el-button @click="refreshCode" type='text' style="margin-top:-45px">看不清，换一张</el-button>
                    </div>
                </div>
			</el-form-item>
			
            <el-form-item size="small" class="me-login-button">
            <el-button type="primary" @click.native.prevent="login('userForm')">登录</el-button>
            </el-form-item>
            
        </el-form>

        </div>
    </div>
</template>

<script>
import SIdentify from '@/components/randomcode/RandomCode.vue'
    export default {
        name: 'Login',
        data() {
            // 自定义验证规则：验证码验证规则
        const validateVerifycode = (rule, value, callback) => {
            
            if (value === '') {
                callback(new Error('请输入验证码'))
            } else if (value !== this.identifyCode) {
                console.log('validateVerifycode:', value)
                callback(new Error('验证码不正确'))
            } else {
                callback()
            }
        }
        return {
            
            userForm: {
                account: '',
                password: '',
                verifycode: ''
            },
            rules: {
            account: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
                {max: 10, message: '不能大于10个字符', trigger: 'blur'}
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'blur'},
                {max: 10, message: '不能大于10个字符', trigger: 'blur'}
            ],
            verifycode: [
						{ required: true, trigger: 'blur', validator: validateVerifycode },
					]
            },
            identifyCodes: '1234567890',
			identifyCode: '',
			
        }
        },
        components: {
			// 注册绘制随机验证码的组件
			SIdentify
		},
        mounted() {
			// 验证码初始化
			this.identifyCode = ''
			this.makeCode(this.identifyCodes, 4)
		},
        methods: {
            login(formName) {
            let that = this

            this.$refs[formName].validate((valid) => {
            if (valid) {

                that.$store.dispatch('login', that.userForm).then(() => {
                    that.$router.go(-1)
                }).catch((error) => {
                if (error !== 'error') {
                    that.$message({message: error, type: 'error', showClose: true});
                }
                })
            } else {
                return false;
            }
            });
        },
        // 生成随机数
			randomNum(min, max) {
				return Math.floor(Math.random() * (max - min) + min)
			},
			// 切换验证码
			refreshCode() {
				this.identifyCode = ''
				this.makeCode(this.identifyCodes, 4)
			},
			// 生成四位随机验证码
			makeCode(o, l) {
				for (let i = 0; i < l; i++) {
					this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
				}
				console.log(this.identifyCode)
			}
        }
    }
    </script>

    <style scoped>
    #login {
        min-width: 100%;
        min-height: 100%;
    }

    .me-video-player {
        background-color: transparent;
        width: 100%;
        height: 100%;
        object-fit: fill;
        display: block;
        position: absolute;
        left: 0;
        z-index: 0;
        top: 0;
    }

    .me-login-box {
        position: absolute;
        width: 300px;
        height: 280px;
        background-color: white;
        margin-top: 150px;
        margin-left: -180px;
        left: 50%;
        padding: 30px;
    }

    .me-login-box-radius {
        border-radius: 10px;
        box-shadow: 0px 0px 1px 1px rgba(161, 159, 159, 0.1);
    }

    .me-login-box h1 {
        text-align: center;
        font-size: 24px;
        margin-bottom: 20px;
        vertical-align: middle;
    }

    .me-login-design {
        text-align: center;
        font-family: 'Open Sans', sans-serif;
        font-size: 18px;
    }

    .me-login-design-color {
        color: #5FB878 !important;
    }

    .verify_class {
        display: flex;
        justify-content: space-between;
        height: 50px;
        line-height: 20px;
    }

    .me-login-button {
        margin-top: 25px;
        text-align: center;
    }

    .me-login-button button {
        width: 100%;
    }

</style>