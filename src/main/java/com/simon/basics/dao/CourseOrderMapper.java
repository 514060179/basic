package com.simon.basics.dao;

import com.simon.basics.model.CourseOrder;

public interface CourseOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(CourseOrder record);

    int insertSelective(CourseOrder record);

    CourseOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(CourseOrder record);

    int updateByPrimaryKey(CourseOrder record);
}