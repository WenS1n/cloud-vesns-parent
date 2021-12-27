import request from '@/utils/request'

export default {
    
    //带条件分页查询课程列表
    getCourseApiPageVo(current,limit,courseQuery){
        return request({
            url: `/eduservice/courseapi/getCourseApiPageVo/${current}/${limit}`,
            method: 'post',
            data:courseQuery
          })
    },
    //根据课程id查询课程相关信息
    getCourseWebInfo(courseId){
        return request({
            url: `/eduservice/courseapi/getCourseWebInfo/${courseId}`,
            method: 'get'
          })
    }



}