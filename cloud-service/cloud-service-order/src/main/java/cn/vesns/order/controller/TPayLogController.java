package cn.vesns.order.controller;


import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.order.service.TPayLogService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-21
 */
@RestController
@CrossOrigin
@Api(description = "支付管理")
@RequestMapping("/orderservice/paylog")
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    @ApiOperation(value = "根据订单编号生成二维码")
    @GetMapping("createNative/{orderNo}")
    public ResponseResult createNative(@PathVariable String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        return ResponseResult.ok().data(map);
    }


    @ApiOperation(value = "根据订单编号查询支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public ResponseResult queryPayStatus(@PathVariable String orderNo){
        // 调用微信接口查询支付状态
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            throw new VesnsException(2000,"支付出错！");
        }
        if ("SUCCESS".equals(map.get("grade_state"))) {
            // 支付成功后更改订单状态，记录支付日志
            payLogService.updateOrderStatus(map);
            return ResponseResult.ok().message("支付成功");
        }
        return ResponseResult.ok().code(25000).message("支付中");

    }

}

