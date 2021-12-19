package cn.vesns.eduservice.service;

import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
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

    void addCourseInfo(CourseInfoFrom courseInfo);
}
