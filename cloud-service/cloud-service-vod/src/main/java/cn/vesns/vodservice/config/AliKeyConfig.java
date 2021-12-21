package cn.vesns.vodservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: vesns vip865047755@126.com
 * @Title: AliKey
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 17:09
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class AliKeyConfig {

    private String accessKey;

    private String accessKeySecret;




}
