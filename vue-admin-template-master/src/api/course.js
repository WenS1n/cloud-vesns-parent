import request from '@/utils/request'

export default {
  //添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/educourse/addCourseInfo`,
      method: 'post',
      data: courseInfo,
    })
  },

  /**
   * 根据id查询课程信息
   * @param {*} id 
   * @returns 
   */
  getCourseInfoById(id) {
    return request({
      url: `/eduservice/educourse/getCourseInfoById/${id}`,
      method: 'get',
    })
  },

  // 修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/educourse/updateCourseInfo`,
      method: 'post',
      data: courseInfo,
    });
  },

  // 根据课程id查询课程发布信息
  getCoursePublishById(id) {
    return request({
      url: `/eduservice/educourse/getCoursePublishById/${id}`,
      method: 'get',
    });
  },

  // 根据课程id更改课程发布状态
  publishCourse(id) {
    return request({
      url: `/eduservice/educourse/publishCourse/${id}`,
      method: 'get',
    });
  },

  // 查询所有课程信息
  getCourseInfo() {
    return request({
      url: `/eduservice/educourse/getCourseInfo`,
      method: 'get',
    });
  },

  // 根据id删除课程相关信息
  deleteCourseInfo(id) {
    return request({
      url: `/eduservice/educourse/deleteCourseInfo/${id}`,
      method: 'delete',
    });
  },
  //带条件分页查询课程信息
  queryPageCourseInfo(current, limit, selectCourseVO) {
    return request({
      url: `/eduservice//educourse/queryPageCourseInfo/${current}/${limit}`,
      method: 'post',
      data: selectCourseVO  //转化json传递
    })
  },

}
