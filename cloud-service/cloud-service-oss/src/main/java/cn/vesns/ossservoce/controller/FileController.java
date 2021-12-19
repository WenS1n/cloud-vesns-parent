package cn.vesns.ossservoce.controller;

import cn.vesns.ossservoce.service.FileService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: vesns vip865047755@126.com
 * @Title: FileController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-16 0:54
 */
@RestController
@CrossOrigin
@RequestMapping("/eduoss/fileoss")
@Api(description = "文件管理")
public class FileController {

    @Resource
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    public ResponseResult uploadFile(@RequestParam(value = "file",required = false)MultipartFile file) {
        String url = fileService.uploadFileOss(file);
        return ResponseResult.ok().data("url",url);

    }

}
