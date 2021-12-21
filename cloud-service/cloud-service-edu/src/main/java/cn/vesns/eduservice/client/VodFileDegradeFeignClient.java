package cn.vesns.eduservice.client;

import cn.vesns.utils.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: VoidFileDegradeFeignClient
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 18:11
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public ResponseResult deleteVideo(String videoId) {
        // TODO 后续优化：再次根据videoId删除
        return ResponseResult.error().message("删除失败");
    }

    @Override
    public ResponseResult delVideoList(List<String> videoIdList) {
        return ResponseResult.error().message("删除失败");
    }
}
