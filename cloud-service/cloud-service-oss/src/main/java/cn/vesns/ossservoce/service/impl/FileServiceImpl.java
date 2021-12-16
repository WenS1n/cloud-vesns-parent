package cn.vesns.ossservoce.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.ossservoce.service.FileService;
import cn.vesns.ossservoce.util.ConstantPropertiesUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: vesns vip865047755@126.com
 * @Title: FileServiceImpl
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-16 0:55
 */
@Service
public class FileServiceImpl implements FileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadFileOss(MultipartFile file) {

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtil.END_POINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret =  ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName =  ConstantPropertiesUtil.BUCKET_NAME;
        String fileName = file.getOriginalFilename();

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            fileName = UUID.randomUUID().toString()+fileName;
            String path = new DateTime().toString("yyyy/MM/dd");
            fileName = path+"/"+fileName;
            ossClient.putObject(bucketName, fileName,inputStream);
            ossClient.shutdown();
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new VesnsException(20001,"上传失败");
        }

    }
}
