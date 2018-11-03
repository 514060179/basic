package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author fengtianying
 * @date 2018/9/19 11:26
 */
public class CourseRosterAttendance {

    @ApiModelProperty(value = "课程id")
    private Long courseId;
    @ApiModelProperty(value = "账户id")
    private Long accountId;
    @ApiModelProperty("用户名字")
    private String name;
    @ApiModelProperty("出勤类型")
    private String attendType;
    @ApiModelProperty("学生家长名字")
    private String parentName;
    @ApiModelProperty("用户性别")
    private String sex;
    @ApiModelProperty("用户年龄")
    private Integer age;
    @ApiModelProperty("用户手机号码")
    private String phone;
    @ApiModelProperty("用户座位x坐标")
    private Integer rosterSeatX;
    @ApiModelProperty("用户座位x坐标")
    private Integer rosterSeatY;
    @ApiModelProperty("签到时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("是否已经出勤")
    private Boolean isAttendance;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendType() {
        return attendType;
    }

    public void setAttendType(String attendType) {
        this.attendType = attendType;
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

    public Integer getRosterSeatX() {
        return rosterSeatX;
    }

    public void setRosterSeatX(Integer rosterSeatX) {
        this.rosterSeatX = rosterSeatX;
    }

    public Integer getRosterSeatY() {
        return rosterSeatY;
    }

    public void setRosterSeatY(Integer rosterSeatY) {
        this.rosterSeatY = rosterSeatY;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getAttendance() {
        return isAttendance;
    }

    public void setAttendance(Boolean attendance) {
        isAttendance = attendance;
    }
}
