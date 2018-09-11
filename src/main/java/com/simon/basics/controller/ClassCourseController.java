package com.simon.basics.controller;

import com.simon.basics.model.vo.ReturnParam;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengtianying
 * @date 2018/9/10 16:51
 */
@RestController
@RequestMapping("/api/course")
@Api(tags = "class",description = "课程/班级：列表、详情、修改、新增")
public class ClassCourseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("list")
    public ReturnParam list(){
        return ReturnParam.success();
    }

    @PostMapping("detail")
    public ReturnParam detail(){
        return ReturnParam.success();
    }

    @PostMapping("add")
    public ReturnParam add(){
        return ReturnParam.success();
    }

    @PostMapping("update")
    public ReturnParam update(){
        return ReturnParam.success();
    }

}
