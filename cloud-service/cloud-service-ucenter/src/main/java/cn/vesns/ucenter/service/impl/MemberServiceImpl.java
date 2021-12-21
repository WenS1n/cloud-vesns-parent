package cn.vesns.ucenter.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.config.SmsConfig;

import cn.vesns.ucenter.entity.UcenterMember;
import cn.vesns.ucenter.entity.vo.LoginVo;
import cn.vesns.ucenter.entity.vo.RegisterVo;
import cn.vesns.ucenter.mapper.MemberMapper;
import cn.vesns.ucenter.service.MemberService;
import cn.vesns.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-20
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, UcenterMember> implements MemberService {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SmsConfig smsConfig;

    /**
     * 用户注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 验空
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        System.out.println(registerVo.toString());
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
            throw new VesnsException(20001, "注册信息有误");
        }
        LambdaQueryWrapper<UcenterMember> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLambdaQueryWrapper.eq(UcenterMember::getMobile, mobile);
        Integer integer = baseMapper.selectCount(memberLambdaQueryWrapper);
        System.out.println("---------->integer"+integer);
        if (integer > 0) {
            throw new VesnsException(20001, "手机号重复");
        }

        String key = RedisUtils.createCacheKey(smsConfig.getPhonePrefix(), mobile);
        if (redisUtils.hasKey(key)) {
            System.out.println("---------->" + key);
            // 验证输入的验证码是否正确
            if (!(code.equals(redisUtils.getCacheObject(key)))) {
                System.out.println("---------------redisUtils" + redisUtils.getCacheObject(key));
                // 验证成功后删除验证码缓存
                redisUtils.delete(key);
                throw new VesnsException(20001, "验证码错误");
            }
        }
        String md5Password = MD5.encrypt(password);
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setPassword(md5Password);
        member.setMobile(mobile);
        member.setIsDisabled(false);
        member.setAvatar("https://cloud-beakermall.oss-cn-chengdu.aliyuncs.com/2021/12/20/e14f91dc-d9cc-41bb-86e1-ddfae35acdcarabbit.jpg");
        System.out.println("--------->"+member);
        baseMapper.insert(member);
    }

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @Override
    public String login(LoginVo loginVo) {
        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new VesnsException(20001, "手机号或密码错误");
        }
        LambdaQueryWrapper<UcenterMember> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLambdaQueryWrapper.eq(UcenterMember::getMobile,mobile);
        UcenterMember member = baseMapper.selectOne(memberLambdaQueryWrapper);
        if (member == null) {
            throw new VesnsException(20001, "手机号或密码错误");
        }
        String md5Password = MD5.encrypt(password);
        if (!md5Password.equals(member.getPassword())) {
            throw new VesnsException(20001, "手机号或密码错误");
        }
        // 生成token
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }
}