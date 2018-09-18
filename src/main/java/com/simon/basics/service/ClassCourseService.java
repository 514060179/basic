package com.simon.basics.service;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 14:21
 */
public interface ClassCourseService {

    /**
     * 详情
     * @param courseId
     * @return
     */
    ClassCourse findOne(Long courseId);

    /**
     * 列表
     * @param classCourse
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ClassCourseWithBLOBs> findListByPage(ClassCourse classCourse,int pageNum,int pageSize);

    /**
     * 更新
     * @param classCourse
     * @return
     */
    int update(ClassCourse classCourse);

    /**
     * 删除
     * @param courseId
     * @return
     */
    int delete(Long courseId);

    /**
     * 新增
     * @param classCourse
     * @return
     */
    int add(ClassCourse classCourse);
}
