package com.simon.basics.controller;

import com.simon.basics.model.*;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Future;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author fengtianying
 * @date 2018/9/10 16:51
 */
@RestController
@RequestMapping("/api/course")
@Api(tags = "class", description = "课程/班级：列表、详情、修改、新增")
public class ClassCourseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClassCourseService classCourseService;

    @PostMapping("list")
    public ReturnParam list(ClassCourse classCourse, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(classCourseService.findListByPage(classCourse, pageNum, pageSize));
    }

    @PostMapping("detail")
    public ReturnParam detail(@RequestParam Long courseId) {
        return ReturnParam.success(classCourseService.findOne(courseId, null));
    }

    @PostMapping("add")
    public ReturnParam add(ClassCourse classCourse, @RequestParam Long accountId, @RequestParam Integer courseTotal, @RequestParam Long seatId, @RequestParam Long typeId, @RequestParam Double courseCost, @RequestParam Date courseStartTime, @RequestParam @Future Date courseEndTime) {
        return ReturnParam.success(classCourseService.add(classCourse));
    }

    @PostMapping("update")
    public ReturnParam update(ClassCourse classCourse, @RequestParam Long courseId) {
        return ReturnParam.success(classCourseService.update(classCourse));
    }

    @GetMapping("courseStart")
    public ReturnParam courseStart(@RequestParam Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourse classCourse = classCourseService.findOne(courseId, user.getAccountId());

        if (Objects.isNull(classCourse)) {
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        //获取上节课
        RosterAttendance rosterAttendance = classCourseService.findRosterAttendance(courseId, classCourse.getCourseCurrent() - 1);
        if (rosterAttendance!=null&&Objects.isNull(rosterAttendance.getEndTime())) {
            logger.warn("上节课程未结束，结束上节课程才课开始上课" + courseId + classCourse.getCourseCurrent());
            return ReturnParam.lastCourseNoEnding();
        }
        if (EnumCode.CourseStatus.COURSE_END.getValue().equals(classCourse.getCourseStatus())
                || EnumCode.CourseStatus.COURSE_END.getValue().equals(classCourse.getCourseStatus())) {//课程结束或取消
            logger.warn("课程结束或取消，无法开始上课" + courseId);
            return ReturnParam.courseEnding();
        }
        classCourse = classCourseService.courseStart(classCourse);
        if (Objects.isNull(classCourse)) {
            return ReturnParam.systemError("业务处理失败!");
        }
        return ReturnParam.success(classCourse);
    }

    @PostMapping("courseAttendance")
    public ReturnParam courseAttendance(@RequestParam Long courseId, int courseCurrent) {
        return ReturnParam.success(classCourseService.getAttendanceList(courseId, courseCurrent));
    }

    @PostMapping("sign")
    public ReturnParam sign(@RequestParam Long courseId, int courseCurrent) {
        return ReturnParam.success(classCourseService.getAttendanceList(courseId, courseCurrent));
    }

    @PostMapping("additional")
    public ReturnParam additional(@RequestParam Long courseId, int courseCurrent) {
        return ReturnParam.success(classCourseService.getAttendanceList(courseId, courseCurrent));
    }

    @GetMapping("courseEnd")
    public ReturnParam courseEnd(@RequestParam Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourse classCourse = classCourseService.findOne(courseId, user.getAccountId());
        if (Objects.isNull(classCourse)) {
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        List<CourseRosterAttendance> courseRosterAttendanceList = classCourseService.getAttendanceList(courseId, classCourse.getCourseCurrent());
        int actualNumber = 0;//实到人数
        int mustNumber = courseRosterAttendanceList.size();//应到人数
        Long total = 0L;
        //查询名单
        for (CourseRosterAttendance courseRosterAttendance : courseRosterAttendanceList
                ) {
            if (courseRosterAttendance.getAttendance()) {
                actualNumber++;
            }
            if (user.getAccountId() == courseRosterAttendance.getAccountId()) { //老师签单单
                total = new Date().getTime() - courseRosterAttendance.getCreateTime().getTime();
            }
        }
        classCourseService.courseEnd(classCourse, user, actualNumber, mustNumber, total);
        return ReturnParam.success();
    }

}
