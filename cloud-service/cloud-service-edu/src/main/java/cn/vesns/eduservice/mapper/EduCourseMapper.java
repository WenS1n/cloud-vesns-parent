package cn.vesns.eduservice.mapper;

import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.vo.CoursePublishVO;
import cn.vesns.eduservice.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author vesns
 * @since 2021-12-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVO getCourseInfoByCourseId(String id);

    CourseWebVo getCourseWebVo(String id);
}
