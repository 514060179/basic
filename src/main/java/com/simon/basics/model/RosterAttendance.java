package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RosterAttendance {
    private Long attendanceId;

    private Long courseId;

    private Long accountId;

    private Integer additionalSeatX;

    private Integer additionalSeatY;

    private Integer attendSectionNum;

    private String attendName;

    private String attendRemark;

    private String attendType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

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

    public Integer getAttendSectionNum() {
        return attendSectionNum;
    }

    public Integer getAdditionalSeatX() {
        return additionalSeatX;
    }

    public void setAdditionalSeatX(Integer additionalSeatX) {
        this.additionalSeatX = additionalSeatX;
    }

    public Integer getAdditionalSeatY() {
        return additionalSeatY;
    }

    public void setAdditionalSeatY(Integer additionalSeatY) {
        this.additionalSeatY = additionalSeatY;
    }

    public void setAttendSectionNum(Integer attendSectionNum) {
        this.attendSectionNum = attendSectionNum;
    }

    public String getAttendName() {
        return attendName;
    }

    public void setAttendName(String attendName) {
        this.attendName = attendName == null ? null : attendName.trim();
    }

    public String getAttendRemark() {
        return attendRemark;
    }

    public void setAttendRemark(String attendRemark) {
        this.attendRemark = attendRemark == null ? null : attendRemark.trim();
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

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Object getEndTime() {
        return endTime;

    }

    public String getAttendType() {
        return attendType;
    }

    public void setAttendType(String attendanceType) {
        this.attendType = attendanceType;
    }
}