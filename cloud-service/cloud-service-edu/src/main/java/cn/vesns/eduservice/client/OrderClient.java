package cn.vesns.eduservice.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("cloud-service-order")
public interface OrderClient {


    @GetMapping("/orderservice/order/isBuyCourse/{orderNo}/{memberId}")
    public Boolean isBuyCourse(@PathVariable("courseId") String orderNo,
                               @PathVariable("memberId") String memberId);
}
