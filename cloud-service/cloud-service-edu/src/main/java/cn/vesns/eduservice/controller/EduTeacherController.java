package cn.vesns.eduservice.controller;


import cn.vesns.eduservice.entity.EduTeacher;
import cn.vesns.eduservice.entity.vo.TeacherVO;
import cn.vesns.eduservice.service.EduTeacherService;
import cn.vesns.utils.ResponseResult;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author vesns
 * @since 2021-12-14
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduteacher")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/queryAll")
    public ResponseResult getAllEduTeacherList() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return ResponseResult.ok().data("list", list);
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("/deleteTeacher/{id}")
    public ResponseResult deleteTeacherList(@PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        System.out.println("===============>" + b);
        if (b) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.error();
        }
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/queryPageTeacher/{current}/{limit}")
    public ResponseResult queryPageTeacherList(@PathVariable Long current,
                                               @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, null);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
//        Map<String,Object> map = new HashMap<>();
//        map.put("list",records);
//        map.put("total",total);
//        return ResponseResult.ok().data(map);
        return ResponseResult.ok().data("list", records).data("total", total);
    }


    @ApiOperation(value = "条件分页查询讲师")
    @PostMapping("/queryPageTeacher/{current}/{limit}")
    public ResponseResult queryPageTeacherVo(@PathVariable Long current,
                                             @PathVariable Long limit, @RequestBody TeacherVO teacherVO) {
        String name = teacherVO.getName();
        Integer level = teacherVO.getLevel();
        String begin = teacherVO.getBegin();
        String end = teacherVO.getEnd();
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(name) || !StringUtils.isEmpty(level) || !StringUtils.isEmpty(begin)|| !StringUtils.isEmpty(end) ) {
            lambdaQueryWrapper.like(EduTeacher::getName, name)
                    .or().eq(EduTeacher::getLevel, level)
                    .or().ge(EduTeacher::getGmtCreate, begin)
                    .or().le(EduTeacher::getGmtCreate, end);
        }
//        if (!StringUtils.isEmpty(level)) {
//            lambdaQueryWrapper.eq(EduTeacher::getLevel, level);
//        }
//        if (!StringUtils.isEmpty(begin)) {
//            lambdaQueryWrapper.ge(EduTeacher::getGmtCreate, begin);
//        }
//        if (!StringUtils.isEmpty(end)) {
//            lambdaQueryWrapper.le(EduTeacher::getGmtCreate, end);
//        }
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, lambdaQueryWrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return ResponseResult.ok().data("list", records).data("total", total);
    }

}

