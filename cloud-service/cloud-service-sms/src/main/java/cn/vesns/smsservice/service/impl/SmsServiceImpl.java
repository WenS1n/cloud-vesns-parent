package cn.vesns.smsservice.service.impl;

import cn.vesns.config.SmsConfig;
import cn.vesns.smsservice.util.SmsUtil;
import cn.vesns.smsservice.service.SmsService;
import cn.vesns.utils.RandomUtil;
import cn.vesns.utils.RedisUtils;
import cn.vesns.utils.ResponseResult;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SmsServiceImpl
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 22:42
 */
@Service
public class SmsServiceImpl implements SmsService {


    @Resource
    private SmsConfig smsConfig;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public ResponseResult sendSmsCode(String phoneNumber) {

        // 下发手机号码，采用e.164标准，+[国家或地区码][手机号]
        String[] phoneNumbers = {"+86" + phoneNumber};
        // 生成6位随机数字字符串
        String smsCode = RandomUtil.getSixBitRandom();
        // 模板参数：若无模板参数，则设置为空（参数1为随机验证码，参数2为有效时间）
        String[] templateParams = {smsCode, smsConfig.getExpireTime()};
        // 发送短信验证码
        SendStatus[] sendStatuses = SmsUtil.sendSms(smsConfig, templateParams, phoneNumbers);
        if ("Ok".equals(sendStatuses[0].getCode())) {
            // 创建缓存的key
            String key = RedisUtils.createCacheKey(smsConfig.getPhonePrefix(), phoneNumber);
            // 将验证码缓存到redis并设置过期时间
            redisUtils.setCacheObject(key, smsCode, Long.parseLong(smsConfig.getExpireTime()));
            return  ResponseResult.ok();
        } else {
            return ResponseResult.error() ;
        }
    }

    @Override
    public ResponseResult verifySmsCode(String phoneNumber, String smsCode) {
        // 创建key
        String key = RedisUtils.createCacheKey(smsConfig.getPhonePrefix(), phoneNumber);
        // 判断指定key是否存在并且未过期
        if (redisUtils.hasKey(key)) {
            // 验证输入的验证码是否正确
            if (smsCode.equals(redisUtils.getCacheObject(key))) {
                // 验证成功后删除验证码缓存
                redisUtils.delete(key);
                return ResponseResult.ok().message("验证成功");
            } else {
                return ResponseResult.error().message("验证码错误");
            }
        } else {
            return ResponseResult.error().message("验证码已错误");
        }
    }
}
