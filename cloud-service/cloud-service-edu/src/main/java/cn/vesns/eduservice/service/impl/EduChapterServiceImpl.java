package cn.vesns.eduservice.service.impl;

import cn.vesns.eduservice.entity.EduChapter;
import cn.vesns.eduservice.entity.EduVideo;
import cn.vesns.eduservice.entity.vo.ChapterVO;
import cn.vesns.eduservice.entity.vo.VideoVO;
import cn.vesns.eduservice.mapper.EduChapterMapper;
import cn.vesns.eduservice.service.EduChapterService;
import cn.vesns.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService videoService;

    /**
     * 根据课程id查询章节、小节信息
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {

        // 根据courseId查询章节集合信息
        LambdaQueryWrapper<EduChapter> chapterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chapterLambdaQueryWrapper.eq(EduChapter::getCourseId,courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterLambdaQueryWrapper);
        // 根据courseId查询小节集合信息
        LambdaQueryWrapper<EduVideo> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(EduVideo::getCourseId,courseId);
        List<EduVideo> videoList = videoService.list(videoLambdaQueryWrapper);
        // 遍历章节信息、封装章节信息
        List<ChapterVO> chapterVOList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            chapterVOList.add(chapterVO);
            // 遍历章节关联小节信息进行封装
            List<VideoVO> VideoVOList = new ArrayList<>();
            for (int m = 0; m < videoList.size(); m++) {
                EduVideo eduVideo = videoList.get(m);
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    VideoVOList.add(videoVO);
                }
                chapterVO.setChildren(VideoVOList);
            }

        }




        return chapterVOList;
    }
}
