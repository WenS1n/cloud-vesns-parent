package cn.vesns.smsservice.controller;

import cn.vesns.config.SmsConfig;
import cn.vesns.smsservice.service.SmsService;
import cn.vesns.utils.RedisUtils;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SmsController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 22:24
 */
@RestController
@CrossOrigin
@Api(description = "短信发送")
@RequestMapping("/smsservice/sms")
public class SmsController {

    @Resource
    private SmsService smsService;

    @Resource
    private SmsConfig smsConfig;

    @Resource
    private RedisUtils redisUtils;



    @ApiOperation(value = "发送短信")
    @GetMapping("/send/{phone}")
    public ResponseResult sendSmsCode(@PathVariable(value = "phone") String phone) {
        // 根据手机号到redis查询验证码
        // TODO 此处未生效！
        String key = RedisUtils.createCacheKey(smsConfig.getPhonePrefix(), phone);
        // 验证验证码是否存在
        if (redisUtils.hasKey(key)) {
            return ResponseResult.error().message("您的获取验证码请求过快！");
        }else {
            // 如果不存在，则生成验证码
            // 调用接口发送短信
            ResponseResult responseResult = smsService.sendSmsCode(phone);
            // 发送成功，验证码存入redis，有效期5分钟
            return responseResult;
        }
    }

    @ApiOperation(value = "验证短信")
    @PostMapping("/verify")
    public ResponseResult verifySmsCode(@RequestParam(value = "phoneNumber") String phoneNumber,
                                                @RequestParam(value = "smsCode") String smsCode) {
        return smsService.verifySmsCode(phoneNumber, smsCode);
    }


}
