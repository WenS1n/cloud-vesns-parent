package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        eduCourseService.addCourseInfo(courseInfo);
        return ResponseResult.ok();
    }


}

