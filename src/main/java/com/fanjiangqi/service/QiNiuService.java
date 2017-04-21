package com.fanjiangqi.service;

import com.fanjiangqi.util.ToutiaoUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by fanjiangqi on 2017/3/29.
 */
@Service
public class QiNiuService {

    String accessKey = "xm0W1QAgKeFnxRor07oYVZNGR2xIRlUXqqnw9xTE";
    String secretKey = "gtscYPrEVozyckbZl3rs4wesQWainlDQidym2Ujv";
    String bucket = "jiangqi";

     private  static String QINIU_IMAGE_DOMAIN = "http://onkmukrp4.bkt.clouddn.com/";
    //上传到七牛云，参考七牛云sdk完成
    public String saveImage(MultipartFile file) throws IOException {

        //判断是否上传的是正确图片并重命名图片
        int dosPos = file.getOriginalFilename().lastIndexOf(".");
        if (dosPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dosPos + 1).toLowerCase();//取拓展名
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //System.out.println(upToken);

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
       // String localFilePath = "/home/qiniu/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = null;
        try {
            Response response = uploadManager.put(file.getBytes(), fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
           // System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            if (response.isOK() && response.isJson())
                return QINIU_IMAGE_DOMAIN + fileName;
            else {
                return null;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }

    }
}
