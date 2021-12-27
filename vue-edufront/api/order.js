import request from '@/utils/request'

export default {
    
    //根据课程id、用户id创建订单
    createOrder(courseId){
        return request({
            url: `/orderservice/order/createOrder/${courseId}`,
            method: 'post'
          })
    },
    //根据订单编号查询订单信息
    getOrderInfo(orderNo){
        return request({
            url: `/orderservice/order/getOrderInfo/${orderNo}`,
            method: 'get'
          })
    }



}