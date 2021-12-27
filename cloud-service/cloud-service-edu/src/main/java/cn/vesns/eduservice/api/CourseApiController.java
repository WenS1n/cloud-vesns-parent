package cn.vesns.eduservice.api;

import cn.vesns.eduservice.client.OrderClient;
import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.vo.ChapterVO;
import cn.vesns.eduservice.entity.vo.CourseQueryVo;
import cn.vesns.eduservice.entity.vo.CourseWebVo;
import cn.vesns.eduservice.entity.vo.SelectCourseVO;
import cn.vesns.eduservice.service.EduChapterService;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.utils.JwtUtils;
import cn.vesns.utils.ResponseResult;
import cn.vesns.vo.CourseWebVoForOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: vesns vip865047755@126.com
 * @Title: CourseApiController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-21 17:43
 */
@Api(description = "前台课程展示")
@RestController
@RequestMapping("/eduservice/courseapi")
@CrossOrigin
public class CourseApiController {

    @Resource
    EduCourseService courseService;

    @Resource
    EduChapterService chapterService;

    @Resource
    OrderClient orderClient;

    @ApiOperation(value = "带条件分页查询课程列表")
    @PostMapping("getCourseApiPageVo/{current}/{limit}")
    public ResponseResult getCourseApiPageVo(@PathVariable Long current,
                                @PathVariable Long limit,
                                @RequestBody CourseQueryVo courseQueryVo){
        Page<EduCourse> page = new Page<>(current,limit);
        Map<String,Object> map = courseService.getCourseApiPageVo(page,courseQueryVo);
        return ResponseResult.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程相关信息")
    @GetMapping("getCourseWebInfo/{courseId}")
    public ResponseResult getCourseWebInfo(@PathVariable String courseId, HttpServletRequest request){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        System.out.println("courseWebVo--->"+courseWebVo);
        //2查询课程大纲信息
        List<ChapterVO> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        // 根据课程id，用户id查询用户是否已经购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Boolean isBuyCourse = orderClient.isBuyCourse(courseId, memberId);


        return ResponseResult.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuyCourse",isBuyCourse);
    }




    @ApiOperation(value = "根据课程id查询课程相关信息跨模块")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(
            @PathVariable("courseId") String courseId){
        //1 查询课程相关信息存入CourseWebVo
        CourseWebVo courseWebVo = courseService.getCourseWebVo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }



}
