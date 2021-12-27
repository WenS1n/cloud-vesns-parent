package cn.vesns.order.service;

import cn.vesns.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-21
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String memberID);
}
