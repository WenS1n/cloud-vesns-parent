package cn.vesns.order.controller;


import cn.vesns.order.entity.TOrder;
import cn.vesns.order.service.TOrderService;
import cn.vesns.utils.JwtUtils;
import cn.vesns.utils.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-21
 */
@CrossOrigin
@Api(description = "订单模块")
@RestController
@RequestMapping("/orderservice/order")
public class TOrderController {

    @Resource
    private TOrderService orderService;


    @ApiOperation(value = "根据课程id、用户id创建订单")
    @PostMapping("/createOrder/{courseId}")
    public ResponseResult createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberID = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,memberID);
        return ResponseResult.ok().data("orderNo",orderNo);

    }


    @ApiOperation(value = "根据订单编号查询订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public ResponseResult createOrder(@PathVariable String orderNo) {
        LambdaQueryWrapper<TOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(TOrder::getOrderNo,orderNo);
        TOrder order = orderService.getOne(orderLambdaQueryWrapper);
        return ResponseResult.ok().data("order",order);
    }

    @ApiOperation(value = "根据课程id，用户id查询是已购买，远程调用")
    @GetMapping("/isBuyCourse/{orderNo}/{memberId}")
    public Boolean isBuyCourse(@PathVariable String orderNo,
                                      @PathVariable String memberId) {
        LambdaQueryWrapper<TOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(TOrder::getStatus,1);
        orderLambdaQueryWrapper.eq(TOrder::getCourseId,orderNo);
        orderLambdaQueryWrapper.eq(TOrder::getMemberId,memberId);
        int count = orderService.count(orderLambdaQueryWrapper);
        return count > 0;

    }
}

