package cn.vesns.baseservice.handler;

import cn.vesns.utils.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: vesns vip865047755@126.com
 * @Title: GlobalExceptionHandler
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-15 21:01
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseResult error(Exception e) {
        e.printStackTrace();
        return ResponseResult.error().message(e.getMessage());
    }

    @ExceptionHandler(VesnsException.class)
    @ResponseBody
    public ResponseResult error(VesnsException e) {
        e.printStackTrace();
        return ResponseResult.error().message(e.getMessage()).code(e.getCode());
    }

}
