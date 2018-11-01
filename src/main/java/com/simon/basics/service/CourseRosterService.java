package com.simon.basics.service;

import com.simon.basics.model.CourseRoster;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/10/23 9:46
 */
public interface CourseRosterService {

    /**
     * 根据课程id获取名单列表
     * @param courseId
     * @return
     */
    List<CourseRoster> findListByCourseId(Long courseId);

    /**
     * 更新课程名单
     * @param courseRosterList
     * @return
     */
    int updateCourseRosterByList(List<CourseRoster> courseRosterList);

    /**
     * 删除课程名单
     * @param courseId
     * @param accountId
     * @return
     */
    int delByCourseIdAndAccountId(Long courseId,Long accountId);

    /**
     * 获取课程名单
     * @param courseId
     * @param accountId
     * @return
     */
    CourseRoster findByCourseIdAndAccountId(Long courseId,Long accountId);

}
