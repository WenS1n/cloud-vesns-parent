package cn.vesns.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: vesns vip865047755@126.com
 * @Title: SelectCourseVO
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 0:09
 */
@Data
public class SelectCourseVO implements Serializable {

    @ApiModelProperty(value = "课程标题,模糊查询")
    private String title;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
