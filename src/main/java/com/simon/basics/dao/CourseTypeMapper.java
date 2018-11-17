package com.simon.basics.dao;

import com.simon.basics.model.CourseType;
import com.simon.basics.model.CourseTypeWithBLOBs;

import java.util.List;

public interface CourseTypeMapper {

    List<CourseType> findParentListByTypeSeries();

    List<CourseType> findChildListByTypeSeries(Long typeId);

    int insertCourseType(CourseType courseType);

    int updateCourseType(CourseType courseType);

    int deleteById(Long typeId);

    CourseType findById(Long typeId);
}