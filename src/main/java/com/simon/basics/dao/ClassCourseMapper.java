package com.simon.basics.dao;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;

public interface ClassCourseMapper {
    int deleteByPrimaryKey(Long courseId);

    int insert(ClassCourseWithBLOBs record);

    int insertSelective(ClassCourseWithBLOBs record);

    ClassCourseWithBLOBs selectByPrimaryKey(Long courseId);

    int updateByPrimaryKeySelective(ClassCourseWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ClassCourseWithBLOBs record);

    int updateByPrimaryKey(ClassCourse record);
}