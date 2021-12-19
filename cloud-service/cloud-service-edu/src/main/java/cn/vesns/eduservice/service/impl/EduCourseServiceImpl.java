package cn.vesns.eduservice.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.EduCourseDescription;
import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
import cn.vesns.eduservice.mapper.EduCourseMapper;
import cn.vesns.eduservice.service.EduCourseDescriptionService;
import cn.vesns.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Resource
    private EduCourseDescriptionService courseDescriptionService;
    /**
     * 添加课程信息
     * @param courseInfo
     */
    @Override
    public void addCourseInfo(CourseInfoFrom courseInfo) {

        // 添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new VesnsException(20001,"创建课程失败");
        }
        // 获取课程id
        String id = eduCourse.getId();

        // 添加课程描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(courseDescription);


    }
}
