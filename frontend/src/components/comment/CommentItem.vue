<template>
    <div class="me-view-comment-item">
        <div class="me-view-comment-author">
            <a>
                <img class="me-view-picture" :src="comment.author.avatar" />
            </a>
            <div class="me-view-info">
                <span class="me-view-nickname">{{comment.author.nickname}}</span>
                <div class="me-view-nickname">
                    <span>{{ rootCommentCounts - index}}</span>
                    <span>{{ comment.createDate | format}}</span>
                </div>
            </div>
        </div>
        <div>
            <p class="me-view-comment-content">{{comment.content}}</p>
            <div class="me-view-comment-tools">
                <a class="me-view-comment-tool" @click="showComment(-1, comment.author)">
                    <i class="me-icon-comment"></i>&nbsp; 评论
                </a>
            </div>
            <div class="me-reply-list">
                <div class="me-reply-item" v-for="c in comment.childrens" :key="c.id">
                    <div style="font-size=14px">
                        <span class="me-reply-user">{{c.author.nickname}}:&nbsp;&nbsp;</span>
                        <span class="me-reply-user" v-if="c.level == 2">@{{ c.toUser.nickname}}</span>
                        <span>{{c.content}}</span>
                    </div>
                </div>
                <div class="me-view-meta">
                    <span class="padding-right:10px">{{comment.createDate | format}}</span>
                </div>
            </div>
            <div class="me-view-comment-write" v-show="commentShow">
                <el-input
                v-model="reply.content"
                type="input"
                style="width:90%"
                :placeholder=placeholder
                class="me-view-comment-text"
                resize="none"
                >
                </el-input>
                <el-button style="margin-left:8px" @click="publishComment()" type="text">评论</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import {publishComment} from '@/api/comment';
export default {
    name: 'CommentItem',
    props: {
        comment:Object,
        index: Number,
        rootCommentCounts: Number,
        articleId: String
    },
    data() {
        return {
            placeholder: '你的评论....',
            commentShow: false,
            commentShowIndex: '',
            reply: this.getEmptyReply()
        }
    }, 
    methods: {
        showComment(commentShowIndex, toUser) {
            this.reply = this.getEmptyReply();
            if (this.commentShowIndex !== commentShowIndex) {
                if (toUser) {
                    this.placeholder = `@${toUser.nickname} `
                    this.reply.toUserId = toUser.id
                } else {
                    this.placeholder = '你的评论...'
                }

                this.commentShow = true;
                this.commentShowIndex = commentShowIndex;
            } else {
                this.commentShow = false;
                this.commentShowIndex = '';
            }
        },

        publishComment() {
            let that = this;
            console.log(this.reply);
            if (!that.reply.content) {
                return;
            }
            publishComment(this.reply, this.$store.state.token).then(
                
                res => {
                    console.log(res.data)
                    if (res.data.success) {
                        
                        this.$message({
                            type: 'success',
                            message:'评论成功',
                            showClose: true
                        });
                        if(!that.comment.childrens){
                            that.comment.childrens = []
                        }
                        
                        that.comment.childrens.unshift(res.data.data)
                        that.$emit('commentCountsIncrement')
                        that.showComment(that.commentShowIndex)
                        
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.data.msg,
                            showClose: true
                        })
                    }
                }
            ).catch(
                err => {
                    
                    if (err !== 'error') {
                        that.$message({type: 'error', message: '评论失败', showClose: true});
                    }
                }
            )

        },
        getEmptyReply() {
            return  {
                articleId: this.articleId,
                parent: this.comment.id,
                toUserId: '',
                content: ''
            }
        }
    }

}
</script>

<style>
.me-view-tag-item {
        margin: 0 4px;
}

.me-view-comment {
        margin-top: 60px;
}

.me-view-comment-title {
        font-weight: 600;
        border-bottom: 1px solid #f0f0f0;
        padding-bottom: 20px;
}

.me-view-comment-write {
        margin-top: 20px;
}

.me-view-comment-text {
    font-size: 16px;
}

    .v-show-content {
        padding: 8px 25px 15px 30px !important;
    }

.v-note-wrapper .v-note-panel {
    box-shadow: none !important;
}

.me-view-comment-item {
    margin-top: 20px;
    padding-bottom: 16px;
    border-bottom: 1px solid #f0f0f0;
}

.me-view-comment-author {
    margin: 10px 0;
    vertical-align: middle;
}

.me-view-nickname {
    font-size: 14px;
}

.me-view-comment-content {
    line-height: 1.5;
}

.me-view-comment-tools {
    margin-top: 4px;
    margin-bottom: 10px;
}

.me-view-comment-tool {
    font-size: 13px;
    color: #a6a6a6;
    padding-right: 14px;
}

.v-note-wrapper .v-note-panel .v-note-show .v-show-content, .v-note-wrapper .v-note-panel .v-note-show .v-show-content-html {
    background: #fff !important;
}

.me-reply-list {
    padding-left: 16px;
    border-left: 4px solid #c5cac3;
}

.me-reply-item {
    margin-bottom: 8px;
    border-bottom: 1px solid #f0f0f0;
}

.me-reply-user {
    color: #78b6f7;
}
</style>