package cn.vesns.eduservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    //设置表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
