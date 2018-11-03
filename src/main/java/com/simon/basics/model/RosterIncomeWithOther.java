package com.simon.basics.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author fengtianying
 * @date 2018/11/3 11:10
 */
public class RosterIncomeWithOther extends RosterIncome{

    @ApiModelProperty(value = "课程名字")
    private String courseName;

    @ApiModelProperty(value = "筛选条件:日期.格式:2018/10")
    private String[] date;

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
