package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.client.VodClient;
import cn.vesns.eduservice.entity.EduChapter;
import cn.vesns.eduservice.entity.EduVideo;
import cn.vesns.eduservice.entity.vo.ChapterVO;
import cn.vesns.eduservice.service.EduChapterService;
import cn.vesns.eduservice.service.EduVideoService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-19
 */
@CrossOrigin
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/eduvideo")
public class EduVideoController {

    @Resource
    private EduVideoService videoService;

    @Resource
    private VodClient vodClient;


    @ApiOperation(value = "添加小节信息")
    @PostMapping("/addVideo")
    public ResponseResult addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        System.out.println(eduVideo.toString());
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id删除小节")
    @DeleteMapping("/deleteVideo/{id}")
    public ResponseResult deleteVideo(@PathVariable String id) {

        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (videoSourceId != null) {
            vodClient.deleteVideo(videoSourceId);
        }
        videoService.removeById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id查询小节信息")
    @GetMapping("/getVideoById/{id}")
    public ResponseResult getVideoById(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return ResponseResult.ok().data("eduVideo",eduVideo);
    }

    @ApiOperation(value = "修改章节信息")
    @PostMapping("/updateVideo")
    public ResponseResult updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return ResponseResult.ok();
    }

}

