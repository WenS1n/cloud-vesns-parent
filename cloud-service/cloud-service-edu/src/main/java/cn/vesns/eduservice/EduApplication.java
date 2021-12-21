package cn.vesns.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: vesns vip865047755@126.com
 * @Title: EduApplication
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-14 23:32
 */


@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("cn.vesns.eduservice.mapper")
@SpringBootApplication
@ComponentScan(basePackages = {"cn.vesns"})
@EnableSwagger2
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
