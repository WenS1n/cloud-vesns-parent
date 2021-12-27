package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.EduTeacher;
import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
import cn.vesns.eduservice.entity.vo.CoursePublishVO;
import cn.vesns.eduservice.entity.vo.SelectCourseVO;
import cn.vesns.eduservice.entity.vo.TeacherVO;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.utils.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-18
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {


    @Resource
    private EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("/addCourseInfo")
    public ResponseResult addCourseInfo(@RequestBody CourseInfoFrom courseInfo) {
        String courseId = eduCourseService.addCourseInfo(courseInfo);
        return ResponseResult.ok().data("courseId",courseId);
    }

    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("/getCourseInfoById/{id}")
    public ResponseResult getCourseInfoById(@PathVariable String id) {
        CourseInfoFrom courseInfo = eduCourseService.getCourseInfoById(id);
        return ResponseResult.ok().data("courseInfo",courseInfo);

    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("/updateCourseInfo")
    public ResponseResult updateCourseInfo(@RequestBody CourseInfoFrom courseInfo) {
        eduCourseService.updateCourseInfo(courseInfo);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据课程id查询课程发布信息")
    @GetMapping("/getCoursePublishById/{id}")
    public ResponseResult getCourseInfoByCourseId(@PathVariable String id) {
        CoursePublishVO coursePublishVo = eduCourseService.getCourseInfoByCourseId(id);
        return ResponseResult.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "根据课程id更改课程发布状态")
    @GetMapping("/publishCourse/{id}")
    public ResponseResult publishCourse(@PathVariable String id) {
        EduCourse eduCourse = eduCourseService.getById(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "查询所有课程信息")
    @GetMapping("/getCourseInfo")
    public ResponseResult getCourseInfo() {
        List<EduCourse> list = eduCourseService.list(null);
        return ResponseResult.ok().data("list",list);
    }

    @ApiOperation(value = "条件分页查询课程信息")
    @PostMapping("/queryPageCourseInfo/{current}/{limit}")
    public ResponseResult queryPageCourseInfo(@PathVariable Long current,
                                             @PathVariable Long limit, @RequestBody SelectCourseVO selectCourseVO) {

        String title = selectCourseVO.getTitle();
        String begin = selectCourseVO.getBegin();
        String end = selectCourseVO.getEnd();
        System.out.println("---->" + title + "---->" + begin + "--->" + end);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        Page<EduCourse> page = new Page<>(current, limit);
        eduCourseService.page(page, wrapper);
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();
        return ResponseResult.ok().data("list", records).data("total", total);
    }


    @ApiOperation(value = "根据id删除课程相关信息")
    @DeleteMapping("/deleteCourseInfo/{id}")
    public ResponseResult deleteCourseInfo(@PathVariable String id) {
        eduCourseService.deleteCourseInfo(id);
        return ResponseResult.ok();
    }
}

