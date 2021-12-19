package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.entity.vo.OneSubjectVO;
import cn.vesns.eduservice.service.EduSubjectService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-16
 */
@Api(description = "课程分类管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {

    @Resource
    private EduSubjectService eduSubjectService;

    @ApiOperation(value = "批量导入课程分类")
    @PostMapping("/addSubject")
    public ResponseResult addSubject(@RequestParam(value = "file",required = false) MultipartFile file) {
        eduSubjectService.addSubject(file,eduSubjectService);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "查询所有课程分类")
    @GetMapping("/getAllSubject")
    public ResponseResult getAllSubject() {
        List<OneSubjectVO> eduSubjectList = eduSubjectService.getAllSubject();
        return ResponseResult.ok().data("allSubject",eduSubjectList);
    }
}

