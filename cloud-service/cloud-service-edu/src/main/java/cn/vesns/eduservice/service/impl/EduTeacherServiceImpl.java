package cn.vesns.eduservice.service.impl;

import cn.vesns.eduservice.entity.EduTeacher;
import cn.vesns.eduservice.mapper.EduTeacherMapper;
import cn.vesns.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-14
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 分页查询前台讲师展示
     * @param pageParam
     * @return
     */
    @Override
    public Map<String, Object> getTeacherApiPage(Page<EduTeacher> pageParam) {
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduTeacher::getGmtCreate);
        IPage<EduTeacher> teacherIPage = baseMapper.selectPage(pageParam, queryWrapper);
        List<EduTeacher> records = pageParam.getRecords();
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
}
