package cn.vesns.eduservice.client;

import cn.vesns.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: VodClient
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 17:35
 */
@Component
@FeignClient(name = "cloud-service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {


    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/eduvod/vod/deleteVideo/{videoId}")
    public ResponseResult deleteVideo(@PathVariable(value = "videoId") String videoId) ;

    /**
     * 批量删除阿里云视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/eduvod/vod/delVideoList")
    public ResponseResult delVideoList(@RequestParam("videoIdList") List<String> videoIdList);

}
