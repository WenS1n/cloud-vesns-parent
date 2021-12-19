package cn.vesns.baseservice.handler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: vesns vip865047755@126.com
 * @Title: VesnsException
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-15 21:10
 */
@Data
public class VesnsException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;


    public VesnsException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "VesnsException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
