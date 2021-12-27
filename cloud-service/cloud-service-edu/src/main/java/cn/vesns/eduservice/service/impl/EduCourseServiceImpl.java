package cn.vesns.eduservice.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.eduservice.client.VodClient;
import cn.vesns.eduservice.entity.EduChapter;
import cn.vesns.eduservice.entity.EduCourse;
import cn.vesns.eduservice.entity.EduCourseDescription;
import cn.vesns.eduservice.entity.EduVideo;
import cn.vesns.eduservice.entity.vo.CourseInfoFrom;
import cn.vesns.eduservice.entity.vo.CoursePublishVO;
import cn.vesns.eduservice.entity.vo.CourseQueryVo;
import cn.vesns.eduservice.entity.vo.CourseWebVo;
import cn.vesns.eduservice.mapper.EduCourseMapper;
import cn.vesns.eduservice.service.EduChapterService;
import cn.vesns.eduservice.service.EduCourseDescriptionService;
import cn.vesns.eduservice.service.EduCourseService;
import cn.vesns.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private EduVideoService videoService;

    @Resource
    private EduChapterService chapterService;

    @Resource
    private VodClient vodClient;

    /**
     * 添加课程信息
     *
     * @param courseInfo
     * @return
     */
    @Override
    public String addCourseInfo(CourseInfoFrom courseInfo) {

        // 添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new VesnsException(20001, "创建课程失败");
        }
        // 获取课程id
        String id = eduCourse.getId();

        // 添加课程描述信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(id);
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(courseDescription);


        return id;
    }

    /**
     * 根据id查询课程信息and详情描述
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoFrom getCourseInfoById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoFrom courseInfoFrom = new CourseInfoFrom();
        BeanUtils.copyProperties(eduCourse, courseInfoFrom);
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        courseInfoFrom.setDescription(courseDescription.getDescription());

        return courseInfoFrom;

    }

    /**
     * 修改课程信息
     *
     * @param courseInfo
     */
    @Override
    public void updateCourseInfo(CourseInfoFrom courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new VesnsException(20001, "修改课程失败");
        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.updateById(courseDescription);


    }

    /**
     * 根据课程id查询发布信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVO getCourseInfoByCourseId(String id) {
        CoursePublishVO coursePublishVO = baseMapper.getCourseInfoByCourseId(id);
        return coursePublishVO;
    }

    /**
     * 根据id删除课程相关信息！
     * @param id 课程id
     */
    @Override
    public void deleteCourseInfo(String id) {
        //删除视频
        LambdaQueryWrapper<EduVideo> idLambdaQueryWrapper = new LambdaQueryWrapper<>();
        idLambdaQueryWrapper.eq(EduVideo::getCourseId,id);
        List<EduVideo> list = videoService.list(idLambdaQueryWrapper);
        // 遍历视频id
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EduVideo eduVideo = list.get(i);
            videoIds.add(eduVideo.getVideoSourceId());
        }
        if (videoIds.size() > 0) {
            vodClient.delVideoList(videoIds);
        }
        // -删除章节-删除课程描述-删除课程
        LambdaQueryWrapper<EduVideo> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(EduVideo::getCourseId, id);
        videoService.remove(videoLambdaQueryWrapper);

        LambdaQueryWrapper<EduChapter> chapterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chapterLambdaQueryWrapper.eq(EduChapter::getCourseId, id);
        // 删除章节
        chapterService.remove(chapterLambdaQueryWrapper);
        // 删除详情描述
        courseDescriptionService.removeById(id);
        // 删除课程
        int delete = baseMapper.deleteById(id);
        if (delete == 0) {
            throw new VesnsException(20001, "删除课程失败");
        }
    }

    @Override
    public Map<String, Object> getCourseApiPageVo(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        //1 取出查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();
        //2 验空，不为空拼写到查询条件
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();

        if(!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq(EduCourse::getSubjectParentId,subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq(EduCourse::getSubjectId,subjectId);
        }
        if(!StringUtils.isEmpty(buyCountSort)){
            queryWrapper.orderByDesc(EduCourse::getBuyCount);
        }
        if(!StringUtils.isEmpty(gmtCreateSort)){
            queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }
        if(!StringUtils.isEmpty(priceSort)){
            queryWrapper.orderByDesc(EduCourse::getPrice);
        }
        queryWrapper.eq(EduCourse::getStatus,"Normal");
        //3 分页查询
        baseMapper.selectPage(pageParam,queryWrapper);
        //4 封装数据
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //根据课程id查询课程相关信息
    @Override
    public CourseWebVo getCourseWebVo(String id) {
        CourseWebVo courseWebVo = baseMapper.getCourseWebVo(id);
        return courseWebVo;
    }

}
