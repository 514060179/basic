package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.*;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 14:21
 */
public interface ClassCourseService {

    /**
     * 详情
     *
     * @param courseId
     * @return
     */
    ClassCourse findOne(Long courseId, Long studentId, Long accountId);

    /**
     * 列表
     *
     * @param classCourse
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ClassCourseWithBLOBs> findListByPage(ClassCourse classCourse, Boolean bought, int pageNum, int pageSize);

    /**
     * 更新
     *
     * @param classCourse
     * @return
     */
    int update(ClassCourse classCourse);

    /**
     * 删除
     *
     * @param courseId
     * @return
     */
    int delete(Long courseId);

    /**
     * 新增
     *
     * @param classCourse
     * @return
     */
    ClassCourse add(ClassCourse classCourse);

    /**
     * 开始上课
     *
     * @param classCourse
     * @return
     */
    ClassCourse courseStart(ClassCourse classCourse);


    /**
     * 获取考勤名单
     *
     * @param courseId
     * @param courseCurrent
     * @return
     */
    List<CourseRosterAttendance> getAttendanceList(Long courseId, int courseCurrent);

    /**
     *  获取老师的出勤情况
     * @param courseId
     * @param courseCurrent
     * @return
     */
    RosterAttendance getTeacherAttendance(Long courseId, int courseCurrent);
    /**
     * 获取X课时出席名单
     *
     * @param courseId
     * @param courseCurrent
     * @return
     */
    RosterAttendance findRosterAttendance(Long courseId, int courseCurrent);

    /**
     * 结束课程(下课)
     *
     * @param classCourse
     */
    void courseEnd(ClassCourse classCourse, User user, int actualNumber, int mustNumber, Long costTime);
    /**
     * 结束课程(取消)
     *
     * @param courseId
     */
    void courseCancel(Long courseId);

    /**
     * 学生签到
     *
     * @param courseId
     * @param courseCurrent
     */
    void sign(Long courseId, int courseCurrent);

    /**
     * 获取用户课程名单
     *
     * @param accountId
     * @param type
     * @return
     */
    CourseRoster findCourseRoster(Long accountId, Long type);

    /**
     * 获取老师签到名单
     *
     * @param courseId
     * @param courseCurrent
     * @return
     */
    RosterAttendance findTeacherRosterAttendance(Long courseId, int courseCurrent);

    /**
     * 串课
     *
     * @param accountId
     * @param courseId
     * @param rosterId
     */
    void additional(Long accountId, Long courseId, int courseCurrent, Long rosterId, int rosterSeatX, int rosterSeatY);

    /**
     * 发送短信
     * @param courseRosterAttendanceList
     */
    void endCourseSendMsg(List<CourseRosterAttendance> courseRosterAttendanceList);
}
