package cn.vesns.eduservice.service.impl;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.eduservice.entity.EduSubject;
import cn.vesns.eduservice.entity.vo.ExcelSubjectData;
import cn.vesns.eduservice.entity.vo.OneSubjectVO;
import cn.vesns.eduservice.entity.vo.TwoSubjectVO;
import cn.vesns.eduservice.listener.SubjectExcelListener;
import cn.vesns.eduservice.mapper.EduSubjectMapper;
import cn.vesns.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author vesns
 * @since 2021-12-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 批量导入课程分类
     *
     * @param file
     * @param eduSubjectService
     */
    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,
                    new SubjectExcelListener(eduSubjectService))
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new VesnsException(20001, "导入课程失败");
        }
    }

    /**
     * 查询所有课程分类
     *
     * @return
     */
    @Override
    public List<OneSubjectVO> getAllSubject() {

        // 查询所有一级分类
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduSubject::getParentId, "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(lambdaQueryWrapper);

        // 查询所有二级分类
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.ne(EduSubject::getParentId, "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(lambdaQueryWrapper2);
        // 封装一级分类
        List<OneSubjectVO> allSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 取出一级分类
            EduSubject oneSubject = oneSubjectList.get(i);
            // 转化为VO
            OneSubjectVO oneSubjectVO = new OneSubjectVO();
            BeanUtils.copyProperties(oneSubject, oneSubjectVO);
            allSubjectList.add(oneSubjectVO);

            // 找到与一级分类有关的二级分类进行封装

            List<TwoSubjectVO> twoSubjectVOs = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                // 取出二级分类
                EduSubject twoSubject = twoSubjectList.get(m);
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubjectVO twoSubjectVO = new TwoSubjectVO();
                    BeanUtils.copyProperties(twoSubject, twoSubjectVO);
                    twoSubjectVOs.add(twoSubjectVO);
                }
            }
            oneSubjectVO.setChildren(twoSubjectVOs);
        }
        return allSubjectList;

    }
}
