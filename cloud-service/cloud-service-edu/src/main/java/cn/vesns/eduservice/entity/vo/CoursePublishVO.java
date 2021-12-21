package cn.vesns.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: vesns vip865047755@126.com
 * @Title: CoursePublicshVO
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-19 22:42
 */
@Data
public class CoursePublishVO implements Serializable {
    private String id;

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;

}
