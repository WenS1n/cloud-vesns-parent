import request from '@/utils/request'

export default {
    
    //前台分页查询讲师列表
    getTeacherApiPage(current,limit){
        return request({
            url: `/eduservice/teacherapi/getTeacherApiPage/${current}/${limit}`,
            method: 'get'
          })
    },
    //前台查询讲师详情
    getTeacherCourseById(teacherId){
        return request({
            url: `/eduservice/teacherapi/getTeacherCourseById/${teacherId}`,
            method: 'get'
          })
    }



}