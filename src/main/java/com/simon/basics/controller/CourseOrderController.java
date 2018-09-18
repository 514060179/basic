package com.simon.basics.controller;

import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author simon.feng
 * @Date Created in 20:45 2018/9/18/018
 * @Modificd
 */
@RestController
@RequestMapping("api/courseOrder")
@Api(tags = "courseOrder",description = "课程订单")
public class CourseOrderController {

    @PostMapping("create")
    public ReturnParam create(@RequestParam Long courseId){
        new SnowflakeIdWorker().nextId();
        return null;
    }
}
