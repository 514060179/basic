package com.simon.basics.model;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:23
 */
public class CourseTypeWithBLOBs extends CourseType {

    private List<CourseType> courseTypeList;

    public List<CourseType> getCourseTypeList() {
        return courseTypeList;
    }

    public void setCourseTypeList(List<CourseType> courseTypeList) {
        this.courseTypeList = courseTypeList;
    }
}
