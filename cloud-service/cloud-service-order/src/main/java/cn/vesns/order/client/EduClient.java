package cn.vesns.order.client;

import cn.vesns.vo.CourseWebVoForOrder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: vesns vip865047755@126.com
 * @Title: EduClient
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-22 15:52
 */
@Component
@FeignClient("cloud-service-edu")
public interface EduClient {

//    @GetMapping("/eduservice/courseapi/getCourseInfoForOrder/{courseId}")
//    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);

    @HystrixCommand
    @GetMapping("/eduservice/courseapi/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);
}
