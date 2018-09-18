package com.simon.basics.dao;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassCourseMapper {
    int deleteByPrimaryKey(Long courseId);

    int insert(ClassCourseWithBLOBs record);

    int insertSelective(ClassCourse classCourse);

    ClassCourseWithBLOBs selectByPrimaryKey(Long courseId);

    List<ClassCourseWithBLOBs> findListByCondition(ClassCourse classCourse);

    int updateByPrimaryKeySelective(ClassCourse record);

    int updateByPrimaryKeyWithBLOBs(ClassCourseWithBLOBs record);

    int updateByPrimaryKey(ClassCourse record);

    int updateStatus(@Param("courseId") Long courseId, @Param("deleted")Boolean deleted);
}