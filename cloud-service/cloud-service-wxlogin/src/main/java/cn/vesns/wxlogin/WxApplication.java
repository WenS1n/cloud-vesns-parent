package cn.vesns.wxlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: vesns vip865047755@126.com
 * @Title: WxApplication
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-21 12:22
 */
@EnableDiscoveryClient
@MapperScan("cn.vesns.wxlogin.mapper")
@SpringBootApplication
@ComponentScan(basePackages = {"cn.vesns"})
@EnableSwagger2
public class WxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class,args);
    }
}
