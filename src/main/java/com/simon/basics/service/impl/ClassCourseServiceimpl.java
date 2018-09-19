package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.simon.basics.dao.ClassCourseMapper;
import com.simon.basics.dao.RosterAttendanceMapper;
import com.simon.basics.dao.RosterIncomeMapper;
import com.simon.basics.model.*;
import com.simon.basics.service.ClassCourseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 14:29
 */
@Service
public class ClassCourseServiceimpl implements ClassCourseService {

    @Autowired
    private ClassCourseMapper classCourseMapper;
    @Autowired
    private RosterAttendanceMapper rosterAttendanceMapper;
    @Autowired
    private RosterIncomeMapper rosterIncomeMapper;

    @Override
    public ClassCourse findOne(Long courseId, Long accountId) {
        return classCourseMapper.selectByPrimaryKey(courseId, accountId);
    }

    @Override
    public List<ClassCourseWithBLOBs> findListByPage(ClassCourse classCourse, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return classCourseMapper.findListByCondition(classCourse);
    }

    @Override
    public int update(ClassCourse classCourse) {
        return classCourseMapper.updateByPrimaryKeyAndAccountIdSelective(classCourse);
    }

    @Override
    public int delete(Long courseId) {
        return classCourseMapper.updateStatus(courseId, true);
    }

    @Override
    public int add(ClassCourse classCourse) {
        return classCourseMapper.insertSelective(classCourse);
    }

    @Transactional
    @Override
    public ClassCourse courseStart(ClassCourse classCourse) {
        //获取课程
        //1.更新课程
        //2.新增教师签到名单
        int current = classCourse.getCourseCurrent();
        ClassCourse update = new ClassCourse();
        update.setCourseId(classCourse.getCourseId());
        if (current == 0) {//第一次开课
            classCourse.setCourseStatus(EnumCode.CourseStatus.COURSE_IN.getValue());
            update.setCourseStatus(EnumCode.CourseStatus.COURSE_IN.getValue());
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        update.setCourseCurrent(1);
        update.setAccountId(user.getAccountId());
        int i = classCourseMapper.updateByPrimaryKeyAndAccountIdSelective(update);
        if (i > 0) {
            //新增签到
            RosterAttendance rosterAttendanceUpdate = new RosterAttendance();
            rosterAttendanceUpdate.setAccountId(user.getAccountId());
            rosterAttendanceUpdate.setAttendSectionNum(classCourse.getCourseTotal() + 1);
            rosterAttendanceUpdate.setCourseId(classCourse.getCourseId());
            rosterAttendanceUpdate.setAttendanceType("");
            rosterAttendanceMapper.insertSelective(rosterAttendanceUpdate);
            classCourse.setCourseCurrent(classCourse.getCourseTotal() + 1);
            return classCourse;
        }
        return null;
    }

    @Override
    public List<CourseRosterAttendance> getAttendanceList(Long courseId, int courseCurrent) {
        return classCourseMapper.getAttendanceList(courseId, courseCurrent);
    }

    @Override
    public RosterAttendance findRosterAttendance(Long courseId, int courseCurrent) {
        return rosterAttendanceMapper.selectByCourseAndNum(courseId, courseCurrent);
    }

    @Transactional
    @Override
    public void courseEnd(ClassCourse classCourse, User user, int actualNumber, int mustNumber, Long costTime) {
//        //1.更新学生课时 放到学生签到处
//        CourseRoster courseRosterUpdate = new CourseRoster();
//        courseRosterUpdate.setCourseId(classCourse.getCourseId());
//        courseRosterUpdate.setRosterCourseCountRest(1);
        //1更新考勤
        RosterAttendance rosterAttendanceUpdate = new RosterAttendance();
        rosterAttendanceUpdate.setCourseId(classCourse.getCourseId());
        rosterAttendanceUpdate.setAttendSectionNum(classCourse.getCourseCurrent());
        rosterAttendanceUpdate.setEndTime(new Date());
        //2.老师收入插入记录
        RosterIncome rosterIncomeInsert = new RosterIncome();
        rosterIncomeInsert.setAccountId(classCourse.getAccountId());
        rosterIncomeInsert.setActualNumber(actualNumber);//实到人数
        rosterIncomeInsert.setMustNumber(mustNumber);//应到人数
        rosterIncomeInsert.setCourseId(classCourse.getCourseId());
        rosterIncomeInsert.setIncomeSectionNum(classCourse.getCourseCurrent());//章节数
        BigDecimal averageCourse = classCourse.getCourseCost().divide(new BigDecimal(classCourse.getCourseTotal()),2, BigDecimal.ROUND_CEILING);
        rosterIncomeInsert.setAverageCourse(averageCourse);//每节收费
        rosterIncomeInsert.setPercentage(user.getPercentage());//提成百分比
        String teacherChargeType = user.getTeacherChargeType();
        rosterIncomeInsert.setIncomeType(teacherChargeType);//收费类型
        if (EnumCode.TeacherChargeType.CHARGE_TYPE_TIME.getValue().equals(teacherChargeType)) {//按时
            BigDecimal averageHour = user.getAverageHour();
            int unit = new BigDecimal(costTime).divide(averageHour.multiply(new BigDecimal(1000*60*60))).intValue();//花费总单位时间
            rosterIncomeInsert.setAverageHour(averageHour);//平均X小时起收费。收费单位
            rosterIncomeInsert.setAverageHourCost(user.getAverageHourCost().multiply(new BigDecimal(unit)).setScale(2, BigDecimal.ROUND_HALF_UP));//每个收费单位收取费用
            rosterIncomeInsert.setExceedNum(user.getExceedNum());//超过人数提成
            rosterIncomeInsert.setIncomeAmount(new BigDecimal(1000));//收入金额
        } else if (EnumCode.TeacherChargeType.CHARGE_TYPE_PERCENTAGE.getValue().equals(teacherChargeType)) {//按提成
            rosterIncomeInsert.setIncomeAmount(user.getPercentage().multiply(averageCourse).setScale(2, BigDecimal.ROUND_HALF_UP));//收入金额
        }
        //更新
        rosterAttendanceMapper.updateByCourseAndNum(rosterAttendanceUpdate);
        //插入
        rosterIncomeMapper.updateByPrimaryKeySelective(rosterIncomeInsert);
    }
}
