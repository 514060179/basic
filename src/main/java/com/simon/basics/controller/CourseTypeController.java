package com.simon.basics.controller;

import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.CourseTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengtianying
 * @date 2018/9/18 17:05
 */
@RestController
@RequestMapping("api/courseType")
@Api(tags = "courseType",description = "课程类型获取")
public class CourseTypeController {

    @Autowired
    private CourseTypeService courseTypeService;
    @PostMapping("list")
    public ReturnParam list(@RequestParam @ApiParam("类型系列0代表所有系列其他（类型id）代表该系列下的所有类别") Long typeSeries){
        return ReturnParam.success(courseTypeService.findListByTypeSeries(typeSeries));
    }
}
