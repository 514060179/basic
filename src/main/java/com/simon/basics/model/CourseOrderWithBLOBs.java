package com.simon.basics.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fengtianying
 * @date 2018/9/21 15:08
 */
public class CourseOrderWithBLOBs extends CourseOrder {

    @ApiModelProperty("是否选座:mull代表无权限")
    private Boolean chooseSeat;
    @ApiModelProperty("用户名字")
    private String name;
    @ApiModelProperty("学生家长名字")
    private String parentName;
    @ApiModelProperty("用户性别")
    private String sex;
    @ApiModelProperty("用户年龄")
    private Integer age;
    @ApiModelProperty("用户手机号码")
    private String phone;
    @ApiModelProperty(value = "课程名字")
    private String courseName;
    @ApiModelProperty(value = "课程价格")
    private BigDecimal courseCost;

    public Boolean getChooseSeat() {
        return chooseSeat;
    }

    public void setChooseSeat(Boolean chooseSeat) {
        this.chooseSeat = chooseSeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(BigDecimal courseCost) {
        this.courseCost = courseCost;
    }
}
