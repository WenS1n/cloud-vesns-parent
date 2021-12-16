package cn.vesns.eduservice.controller;

import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author: vesns vip865047755@126.com
 * @Title: EduLoginController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-15 22:29
 */
@Api(description = "模拟登录")
@RestController
@RequestMapping("/eduuser")
@CrossOrigin
public class EduLoginController {


    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseResult login () {
        return ResponseResult.ok().data("token","admin");
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public ResponseResult info () {
        return ResponseResult.ok().data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }



}
