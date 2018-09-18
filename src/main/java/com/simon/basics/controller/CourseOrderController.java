package com.simon.basics.controller;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CourseOrderService courseOrderService;
    @Autowired
    private ClassCourseService classCourseService;
    @PostMapping("create")
    public ReturnParam create(@RequestParam Long courseId){
        ClassCourse classCourse = classCourseService.findOne(courseId);
        return ReturnParam.success(courseOrderService.create(classCourse));
    }
}
