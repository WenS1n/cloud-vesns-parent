package cn.vesns.order.client;

import cn.vesns.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: vesns vip865047755@126.com
 * @Title: UcenterClient
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-22 16:00
 */
@Component
@FeignClient("cloud-servicer-ucenter")
public interface UcenterClient {


    @GetMapping("/ucenter/member/getUcenterInfoForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterInfoForOrder(@PathVariable("memberId") String memberId);
}
