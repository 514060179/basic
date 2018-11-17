package com.simon.basics.controller;

import com.simon.basics.model.CourseType;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.CourseTypeService;
import com.simon.basics.util.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:05
 */
@RestController
@RequestMapping("api/courseType")
@Api(tags = "courseType",description = "课程类型获取")
public class CourseTypeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseTypeService courseTypeService;
    @PostMapping("list")
    public ReturnParam<List<CourseType>> list(@RequestParam @ApiParam("类型系列0代表所有系列其他（类型id）代表该系列下的所有类别") Long typeSeries){
        return ReturnParam.success(courseTypeService.findListByTypeSeries(typeSeries));
    }

    @PostMapping("add")
    @ApiOperation("新增课程类型")
    public ReturnParam<CourseType> add(@RequestBody CourseType courseType){
        Long typeSeries = courseType.getTypeSeries();
        if (typeSeries!=0L){
            //查询课程类型是否存在父级id
            CourseType findCourse = courseTypeService.findOne(typeSeries);
            if (findCourse==null){
                return ReturnParam.noHandlerFound("未找到资源父级资源:type="+typeSeries);
            }
        }
        return ReturnParam.success(courseTypeService.add(courseType));
    }

    @PostMapping("update")
    @ApiOperation("修改课程类型")
    public ReturnParam update(@RequestBody CourseType courseType){
        Long typeSeries = courseType.getTypeSeries();
        if (typeSeries!=0L){
            //查询课程类型是否存在父级id
            CourseType findCourse = courseTypeService.findOne(typeSeries);
            if (findCourse==null){
                return ReturnParam.noHandlerFound("未找到资源父级资源id:"+typeSeries);
            }
        }
        if (courseTypeService.update(courseType)>0){
            return ReturnParam.success();
        }else{
            logger.warn("未找到资源:{}", JSONUtil.objectToJson(courseType));
            return ReturnParam.noHandlerFound("未找到资源:"+ JSONUtil.objectToJson(courseType));
        }
    }

    @GetMapping("delete")
    @ApiOperation("修改课程类型")
    public ReturnParam<CourseType> delete(@RequestParam Long typeId){
        if (courseTypeService.delete(typeId)>0){
            return ReturnParam.success();
        }else{
            logger.warn("未找到资源:{}", typeId);
            return ReturnParam.noHandlerFound("未找到资源:"+ typeId);
        }
    }
}
