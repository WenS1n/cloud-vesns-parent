package cn.vesns.vodservice.controller;

import cn.vesns.baseservice.handler.VesnsException;
import cn.vesns.utils.ResponseResult;
import cn.vesns.vodservice.config.AliKeyConfig;
import cn.vesns.vodservice.util.AliyunVodSDKUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: VodController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 11:04
 */
@CrossOrigin
@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/vod")
public class VodController {

    @Resource
    private AliKeyConfig aliKeyConfig;



    @ApiOperation("视频上传")
    @PostMapping("/uploadVideo")
    public ResponseResult uploadVideo(@RequestParam(value = "file",required = false)MultipartFile file) {
        try {

            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(
                    aliKeyConfig.getAccessKey(),aliKeyConfig.getAccessKeySecret(),title,originalFilename,inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(uploadStreamRequest);
            String videoId = response.getVideoId();
            return ResponseResult.ok().data("videoId",videoId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new VesnsException(20001,"文件上传失败");
        }
    }

    @ApiOperation("删除上传")
    @DeleteMapping("/deleteVideo/{videoId}")
    public ResponseResult deleteVideo(@PathVariable(value = "videoId") String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(aliKeyConfig.getAccessKey(),aliKeyConfig.getAccessKeySecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return ResponseResult.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new VesnsException(20001,"删除视频失败");
        }
    }


    @ApiOperation(value = "批量删除视频")
    @DeleteMapping("delVideoList")
    public ResponseResult delVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        try {
            //*初始化客户端对象
            DefaultAcsClient client = AliyunVodSDKUtil.initVodClient(aliKeyConfig.getAccessKey(),
                    aliKeyConfig.getAccessKeySecret());
            //*创建请求对象（不同操作，类不同）
            DeleteVideoRequest request = new DeleteVideoRequest();

            //*向请求中设置参数
            //支持传入多个视频ID，多个用逗号分隔
            //11,12,13
            String videoIds = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);
            //*调用客户端对象方法发送请求，拿到响应
            client.getAcsResponse(request);
            return ResponseResult.ok();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new VesnsException(20001,"批量删除视频失败");
        }

    }
}
