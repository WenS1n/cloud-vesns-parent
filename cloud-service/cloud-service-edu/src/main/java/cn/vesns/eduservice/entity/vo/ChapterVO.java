package cn.vesns.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: ChapterVO
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-19 17:50
 */
@Data
public class ChapterVO {
    private String id;

    private String title;

    private List<VideoVO> children = new ArrayList<VideoVO>();
}
