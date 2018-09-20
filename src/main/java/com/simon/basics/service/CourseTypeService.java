package com.simon.basics.service;

import com.simon.basics.model.CourseType;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:11
 */
public interface CourseTypeService {

    /**
     * 根据typeSeries 获取列表
     * @param typeSeries
     * @return
     */
    List<CourseType> findListByTypeSeries(Long typeSeries);
}