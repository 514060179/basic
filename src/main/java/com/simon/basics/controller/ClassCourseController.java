package com.simon.basics.controller;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Future;
import java.util.Date;


/**
 * @author fengtianying
 * @date 2018/9/10 16:51
 */
@RestController
@RequestMapping("/api/course")
@Api(tags = "class",description = "课程/班级：列表、详情、修改、新增")
public class ClassCourseController {

    @Autowired
    private ClassCourseService classCourseService;

    @PostMapping("list")
    public ReturnParam list(ClassCourse classCourse, @RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize){
        return ReturnParam.success(classCourseService.findListByPage(classCourse,pageNum,pageSize));
    }

    @PostMapping("detail")
    public ReturnParam detail(@RequestParam Long courseId ){
        return ReturnParam.success(classCourseService.findOne(courseId));
    }

    @PostMapping("add")
    public ReturnParam add(ClassCourse classCourse, @RequestParam Long accountId,@RequestParam Long seatId, @RequestParam Long typeId, @RequestParam Double courseCost, @RequestParam  Date courseStartTime, @RequestParam @Future Date courseEndTime){
        return ReturnParam.success(classCourseService.add(classCourse));
    }

    @PostMapping("update")
    public ReturnParam update(ClassCourse classCourse,@RequestParam Long courseId){
        return ReturnParam.success(classCourseService.update(classCourse));
    }

}
