import request from '@/utils/request'

export default {
    
    //查询所有课程分类
    getAllSubject(){
        return request({
            // url: `/eduservice/edusubject/getAllSubject`,
            url: `/eduservice/edu-subject/getAllSubject`,
            method: 'get'
          })
    }


}