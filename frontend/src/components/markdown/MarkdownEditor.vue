<template>
    <mavon-editor
    class='me-editor'
    ref = 'md'
    v-model = 'editor.value'
    v-bind= 'editor'
    @imgAdd = 'imgAdd'
    >
    </mavon-editor>
</template>

<script>
import {mavonEditor} from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
import {upload} from '@/api/upload';
export default {
    name: 'MarkdownEditor',
    props: {
        editor: Object
    },
    components: {
        'mavon-editor': mavonEditor,
    },
    mounted() {
        this.$set(this.$editor, 'ref', this.$refs.md);
    },
    methods: {
        imgAdd(pos, $file) {
            
            let that = this;
            let formdata = new FormData();
            formdata.append("image", $file);
            upload(formdata).then(
                data => {
                    
                    if (data.data.success) {
                        
                        that.$refs.md.$img2Url(pos, data.data.data);
                    } else {
                        that.$message({
                            message: data.data.msg,
                            type: 'error',
                            showClose: true
                        })
                    }
                }
            ).catch( err => {
                that.$message({
                    message: err,
                    type: 'error',
                    showClose: true
                })
            })
        }
    }

}
</script>

<style scoped>
.me-editor {
    z-index: 6 !important;
}
.v-note-wrapper.fullscreen {
    top: 60px !important
}
</style>