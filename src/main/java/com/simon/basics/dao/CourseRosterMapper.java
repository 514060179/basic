package com.simon.basics.dao;

import com.simon.basics.model.CourseRoster;

public interface CourseRosterMapper {
    int deleteByPrimaryKey(Long rosterId);

    int insert(CourseRoster record);

    int insertSelective(CourseRoster record);

    CourseRoster selectByPrimaryKey(Long rosterId);

    int updateByPrimaryKeySelective(CourseRoster record);

    int updateByPrimaryKey(CourseRoster record);
}