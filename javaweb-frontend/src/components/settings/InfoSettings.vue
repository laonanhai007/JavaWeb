<template>
    <el-form
            label-position="top"
            label-width="100px"
            :model="infoForm"
            style="max-width: 460px"
    >
        <el-form-item label="昵称">
            <el-input v-model="infoForm.nickName"/>
        </el-form-item>
        <el-form-item label="性别">
            <el-radio-group v-model="infoForm.gender">
                <el-radio label="1" size="large">男</el-radio>
                <el-radio label="0" size="large">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="专业">
            <el-input v-model="infoForm.specialty"/>
        </el-form-item>
        <el-form-item label="年级">
            <el-input v-model="infoForm.grade"/>
        </el-form-item>
        <el-form-item label="QQ">
            <el-input v-model="infoForm.qq"/>
        </el-form-item>
        <el-form-item label="微信">
            <el-input v-model="infoForm.wx"/>
        </el-form-item>
        <el-form-item label="博客">
            <el-input v-model="infoForm.blog"/>
        </el-form-item>
        <el-form-item label="电话">
            <el-input v-model="infoForm.phone"/>
        </el-form-item>
        <el-form-item label="个人简介">
            <el-input type="textarea" rows="5" v-model="infoForm.desc"/>
        </el-form-item>
    </el-form>
    <el-button type="success" :icon="Select" @click="saveInfo">保存个人信息设置</el-button>
    <el-text style="font-size: 13px;color: lightgray;margin-left: 5px">未填空的内容将不会更改</el-text>
</template>

<script setup>
    import {reactive} from "vue";
    import {Select} from "@element-plus/icons-vue";
    import {post} from "@/net";
    import {ElMessage} from "element-plus";
    import {useStore} from "@/stores";

    const store = useStore()
    const infoForm = reactive({
        nickName: store.auth.account.nickName,
        gender: store.auth.account.gender,
        qq: store.auth.account.qq,
        wx: store.auth.account.wx,
        blog: store.auth.account.blog,
        specialty: store.auth.account.specialty,
        grade: store.auth.account.grade,
        phone: store.auth.account.phone,
        desc: store.auth.account.desc
    })

    const saveInfo = () => {
        post('/api/account/save', infoForm, (message) => {
            ElMessage.success(message)
        })
    }
</script>

<style scoped>

</style>