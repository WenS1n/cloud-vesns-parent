package cn.vesns.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ExcelListener extends AnalysisEventListener<DemoData> {
    //读取每一行数据
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println(demoData);
    }

    //数据读取完做的事
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
