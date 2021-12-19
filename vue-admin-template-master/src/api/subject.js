import request from '@/utils/request'

export default {
    //添加讲师
    getAllSubject(){
      return request({
          url: `/eduservice/edu-subject/getAllSubject`,
          method: 'get',
        }) 
  }

}
