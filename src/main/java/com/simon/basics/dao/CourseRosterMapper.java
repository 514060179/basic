package com.simon.basics.dao;

import com.simon.basics.model.CourseRoster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseRosterMapper {
    int deleteByPrimaryKey(Long rosterId);

    int insert(CourseRoster record);

    int insertSelective(CourseRoster record);

    CourseRoster selectByPrimaryKey(Long rosterId);

    List<CourseRoster> findOneByConditon(CourseRoster courseRoster);

    CourseRoster selectByCourseType(@Param("accountId") Long accountId, @Param("typeId") Long typeId);

    int updateByPrimaryKeySelective(CourseRoster record);

    int updateByAccountAndCourseSelective(CourseRoster record);

    int updateByPrimaryKey(CourseRoster record);

    CourseRoster findTeacherCourseRoster(Long courseId, int courseCurrent);
}