package cn.vesns.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: vesns vip865047755@126.com
 * @Title: MenberVO
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 23:32
 */
@Data
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

}
