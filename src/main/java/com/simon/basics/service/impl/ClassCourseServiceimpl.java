package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.componet.exception.SqlWriteException;
import com.simon.basics.componet.exception.SqlWritePrerequisiteException;
import com.simon.basics.dao.ClassCourseMapper;
import com.simon.basics.dao.CourseRosterMapper;
import com.simon.basics.dao.RosterAttendanceMapper;
import com.simon.basics.dao.RosterIncomeMapper;
import com.simon.basics.model.*;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.util.JSONUtil;
import com.simon.basics.util.SmsUtil;
import com.simon.basics.util.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private CourseRosterMapper courseRosterMapper;
    @Autowired
    private RosterAttendanceMapper rosterAttendanceMapper;
    @Autowired
    private RosterIncomeMapper rosterIncomeMapper;

    @Override
    public ClassCourse findOne(Long courseId, Long studentId, Long accountId) {
        return classCourseMapper.selectByPrimaryKey(courseId,studentId, accountId);
    }

    @Override
    public PageInfo<ClassCourseWithBLOBs> findListByPage(ClassCourse classCourse, Boolean bought,Long studentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long teacherId = null;
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())) {//学生
            classCourse.setAccountId(user.getAccountId());
        }else if (EnumCode.UserType.TYPE_TEACHER.getValue().equals(user.getType())){//老师获取自己课程
            teacherId = user.getAccountId();
        }else{
            classCourse.setAccountId(studentId);
        }
        return new PageInfo<ClassCourseWithBLOBs>(classCourseMapper.findListByCondition(classCourse,teacherId,bought));
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
    public ClassCourse add(ClassCourse classCourse) {
        classCourse.setCourseId(new SnowflakeIdWorker().nextId());
        classCourse.setCourseStatus(EnumCode.CourseStatus.COURSE_INIT.getValue());
        if(classCourseMapper.insertSelective(classCourse)>0){
            return classCourse;
        }
        return null;
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
        update.setClassStatus(EnumCode.ClassStatus.CLASS_BEGINS.getValue());
        int i = classCourseMapper.updateByPrimaryKeyAndAccountIdSelective(update);
        if (i > 0) {
            //新增签到
            RosterAttendance rosterAttendanceInsert = new RosterAttendance();
            rosterAttendanceInsert.setAccountId(user.getAccountId());
            rosterAttendanceInsert.setAttendSectionNum(classCourse.getCourseCurrent() + 1);
            rosterAttendanceInsert.setAttendName(classCourse.getCourseName()+"【第+" +(classCourse.getCourseCurrent()+1)+"节课签到】");
            rosterAttendanceInsert.setCourseId(classCourse.getCourseId());
            rosterAttendanceInsert.setAttendType(EnumCode.AttendType.ATTEND_TYPE_TEACHER.getValue());
            rosterAttendanceMapper.insertSelective(rosterAttendanceInsert);
            classCourse.setCourseCurrent(classCourse.getCourseTotal() + 1);
            return classCourse;
        }
        return null;
    }

    @Override
    public List<CourseRosterAttendance> getAttendanceList(Long courseId, int courseCurrent) {
        List<CourseRosterAttendance> resultList = new ArrayList<CourseRosterAttendance>();
        //获取串课名单
        List<CourseRosterAttendance> courseAdditionalRosterAttendanceList = classCourseMapper.getAdditionalAttendanceList(courseId, courseCurrent);
        resultList.addAll(courseAdditionalRosterAttendanceList);
        //出勤名单
        List<CourseRosterAttendance> courseRosterAttendanceList = classCourseMapper.getAttendanceList(courseId, courseCurrent);
        resultList.addAll(courseRosterAttendanceList);
        return resultList;
    }

    @Override
    public RosterAttendance getTeacherAttendance(Long courseId, int courseCurrent) {
        return rosterAttendanceMapper.selectByCourseAndNum(courseId,courseCurrent);
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
        BigDecimal averageCourse = classCourse.getCourseCost().divide(new BigDecimal(classCourse.getCourseTotal()), 2, BigDecimal.ROUND_CEILING);
        rosterIncomeInsert.setAverageCourse(averageCourse);//每节收费
        rosterIncomeInsert.setPercentage(classCourse.getPercentage());//提成百分比
        String teacherChargeType = user.getTeacherChargeType();
        rosterIncomeInsert.setIncomeType(teacherChargeType);//收费类型
        if (EnumCode.TeacherChargeType.CHARGE_TYPE_TIME.getValue().equals(teacherChargeType)) {//按时
            BigDecimal averageHour = classCourse.getAverageHour();
            int unit = new BigDecimal(costTime).divide(averageHour.multiply(new BigDecimal(1000 * 60 * 60)),2, BigDecimal.ROUND_HALF_UP).intValue();//花费总单位时间
            rosterIncomeInsert.setAverageHour(averageHour);//平均X小时起收费。收费单位
            BigDecimal averageHourCost = classCourse.getAverageHourCost().multiply(new BigDecimal(unit)).setScale(2, BigDecimal.ROUND_HALF_UP);//基本收入
            if (actualNumber > classCourse.getExceedNum()) {//提成
                averageHourCost = averageHourCost.add(classCourse.getExtraCharge());
            }
            rosterIncomeInsert.setAverageHourCost(classCourse.getAverageHourCost());//每个收费单位收取费用
            rosterIncomeInsert.setExceedNum(classCourse.getExceedNum());//超过人数提成
            rosterIncomeInsert.setIncomeAmount(averageHourCost);//收入金额
        } else if (EnumCode.TeacherChargeType.CHARGE_TYPE_PERCENTAGE.getValue().equals(teacherChargeType)) {//按提成
            rosterIncomeInsert.setIncomeAmount(classCourse.getPercentage().multiply(averageCourse).setScale(2, BigDecimal.ROUND_HALF_UP));//收入金额
        }
        //更新
        int i = rosterAttendanceMapper.updateByCourseAndNum(rosterAttendanceUpdate);
        ClassCourse update = new ClassCourse();
        update.setCourseId(classCourse.getCourseId());
        update.setClassStatus(EnumCode.ClassStatus.CLASS_OVER.getValue());
        classCourseMapper.updateByPrimaryKeyAndAccountIdSelective(update);
        if (i > 0) {
            int j = rosterIncomeMapper.insertSelective(rosterIncomeInsert);
            if (j < 1) {
                throw new SqlWritePrerequisiteException("插入教师收入表条数未:" + i + JSONUtil.objectToJson(rosterIncomeInsert));
            }
        } else {
            throw new SqlWritePrerequisiteException("更新课程出勤表条数未:" + i + JSONUtil.objectToJson(rosterAttendanceUpdate));
        }
        //插入
    }

    @Override
    public void courseCancel(Long courseId) {
        ClassCourse classCourse = new ClassCourse();
        classCourse.setCourseId(courseId);
        classCourse.setCourseStatus(EnumCode.CourseStatus.COURSE_END.getValue());
        if (classCourseMapper.updateByPrimaryKeyAndAccountIdSelective(classCourse)<=0){
            throw new RuntimeException("结束课程失败!");
        };
    }

    @Transactional
    @Override
    public void sign(Long courseId, int courseCurrent) {
        //签到
        //减少学生课堂数
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        RosterAttendance rosterAttendanceInsert = new RosterAttendance();
        rosterAttendanceInsert.setCourseId(courseId);
        rosterAttendanceInsert.setAttendSectionNum(courseCurrent);
        rosterAttendanceInsert.setAttendName("【第+" +courseCurrent+"节课签到】");
        rosterAttendanceInsert.setAccountId(user.getAccountId());
        rosterAttendanceInsert.setAttendType(EnumCode.AttendType.ATTEND_TYPE_STUDENT.getValue());
        rosterAttendanceMapper.insertSelective(rosterAttendanceInsert);

        CourseRoster courseRosterUpdate = new CourseRoster();
        courseRosterUpdate.setCourseId(courseId);
        courseRosterUpdate.setRosterCourseCountRest(1);
        courseRosterUpdate.setAccountId(user.getAccountId());
        int i = courseRosterMapper.updateByAccountAndCourseSelective(courseRosterUpdate);
        if (i <= 0) {
            throw new SqlWritePrerequisiteException("update fail：" + JSONUtil.objectToJson(courseRosterUpdate) + "update success:" + i);
        }
    }

    @Override
    public CourseRoster findCourseRoster(Long accountId, Long type) {
        return courseRosterMapper.selectByCourseType(accountId, type);
    }

    @Override
    public RosterAttendance findTeacherRosterAttendance(Long courseId, int courseCurrent) {
        return rosterAttendanceMapper.findTeacherRosterAttendance(courseId,courseCurrent);
    }

    @Transactional
    @Override
    public void additional(Long accountId, Long courseId, int courseCurrent, Long rosterId, int rosterSeatX, int rosterSeatY) {
        //1签到记录
        RosterAttendance rosterAttendanceInsert = new RosterAttendance();
        rosterAttendanceInsert.setCourseId(courseId);
        rosterAttendanceInsert.setAccountId(accountId);
        rosterAttendanceInsert.setAttendType(EnumCode.AttendType.ATTEND_TYPE_ADDITIONAL.getValue());
        rosterAttendanceInsert.setAttendSectionNum(courseCurrent);
        rosterAttendanceInsert.setAttendName("【第+" +courseCurrent+"节课签到】");
        rosterAttendanceInsert.setAdditionalSeatX(rosterSeatX);
        rosterAttendanceInsert.setAdditionalSeatY(rosterSeatY);
        rosterAttendanceMapper.insertSelective(rosterAttendanceInsert);
        //2学生课程课时减1
        CourseRoster courseRosterUpdate = new CourseRoster();
        courseRosterUpdate.setRosterId(rosterId);
        courseRosterUpdate.setRosterCourseCountRest(1);
        int i = courseRosterMapper.updateByPrimaryKeySelective(courseRosterUpdate);
        if (i < 1) {
            throw new SqlWritePrerequisiteException("update fail：" + JSONUtil.objectToJson(courseRosterUpdate) + "update success:" + i);
        }
    }

    @Override
    public void endCourseSendMsg(List<CourseRosterAttendance> courseRosterAttendanceList) {
        courseRosterAttendanceList.forEach(item->{
            String parentName = item.getParentName();
            String name = item.getName();
            String phone = item.getPhone();
            String msg = parentName+"您的孩子"+name+"已经上完课";
            new Thread(()->
                SmsUtil.sendSMS(phone,msg)
            ).start();
        });
    }
}
