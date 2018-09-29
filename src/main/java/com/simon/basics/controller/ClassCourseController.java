package com.simon.basics.controller;

import com.github.pagehelper.PageHelper;
import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.*;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.SeatLayoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    private JedisService jedisService;

    @Autowired
    private SeatLayoutService seatLayoutService;

    @PostMapping("list")
    @ApiOperation("课程列表")
    public ReturnParam list(ClassCourse classCourse, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(classCourseService.findListByPage(classCourse, pageNum, pageSize));
    }

    @PostMapping("detail")
    @ApiOperation("课程详情")
    public ReturnParam detail(@RequestParam Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long accountId = null;
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())) {
            return ReturnParam.success(classCourseService.findOne(courseId, accountId,null));//学生
        }else{
            return ReturnParam.success(classCourseService.findOne(courseId, null,null));//其他
        }
    }

    @PostMapping("add")
    @ApiOperation("课程新增")
    public ReturnParam add(ClassCourse classCourse, @RequestParam Long accountId, @RequestParam Integer courseTotal, @RequestParam Long seatId, @RequestParam Long typeId, @RequestParam Double courseCost, @RequestParam Date courseStartTime, @RequestParam @Future Date courseEndTime) {
        ClassCourse classCourseRespone = classCourseService.add(classCourse);
        if (classCourse!=null){
            //获取座位存放缓存中
            seatLayoutService.findAllList();
            if (!jedisService.mapExists("seatLayoutMap",seatId+"")){
                seatLayoutService.pullAllToRedis();
            }
            jedisService.put("course-seatLayout:"+classCourseRespone.getCourseId(),jedisService.mapGet("seatLayoutMap",seatId+""));
        }
        return ReturnParam.success(classCourseRespone);
    }

    @PostMapping("update")
    @ApiOperation("课程修改")
    public ReturnParam update(ClassCourse classCourse, @RequestParam Long courseId) {
        return ReturnParam.success(classCourseService.update(classCourse));
    }

    @GetMapping("courseStart")
    @ApiOperation("老师开始开始上课")
    public ReturnParam courseStart(@RequestParam Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourse classCourse = classCourseService.findOne(courseId, null,user.getAccountId());

        if (Objects.isNull(classCourse)) {
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        //获取上节课是否结束
        RosterAttendance rosterAttendance = classCourseService.findRosterAttendance(courseId, classCourse.getCourseCurrent());
        if (rosterAttendance != null && Objects.isNull(rosterAttendance.getEndTime())) {
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
    @ApiOperation("老师获取考勤名单")
    public ReturnParam courseAttendance(@RequestParam Long courseId, Integer courseCurrent) {
        if (courseCurrent==null){
            courseCurrent = classCourseService.findOne(courseId,null,null).getCourseCurrent();
        }
        return ReturnParam.success(classCourseService.getAttendanceList(courseId, courseCurrent));
    }

    @PostMapping("sign")
    @ApiOperation("学生签到")
    public ReturnParam sign(@RequestParam Long courseId) {
        //课程是否购买
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourseWithBLOBs classCourseWithBLOBs = (ClassCourseWithBLOBs)classCourseService.findOne(courseId,user.getAccountId(),null);
        if (Objects.isNull(classCourseWithBLOBs)||!classCourseWithBLOBs.getBought()){
            logger.warn("学生:{}未购买课程:{}",courseId,user.getAccountId());
            return ReturnParam.courseNotEnoughOrNotHad();
        }
        int courseCurrent = classCourseWithBLOBs.getCourseCurrent();
        //查找当前课程是否开始(老师是否签到)
        RosterAttendance rosterAttendance = classCourseService.findTeacherRosterAttendance(courseId, classCourseWithBLOBs.getCourseCurrent());
        if (Objects.isNull(rosterAttendance)) {
            logger.warn("课程courseId={},courseCurrent={}未开始", courseId, courseCurrent);
            return ReturnParam.courseNotBeginning();
        }
        classCourseService.sign(courseId, courseCurrent);
        return ReturnParam.success();
    }

    @PostMapping("additional")
    @ApiOperation("老师添加串课名单")
    public ReturnParam additional(@RequestParam Long courseId, @RequestParam Long accountId, @RequestParam int rosterSeatX, @RequestParam int rosterSeatY) {
        //查询是否有资格
        User user = (User) SecurityUtils.getSubject().getPrincipal();//查找当前课程是否开始(老师是否签到)

        ClassCourseWithBLOBs classCourse = (ClassCourseWithBLOBs)classCourseService.findOne(courseId, accountId,user.getAccountId());
        RosterAttendance rosterAttendance = classCourseService.findTeacherRosterAttendance(courseId, classCourse.getCourseCurrent());
        if (Objects.isNull(rosterAttendance)) {
            logger.warn("课程courseId={},courseCurrent={}未开始", courseId, classCourse.getCourseCurrent());
            return ReturnParam.courseNotBeginning();
        }
        if (classCourse.getBought()){
            //课程名单内学生
//            classCourseService.sign(courseId, classCourse.getCourseCurrent());
//            return ReturnParam.success(classCourseService.getAttendanceList(courseId, classCourse.getCourseCurrent()));
            logger.warn("课程名单内学生无法重复签到!");
            return ReturnParam.repeatResource("课程名单内学生无法重复签到!");
        }
        Long type = classCourse.getTypeId();
        CourseRoster courseRoster = classCourseService.findCourseRoster(accountId, type);
        if (!Objects.isNull(courseRoster)) {
            int rest = courseRoster.getRosterCourseCountRest();
            if (rest < 1) {
                return ReturnParam.courseNotEnoughOrNotHad();
            }
            classCourseService.additional(accountId, courseId, classCourse.getCourseCurrent(), courseRoster.getRosterId(),rosterSeatX,rosterSeatY);
            return ReturnParam.success(classCourseService.getAttendanceList(courseId, classCourse.getCourseCurrent()));
        } else {
            logger.warn("串课失败！没有购买该类型课程或课程剩余不足！");
            return ReturnParam.courseNotEnoughOrNotHad();
        }
    }

    @GetMapping("courseEnd")
    @ApiOperation("老师结束课程（下课）")
    public ReturnParam courseEnd(@RequestParam Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourse classCourse = classCourseService.findOne(courseId, null,user.getAccountId());
        if (Objects.isNull(classCourse)) {
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        //获取老师的签到情况
        RosterAttendance rosterAttendance = classCourseService.getTeacherAttendance(courseId, classCourse.getCourseCurrent());
        if (!Objects.isNull(rosterAttendance.getEndTime())){
            logger.warn("老师结束课程重复操作courseId={}",courseId);
            return ReturnParam.repeatResource("课程已经结束！");
        }
        List<CourseRosterAttendance> courseRosterAttendanceList = classCourseService.getAttendanceList(courseId, classCourse.getCourseCurrent());
        int actualNumber = 0;//实到人数
        int mustNumber = courseRosterAttendanceList.size();//应到人数
        Long total = new Date().getTime() - rosterAttendance.getCreateTime().getTime();
        //查询名单
        for (CourseRosterAttendance courseRosterAttendance : courseRosterAttendanceList
                ) {
            if (Objects.isNull(courseRosterAttendance.getAttendance())||courseRosterAttendance.getAttendance()) {
                actualNumber++;
            }
        }
        classCourseService.courseEnd(classCourse, user, actualNumber, mustNumber, total);
        return ReturnParam.success();
    }

}
