<template>
    <div style="width: 100vw;height: 100vh">
        <el-container style="height: 100vh">
            <el-aside :width="isCollapse?'60px':'220px'"
                      style="border-right: solid 1px #c2c2c2;text-align: center;transition: 0.3s">
                <el-menu
                        :default-active="router.currentRoute.value.path"
                        router
                        style="border: none;width: 100%"
                        :collapse="isCollapse"
                >
                    <el-menu-item index="/index">
                        <el-icon>
                            <icon-menu/>
                        </el-icon>
                        <template #title>帖子列表</template>
                    </el-menu-item>
                    <el-menu-item index="/index/settings">
                        <el-icon>
                            <setting/>
                        </el-icon>
                        <template #title>个人设置</template>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header style="border-bottom: solid 1px #c2c2c2">
                    <el-row>
                        <el-col :span="2" style="margin: auto">
                            <el-button @click="isCollapse = !isCollapse" :icon="isCollapse?Expand:Fold" text
                                       style="font-size: 24px;margin-left: -5px"/>
                        </el-col>
                        <el-col :span="13" style="margin: auto">
                            <el-input
                                    style="width: 400px"
                                    v-model="search"
                                    placeholder="搜索论坛内容"
                            >
                                <template #prefix>
                                    <el-icon>
                                        <Search/>
                                    </el-icon>
                                </template>
                                <template #append>
                                    <el-select v-model="select" placeholder="Select" style="width: 115px">
                                        <el-option label="Restaurant" value="1"/>
                                        <el-option label="Order No." value="2"/>
                                        <el-option label="Tel" value="3"/>
                                    </el-select>
                                </template>
                            </el-input>
                        </el-col>
                        <el-col :span="8" style="margin: auto">
                            <div style="text-align: right;margin-right: 10px">
                                <div style="font-weight: bold">
                                    {{ store.auth.account.nickName }}
                                </div>
                                <div style="font-size: 13px;color: gray">
                                    <span>{{ store.auth.account.grade }}</span>
                                    <span>{{ store.auth.account.specialty }}</span>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="1">
                            <el-dropdown trigger="click">
                                <span class="el-dropdown-link">
                                    <el-avatar class="avatar" size="large"
                                               :src="store.auth.account.avatar"
                                    />
                                </span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item :icon="CirclePlus">Action 3</el-dropdown-item>
                                        <el-dropdown-item :icon="Check">个人设置</el-dropdown-item>
                                        <el-dropdown-item :icon="SwitchButton" @click="logout" divided>
                                            <div style="color: red">退出登录</div>
                                        </el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </el-col>
                    </el-row>

                </el-header>
                <el-main style="padding: 0;background-color: gainsboro ">
                    <el-scrollbar height="600px">
                        <router-view/>
                    </el-scrollbar>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script setup>
    import {get, post} from "@/net";
    import {ElMessage} from "element-plus";
    import router from "@/router";
    import {useStore} from "@/stores";
    import {
        Document,
        Setting,
        Menu as IconMenu,
        Search,
        CirclePlus,
        Check,
        SwitchButton, Expand, Fold, Star,
    } from "@element-plus/icons-vue";
    import {ref} from "vue";

    const store = useStore()
    const logout = () => {
        get('/api/account/logout', (msg) => {
            store.auth.account = null
            // localStorage.removeItem("account")
            ElMessage.success(msg)
            router.push('/')
        })
    }


    const isCollapse = ref(false)

    const search = ref('')
    const select = ref('')
</script>

<style scoped>

    .avatar:hover {
        cursor: pointer;
    }
</style>