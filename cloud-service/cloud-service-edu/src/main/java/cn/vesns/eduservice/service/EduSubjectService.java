package cn.vesns.eduservice.service;

import cn.vesns.eduservice.entity.EduSubject;
import cn.vesns.eduservice.entity.vo.OneSubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-16
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubjectVO> getAllSubject();

}
