package cn.vesns.wxlogin.controller;


import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.wxlogin.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-21
 */
@CrossOrigin
@Api(description = "微信登录")
@Controller
@RequestMapping("/api/ucenter/wx")
public class UcenterMemberController {

    @GetMapping("/login")
    public String wxLogin() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        System.out.println("--------"+redirectUrl);
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new VesnsException(20001, e.getMessage());
        }

        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguiguwxlogin");

        return "redirect:" + qrcodeUrl;
    }

}

