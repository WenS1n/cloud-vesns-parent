package cn.vesns.vodservice.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author: vesns vip865047755@126.com
 * @Title: AliyunVodSDKUtil
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 11:05
 */
public class AliyunVodSDKUtil {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
