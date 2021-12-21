import request from '@/utils/request'

export default {
    
    //用户注册
    register(registerVo){
        return request({
            url: `/ucenter/member/register`,
            method: 'post',
            data:registerVo
          })
    },
    //短信发送
    sendSmsPhone(phone){
        return request({
            url: `/smsservice/sms/send/${phone}`,
            method: 'get'
          })
    }
}