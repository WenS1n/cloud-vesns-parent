package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.entity.EduChapter;
import cn.vesns.eduservice.entity.vo.ChapterVO;
import cn.vesns.eduservice.service.EduChapterService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-19
 */
@Api(description = "章节管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/educhapter")
public class EduChapterController {

    @Resource
    private EduChapterService chapterService;

    @ApiOperation(value = "根据课程id查询章节、小节信息")
    @GetMapping("/getChapterVideoByCourseId/{courseId}")
    public ResponseResult getChapterVideoByCourseId(@PathVariable String courseId) {
        List<ChapterVO> chapterVOList = chapterService.getChapterVideoByCourseId(courseId);
        return ResponseResult.ok().data("chapterVideoList",chapterVOList);
    }

    @ApiOperation(value = "添加章节信息")
    @PostMapping("/addChapter")
    public ResponseResult addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据课程id删除章节")
    @DeleteMapping("/deleteChapter/{id}")
    public ResponseResult deleteChapter(@PathVariable String id) {
        chapterService.removeById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id查询章节信息")
    @GetMapping("/getChapterById/{id}")
    public ResponseResult getChapterById(@PathVariable String id) {
        EduChapter eduChapter = chapterService.getById(id);
        return ResponseResult.ok().data("eduChapter",eduChapter);
    }

    @ApiOperation(value = "修改章节信息")
    @PostMapping("/updateChapter")
    public ResponseResult updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return ResponseResult.ok();
    }







}

