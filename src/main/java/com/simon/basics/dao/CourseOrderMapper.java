package com.simon.basics.dao;

import com.simon.basics.model.CourseOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(CourseOrder record);

    int insertSelective(CourseOrder record);

    CourseOrder selectByPrimaryKey(Long orderId);

    CourseOrder selectByOrderNo(String orderNo);

    List<CourseOrder> findListByCondition(CourseOrder record);

    int updateByPrimaryKeySelective(CourseOrder record);

    int updateByPrimaryKey(CourseOrder record);

}