package cn.vesns.smsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SmsApplication
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 22:17
 */
@ComponentScan(basePackages = {"cn.vesns"})
@EnableSwagger2
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }

}
