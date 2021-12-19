package cn.vesns.eduservice.listener;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.eduservice.entity.EduSubject;
import cn.vesns.eduservice.entity.vo.ExcelSubjectData;
import cn.vesns.eduservice.service.EduSubjectService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SubjectExcelListener
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-16 23:51
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {


    public EduSubjectService eduSubjectService;

    /**
     * 手动注入service
     * @param eduSubjectService
     */
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        // 数据验空
        if (excelSubjectData == null) {
            throw new VesnsException(20001,"导入课程失败,数据为空");
        }
        // 判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, excelSubjectData.getOneSubjectName());
        // 一级不重复插入数据库
        if (existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(excelSubjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }
        String pid = existOneSubject.getId();
        // 判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, excelSubjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }
        // 二级不重复插入数据库


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name) {
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduSubject::getParentId,"0");
        lambdaQueryWrapper.eq(EduSubject::getTitle,name);

        EduSubject one = eduSubjectService.getOne(lambdaQueryWrapper);
        return one;
    }

    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid) {
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduSubject ::getParentId,pid);
        lambdaQueryWrapper.eq(EduSubject ::getTitle,name);
        EduSubject two = eduSubjectService.getOne(lambdaQueryWrapper);
        return two;
    }

}
