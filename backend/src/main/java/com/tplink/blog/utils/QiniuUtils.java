package com.tplink.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xyd
 * @create 2022-12-06 21:47
 */
@Component
public class QiniuUtils {

    public static final String url = "https://img.static-degeblog.cn/";

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.accessSecrtKey}")
    private String accessSecrtKey;

    public boolean upload(MultipartFile file, String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        // 图片上传空间名
        String bocket = "degeblog";
        try {
            byte[] bytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecrtKey);
            String uploadToken = auth.uploadToken(bocket);
            Response response = uploadManager.put(bytes, fileName, uploadToken);
            Object parse = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
