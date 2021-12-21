package cn.vesns.smsservice.service;

import cn.vesns.utils.ResponseResult;

public interface SmsService {


    /**
     * 发送短信验证码
     *
     * @param phoneNumber 手机号
     * @return
     */
    ResponseResult sendSmsCode(String phoneNumber);

    /**
     * 验证短信验证码
     * @param phoneNumber 手机号
     * @param smsCode     短信验证码
     * @return
     */
    ResponseResult verifySmsCode(String phoneNumber, String smsCode);

}
