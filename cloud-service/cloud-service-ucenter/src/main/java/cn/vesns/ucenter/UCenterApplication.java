package cn.vesns.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: vesns vip865047755@126.com
 * @Title: UCenterApplication
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 23:23
 */

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("cn.vesns.ucenter.mapper")
@SpringBootApplication
@ComponentScan(basePackages = {"cn.vesns"})
@EnableSwagger2
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
