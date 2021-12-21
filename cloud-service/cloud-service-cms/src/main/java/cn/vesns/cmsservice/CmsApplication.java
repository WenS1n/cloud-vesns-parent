package cn.vesns.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: vesns vip865047755@126.com
 * @Title: CmsApplication
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 19:32
 */
@EnableSwagger2
@MapperScan("cn.vesns.cmsservice.mapper")
@ComponentScan(basePackages = {"cn.vesns"})
@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
