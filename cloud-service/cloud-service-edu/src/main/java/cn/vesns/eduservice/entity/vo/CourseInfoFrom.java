package cn.vesns.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: vesns vip865047755@126.com
 * @Title: CourseInfoFrom
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-18 15:58
 */
@Data
public class CourseInfoFrom implements Serializable {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
