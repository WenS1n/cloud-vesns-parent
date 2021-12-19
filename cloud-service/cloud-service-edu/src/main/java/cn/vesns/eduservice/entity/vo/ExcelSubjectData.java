package cn.vesns.eduservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: vesns vip865047755@126.com
 * @Title: ExcelSubjectData
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-16 23:47
 */
@Data
public class ExcelSubjectData implements Serializable {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
