package com.simon.basics.dao;

import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;
import com.simon.basics.model.CourseRosterAttendance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassCourseMapper {
    int deleteByPrimaryKey(Long courseId);

    int insert(ClassCourseWithBLOBs record);

    int insertSelective(ClassCourse classCourse);

    ClassCourseWithBLOBs selectByPrimaryKey(@Param("courseId") Long courseId, @Param("accountId") Long accountId);

    List<ClassCourseWithBLOBs> findListByCondition(ClassCourse classCourse);

    int updateByPrimaryKeyAndAccountIdSelective(ClassCourse record);

    int updateByPrimaryKeyWithBLOBs(ClassCourseWithBLOBs record);

    int updateByPrimaryKey(ClassCourse record);

    int updateStatus(@Param("courseId") Long courseId, @Param("deleted") Boolean deleted);

    List<CourseRosterAttendance> getAttendanceList(@Param("courseId") Long courseId, @Param("courseCurrent") int courseCurrent);
}