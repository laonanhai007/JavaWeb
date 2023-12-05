<template>
    <div style="text-align: center;margin: 0 20px">
        <div style="margin-top: 130px">
            <div style="font-size: 25px;font-weight: bold">登录</div>
            <div style="font-size: 14px;color: gray;margin-top: 10px">请输入用户名和密码登录</div>
        </div>
        <div style="margin-top: 50px">
            <el-input type="text" placeholder="用户名/邮箱" v-model="form.text">
                <template #prefix>
                    <el-icon>
                        <User/>
                    </el-icon>
                </template>
            </el-input>
            <el-input type="password" placeholder="密码" style="margin-top: 10px" v-model="form.password">
                <template #prefix>
                    <el-icon>
                        <Lock/>
                    </el-icon>
                </template>
            </el-input>
            <el-row style="margin-top: 10px;font-size: 13px">
                <!--                <el-col :span="12" style="text-align: left">-->
                <!--                    <el-checkbox v-model="form.remember" label="记住我" size="default"/>-->
                <!--                </el-col>-->
                <el-col :span="12" style="text-align: right">
                    <el-link @click="router.push('/forget')">忘记密码?</el-link>
                </el-col>
            </el-row>
            <div style="margin-top: 50px">
                <el-button style="width: 200px;font-size: 16px" type="success" @click="login">立即登录</el-button>
            </div>
            <el-divider>
                <span style="color: gray">没有账号?</span>
            </el-divider>
            <div style="margin-top: 10px">
                <el-button style="width: 200px;font-size: 16px" type="warning" @click="router.push('/register')">
                    立即注册
                </el-button>
                <!--                <el-button style="width: 200px;font-size: 16px;margin-top: 10px" type="warning" @click="test">-->
                <!--                    GET测试-->
                <!--                </el-button>-->
                <!--                <el-button style="width: 200px;font-size: 16px;margin-top: 10px" type="warning" @click="testPost">-->
                <!--                    POST测试-->
                <!--                </el-button>-->
            </div>

        </div>
    </div>
</template>

<script setup>
    import {Lock, User} from "@element-plus/icons-vue";
    import {ElMessage} from "element-plus";
    import {get, post} from "@/net";
    import {reactive, ref} from "vue";
    import router from "@/router";
    import {useStore} from "@/stores";

    const form = ref({
        text: '',
        password: '',
    })
    const store = useStore()

    const login = () => {
        if (!form.value.text || !form.value.password)
            ElMessage.warning('请输入用户名或密码')
        else {
            post('/api/auth/login', form.value, (message) => {
                ElMessage.success(message)
                get('/api/account/me', (message) => {
                    // localStorage.setItem("account", JSON.stringify(message))
                    store.auth.account = message
                    router.push('/index')
                })
            })
        }
    }


    const test = () => {
        get('/api/auth/login', (msg) => {
            console.log(msg)
        })
    }
    const user = reactive({
        username: "tom",
        password: "123"
    })
    const testPost = () => {
        post('/api/auth/login', user, (msg) => {
            ElMessage.success(msg)
        })
    }
</script>

<style scoped>

</style>