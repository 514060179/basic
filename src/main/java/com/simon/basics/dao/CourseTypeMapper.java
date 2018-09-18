package com.simon.basics.dao;

import com.simon.basics.model.CourseType;
import com.simon.basics.model.CourseTypeWithBLOBs;

import java.util.List;

public interface CourseTypeMapper {

    List<CourseType> findParentListByTypeSeries();

    List<CourseType> findChildListByTypeSeries(Long typeId);

}