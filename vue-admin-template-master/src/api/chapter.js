import request from '@/utils/request'

export default {

  /**
   * 根据课程id查询章节、小节信息
   * @param {*} id 
   * @returns 
   */
   getChapterVideoByCourseId(courseId) {
    return request({
      url: `/eduservice/educhapter/getChapterVideoByCourseId/${courseId}`,
      method: 'get',
    })
  },
  /**
   * 添加章节
   * @param {*} chapter 
   * @returns 
   */
  addChapter(chapter) {
    return request({
        url: `/eduservice/educhapter/addChapter`,
        method: 'post',
        data: chapter,
      })
  },
  /**
   * 根据id删除章节
   * @param {}} id 
   * @returns 
   */
  deleteChapter(id) {
    return request({
        url: `/eduservice/educhapter/deleteChapter/${id}`,
        method: 'delete',
      })
  },

  getChapterById(id) {
    return request({
        url: `/eduservice/educhapter/getChapterById/${id}`,
        method: 'get',
      })
  },

  updateChapter(chapter) {
    return request({
        url: `/eduservice/educhapter/updateChapter`,
        method: 'post',
        data: chapter,
      })
  },



}
