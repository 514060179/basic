package com.simon.basics.model;

import java.util.Date;

public class RosterAttendance {
    private Integer attendanceId;

    private Long courseId;

    private Long accountId;

    private Integer attendSectionNum;

    private String attendName;

    private String attendRemark;

    private Date createTime;

    private Date updateTime;

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
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
}