package com.simon.basics.service.impl;

import com.simon.basics.dao.CourseTypeMapper;
import com.simon.basics.model.CourseType;
import com.simon.basics.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:11
 */
@Service
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Override
    public List<CourseType> findListByTypeSeries(Long typeSeries) {
        List<CourseType> courseTypeList = null;
        if (0==typeSeries){
            courseTypeList = courseTypeMapper.findParentListByTypeSeries();
        }else{
            courseTypeList = courseTypeMapper.findChildListByTypeSeries(typeSeries);
        }
        return courseTypeList;
    }
}
