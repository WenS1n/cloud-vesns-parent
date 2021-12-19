package cn.vesns.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: TwoSubjectVO
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-18 14:28
 */
@Data
public class TwoSubjectVO {

    @ApiModelProperty(value = "类别id")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;



}
