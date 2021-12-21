package cn.vesns.eduservice.service;

import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
import cn.vesns.eduservice.entity.vo.CoursePublishVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoFrom courseInfo);

    CourseInfoFrom getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoFrom courseInfo);

    CoursePublishVO getCourseInfoByCourseId(String id);

    void deleteCourseInfo(String id);
}
