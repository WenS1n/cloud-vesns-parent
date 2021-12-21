package cn.vesns.ucenter.service;


import cn.vesns.ucenter.entity.UcenterMember;
import cn.vesns.ucenter.entity.vo.LoginVo;
import cn.vesns.ucenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-20
 */
public interface MemberService extends IService<UcenterMember> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);
}
