package cn.vesns.eduservice.service;

import cn.vesns.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author vesns
 * @since 2021-12-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherApiPage(Page<EduTeacher> page);

}
