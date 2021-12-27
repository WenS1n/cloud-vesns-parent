package cn.vesns.order.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.order.client.EduClient;
import cn.vesns.order.client.UcenterClient;
import cn.vesns.order.entity.TOrder;
import cn.vesns.order.mapper.TOrderMapper;
import cn.vesns.order.service.TOrderService;
import cn.vesns.order.util.OrderNoUtil;
import cn.vesns.vo.CourseWebVoForOrder;
import cn.vesns.vo.UcenterMemberForOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-21
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Resource
    EduClient eduClient;

    @Autowired
    UcenterClient centerClient;

    /**
     * 根据课程id，用户id生成订单编号
     * @param courseId
     * @param memberID
     * @return
     */
    @Override
    public String createOrder(String courseId, String memberID) {

        CourseWebVoForOrder courseInfoForOrder = eduClient.getCourseInfoForOrder(courseId);
        if (courseInfoForOrder == null) {
            throw new VesnsException(20001,"获取课程信息失败");
        }
        UcenterMemberForOrder ucenterInfoForOrder = centerClient.getUcenterInfoForOrder(memberID);
        if (ucenterInfoForOrder == null) {
            throw new VesnsException(20001,"获取用户信息失败");
        }
        // 生成订单编号
        String orderNo = OrderNoUtil.getOrderNo();
        // 封装数据存入数据库
        TOrder order = new TOrder();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoForOrder.getTitle());
        order.setCourseCover(courseInfoForOrder.getCover());
        order.setTeacherName(courseInfoForOrder.getTeacherName());
        order.setTotalFee(courseInfoForOrder.getPrice());
        order.setMemberId(memberID);
        order.setMobile(ucenterInfoForOrder.getMobile());
        order.setNickname(ucenterInfoForOrder.getNickname());
        order.setStatus(0);// 未支付
        order.setPayType(1);// 1微信 2支付宝
        baseMapper.insert(order);


        return orderNo;
    }
}
