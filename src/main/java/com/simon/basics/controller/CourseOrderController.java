package com.simon.basics.controller;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author simon.feng
 * @Date Created in 20:45 2018/9/18/018
 * @Modificd
 */
@RestController
@RequestMapping("api/courseOrder")
@Api(tags = "courseOrder", description = "课程订单")
public class CourseOrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseOrderService courseOrderService;
    @Autowired
    private ClassCourseService classCourseService;

    @PostMapping("list")
    public ReturnParam list(CourseOrder courseOrder, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(courseOrderService.getListByPage(courseOrder,pageNum,pageSize));
    }

    @PostMapping("create")
    public ReturnParam create(@RequestParam Long courseId) {
        ClassCourse classCourse = classCourseService.findOne(courseId,null,null);
        if(Objects.isNull(classCourse)){
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        CourseOrder courseOrder = courseOrderService.findOneByCourseId(courseId);
        if (courseOrder!=null){
            logger.warn("重复下单：");
            return ReturnParam.repeatOrder();
        }
        return ReturnParam.success(courseOrderService.create(classCourse));
    }
}
