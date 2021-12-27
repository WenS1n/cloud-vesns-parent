import request from '@/utils/request'

export default {
    
    //根据视频id获取视频播放凭证
    getPlayAuth(vid){
        return request({
            url: `/eduvod/vod/getPlayAuth/${vid}`,
            method: 'get'
          })
    }
}