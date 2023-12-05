<template>
    <div class="topic-page" v-if="topicDetails.topic">
        <div class="topic-main" style="position: sticky;top: 0;z-index: 10">
            <div style="display: flex;width: 100%;border-radius: 3px;border: solid 1px gray">
                <el-button :icon="ArrowLeft" type="info" round plain @click="router.push('/index')">返回列表</el-button>
                <div style="margin: auto;font-weight: bold">{{ topicDetails.topic.title }}</div>
            </div>
        </div>
        <div class="topic-main">
            <div class="topic-main-left">
                <div>
                    <el-avatar :size="'large'" :src="topicDetails.account.avatar"/>
                </div>
                <div style="font-size: 18px;font-weight: bold">
                    <span>{{ topicDetails.account.nickName }}</span>
                    <span v-if="topicDetails.account.gender === 1"><el-icon><Male/></el-icon></span>
                    <span v-if="topicDetails.account.gender === 0"><el-icon><Female/></el-icon></span>
                </div>
                <el-divider style="margin: 10px 0"/>
                <div style="text-align: left;margin-left: 10px" class="desc">
                    <div>QQ:{{ topicDetails.account.qq }}</div>
                    <div>微信:{{ topicDetails.account.wx }}</div>
                    <div>个人博客地址:{{ topicDetails.account.blog }}</div>
                    <div>专业:{{ topicDetails.account.specialty }}</div>
                    <div>年级:{{ topicDetails.account.grade }}</div>
                    <div>电话:{{ topicDetails.account.phone }}</div>
                </div>
                <el-divider style="margin: 10px 0"/>
                <div class="desc">
                    <div>{{ topicDetails.account.desc }}</div>
                </div>
            </div>
            <div class="topic-main-right">
                <div class="topic-content" v-html="content"></div>
                <div style="text-align: right;margin-top: 30px">
                    <InteractButton style="margin-right: 15px" name="编辑帖子"
                                    :check="false"
                                    color="dodgerblue"
                                    v-if="store.auth.account.uid === topicDetails.account.uid"
                                    @check="edit = true">
                        <el-icon>
                            <EditPen/>
                        </el-icon>
                    </InteractButton>
                    <InteractButton style="margin-right: 5px" check-name="已点赞" name="点个赞吧!" color="pink"
                                    :check="topicDetails.like"
                                    @check="interact('like','点赞')">
                        <el-icon>
                            <Pointer/>
                        </el-icon>
                    </InteractButton>
                    <InteractButton
                            @check="interact('collect','收藏')"
                            name="收藏" check-name="已收藏" color="orange"
                            :check="topicDetails.collect">
                        <el-icon>
                            <Star/>
                        </el-icon>
                    </InteractButton>
                </div>
            </div>
        </div>
        <TopicEditor
                :default-type="topicDetails.topic.type"
                :default-title="topicDetails.topic.title"
                :default-text="topicDetails.topic.content"
                :tid="topicDetails.topic.id"
                :show="edit" @close="edit = false"/>
        <!--        <div class="add-comment"  @click="comment.show = true">-->
        <!--            <el-icon>-->
        <!--                <Plus />-->
        <!--            </el-icon>-->
        <!--        </div>-->
        <!--        <TopicCommentEditor :show="comment.show"-->
        <!--                            @close="comment.show = false"-->
        <!--                            :tid="topicDetails.topic.id"-->
        <!--                            :quote="comment.quote"/>-->
    </div>

</template>

<script setup>
    import {useRoute} from "vue-router";
    import {reactive, ref} from "vue";
    import {get} from "@/net";
    import {ArrowLeft, EditPen, Female, Male, Plus, Pointer, Star} from "@element-plus/icons-vue";
    import {QuillDeltaToHtmlConverter} from 'quill-delta-to-html'
    import {computed} from "vue";
    import router from "@/router";
    import InteractButton from "@/components/InteractButton.vue";
    import {ElMessage} from "element-plus";
    import {useStore} from "@/stores";
    import TopicEditor from "@/components/TopicEditor.vue";
    import TopicCommentEditor from "@/components/TopicCommentEditor.vue";

    const edit = ref(false)
    const route = useRoute()
    const tid = route.params.tid
    const store = useStore()
    get(`/api/forum/topic?tid=${tid}`, (message) => {
        topicDetails.topic = message.topic
        topicDetails.account = message.account
    })
    get(`/api/forum/topic-interact?tid=${tid}`, (message) => {
        topicDetails.like = message.like
        topicDetails.collect = message.collect
    })

    const topicDetails = reactive({
        topic: null,
        account: null,
        comments: [],
        like: false,
        collect: false
    })

    const content = computed(() => {
        const ops = topicDetails.topic.content.ops
        const converter = new QuillDeltaToHtmlConverter(ops, {inlineStyles: true})
        return converter.convert()
    })
    const interact = (type, message) => {
        get(`/api/forum/interact?tid=${topicDetails.topic.id}&type=${type}&state=${!topicDetails[type]}`, () => {
            topicDetails[type] = !topicDetails[type]
            if (topicDetails[type])
                ElMessage.success(message + '成功')
            else
                ElMessage.success('已取消' + message)
        })
    }
</script>

<style scoped>
    .topic-page {
        display: flex;
        flex-direction: column;
        gap: 10px;
        padding: 10px 0;
    }

    .topic-main {
        display: flex;
        border-radius: 7px;
        margin: 0 auto;
        background-color: var(--el-bg-color);
        width: 800px;

        .topic-main-left {
            text-align: center;
            width: 200px;
            padding: 10px;
            border-right: solid 1px var(--el-border-color);

            .desc {
                font-size: 12px;
                color: gray;
            }
        }

        .topic-main-right {
            width: 600px;
            padding: 10px 20px;

            .topic-content {
                font-size: 14px;
                line-height: 22px;
                opacity: 0.8;
            }
        }
    }

    .add-comment {
        position: fixed;
        bottom: 20px;
        right: 20cap;
        width: 40px;
        height: 40px;
        border-radius: 50%;
        color: blue;
        font-size: 18px;
        text-align: center;
        line-height: 45px;
        background-color: var(--el-bg-color-overlay);
        box-shadow: var(--el-box-shadow-light);

        &:hover {
            cursor: pointer;
            background: var(--el-border-color-extra-light);
        }
    }
</style>