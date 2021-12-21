package cn.vesns.cmsservice.api;

import cn.vesns.cmsservice.entity.CrmBanner;
import cn.vesns.cmsservice.service.CrmBannerService;
import cn.vesns.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: vesns vip865047755@126.com
 * @Title: CrmBannerApiController
 * @ProjectName: cloud-vesns-parent
 * @Description:
 * @date: 2021-12-20 20:03
 */
@Api(description = "前端banner展示")
@RequestMapping("/cmsservice/banner")
@CrossOrigin
@RestController
public class CrmBannerApiController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation(value = "查询所有信息")
    @GetMapping("/getAllBanner")
    public ResponseResult getAllBanner() {
        List<CrmBanner> list = bannerService.getAllBanner();
        return ResponseResult.ok().data("bannerList",list);

    }

}
