import request from  '@/request'
export function upload(formdata) {
    console.log("====================");
    return request({
        url:'/upload',
        method:'post',
        data: formdata
    })
}