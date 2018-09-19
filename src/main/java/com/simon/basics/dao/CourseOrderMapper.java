package com.simon.basics.dao;

import com.simon.basics.model.CourseOrder;

import java.util.List;

public interface CourseOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(CourseOrder record);

    int insertSelective(CourseOrder record);

    CourseOrder selectByPrimaryKey(Long orderId);

    List<CourseOrder> findListByCondition(CourseOrder record);

    int updateByPrimaryKeySelective(CourseOrder record);

    int updateByPrimaryKey(CourseOrder record);

}