package cn.vesns.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SmsConfig
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 23:00
 */
@ConfigurationProperties(prefix = "tencent.sms")
@Configuration
@Data
public class SmsConfig {
    /**
     * 腾讯云API密钥的SecretId
     */
    private String secretId;
    /**
     * 腾讯云API密钥的SecretKey
     */
    private String secretKey;
    /**
     * 短信应用的SDKAppID
     */
    private String appId;
    /**
     * 签名内容
     */
    private String sign;
    /**
     * 模板ID
     */
    private String templateId;
    /**
     * 过期时间
     */
    private String expireTime;
    /**
     * redis存储的key的前缀
     */
    private String phonePrefix;
}
