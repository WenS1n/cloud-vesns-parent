package cn.vesns.eduservice.service;

import cn.vesns.eduservice.entity.EduChapter;
import cn.vesns.eduservice.entity.vo.ChapterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-19
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);
}
