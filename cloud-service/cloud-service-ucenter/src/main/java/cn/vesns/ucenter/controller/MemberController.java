package cn.vesns.ucenter.controller;



import cn.vesns.ucenter.entity.UcenterMember;
import cn.vesns.ucenter.entity.vo.LoginVo;
import cn.vesns.ucenter.entity.vo.RegisterVo;
import cn.vesns.ucenter.service.MemberService;
import cn.vesns.utils.JwtUtils;
import cn.vesns.utils.ResponseResult;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-20
 */
@RestController
@CrossOrigin
@Api(description = "用户管理")
@RequestMapping("/ucenter/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return ResponseResult.ok().data("token",token);
    }


    @ApiOperation(value = "根据token字符串获取用户信息")
    @GetMapping("getUcenterByToken")
    public ResponseResult getUcenterByToken(HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("---------->"+memberId);
        UcenterMember ucenterMember = memberService.getById(memberId);
        System.out.println("---------->"+ucenterMember);
        String s = JSON.toJSONString(ucenterMember);
        return ResponseResult.ok().data("ucenterMember",s);
    }



}

