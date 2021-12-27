package cn.vesns.eduservice.api;

import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.EduTeacher;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.eduservice.service.EduTeacherService;
import cn.vesns.utils.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: vesns vip865047755@126.com
 * @Title: TeacherApiController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-21 16:43
 */
@Api(description = "前台讲师展示")
@RestController
@RequestMapping("/eduservice/teacherapi")
@CrossOrigin
public class TeacherApiController {

    @Resource
    EduTeacherService teacherService;

    @Resource
    EduCourseService courseService;

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/getTeacherApiPage/{current}/{limit}")
    public ResponseResult queryPageTeacherList(@PathVariable Long current,
                                               @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        Map<String,Object> map = teacherService.getTeacherApiPage(page);
        return ResponseResult.ok().data(map);
    }

    @ApiOperation(value = "前台查询讲师详情")
    @GetMapping("getTeacherCourseById/{teacherId}")
    public ResponseResult getTeacherCourseById(@PathVariable String teacherId){
        //1讲师信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2相关课程
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduCourse::getTeacherId,teacherId);
        List<EduCourse> courseList = courseService.list(queryWrapper);
        return ResponseResult.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }

}
