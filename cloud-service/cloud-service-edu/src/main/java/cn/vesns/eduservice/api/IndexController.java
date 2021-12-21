package cn.vesns.eduservice.api;

import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.EduTeacher;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.eduservice.service.EduTeacherService;
import cn.vesns.utils.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: IndexController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 20:50
 */
@RestController
@CrossOrigin
@Api(description = "首页显示")
@RequestMapping("/eduservice/index")
public class IndexController {
    @Resource
    private EduCourseService courseService;

    @Resource
    private EduTeacherService teacherService;

    @ApiOperation(value = "首页展示课程信息和讲师信息")
    @GetMapping("/getCourseTeacherList")
    public ResponseResult getCourseTeacherList() {
        LambdaQueryWrapper<EduCourse> eduCourseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduCourseLambdaQueryWrapper.orderByDesc(EduCourse::getGmtCreate);
        eduCourseLambdaQueryWrapper.last("LIMIT 8");
        List<EduCourse> courseList = courseService.list(eduCourseLambdaQueryWrapper);

        LambdaQueryWrapper<EduTeacher> eduTeacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eduTeacherLambdaQueryWrapper.orderByDesc(EduTeacher::getGmtCreate);
        eduTeacherLambdaQueryWrapper.last("LIMIT 4");
        List<EduTeacher> teacherList = teacherService.list(eduTeacherLambdaQueryWrapper);
        return ResponseResult.ok().data("courseList",courseList).data("teacherList",teacherList);


    }
}
