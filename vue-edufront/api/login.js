import request from '@/utils/request'

export default {
    //用户登录
    login(loginVo){
        return request({
            url: `/ucenter/member/login`,
            method: 'post',
            data:loginVo
          })
    },
    //根据token字符串获取用户信息
    getUcenterByToken(){
        return request({
            url: `/ucenter/member/getUcenterByToken`,
            method: 'get'
          })
    }
}