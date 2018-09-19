package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ClassCourse {

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "座位id")
    private Long seatId;

    @ApiModelProperty(value = "账户id")
    private Long accountId;

    @ApiModelProperty(value = "课程类型id")
    private Long typeId;

    @ApiModelProperty(value = "课程名字")
    private String courseName;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal courseCost;

    @ApiModelProperty(value = "开始时间")
    private Date courseStartTime;

    @ApiModelProperty(value = "结束日期")
    private Date courseEndTime;

    @ApiModelProperty(hidden = true)
    private String courseStatus;

    @ApiModelProperty(value = "当前课时")
    private Integer courseCurrent;

    @ApiModelProperty(value = "总课时")
    private Integer courseTotal;

    @ApiModelProperty(hidden = true)
    private Boolean deleted;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Date updateTime;


    @ApiModelProperty(value = "课程描述")
    private String courseAbstract;

    @ApiModelProperty(value = "课程备注")
    private String courseRemark;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public BigDecimal getCourseCost() {
        return courseCost;
    }

    public void setCourseCost(BigDecimal courseCost) {
        this.courseCost = courseCost;
    }

    public Date getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(Date courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public Date getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(Date courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus == null ? null : courseStatus.trim();
    }

    public Integer getCourseCurrent() {
        return courseCurrent;
    }

    public void setCourseCurrent(Integer courseCurrent) {
        this.courseCurrent = courseCurrent;
    }

    public Integer getCourseTotal() {
        return courseTotal;
    }

    public void setCourseTotal(Integer courseTotal) {
        this.courseTotal = courseTotal;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCourseAbstract() {
        return courseAbstract;
    }

    public void setCourseAbstract(String courseAbstract) {
        this.courseAbstract = courseAbstract == null ? null : courseAbstract.trim();
    }

    public String getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark == null ? null : courseRemark.trim();
    }
}