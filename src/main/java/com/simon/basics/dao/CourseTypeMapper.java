package com.simon.basics.dao;

import com.simon.basics.model.CourseType;

public interface CourseTypeMapper {
    int deleteByPrimaryKey(Long typeId);

    int insert(CourseType record);

    int insertSelective(CourseType record);

    CourseType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(CourseType record);

    int updateByPrimaryKey(CourseType record);
}