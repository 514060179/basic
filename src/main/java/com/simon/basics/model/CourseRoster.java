package com.simon.basics.model;

import java.util.Date;

public class CourseRoster {
    private Long rosterId;

    private Long courseId;

    private Long accountId;

    private String rosterName;

    private Integer rosterCourseCountRest;

    private Integer rosterSeatX;

    private Integer rosterSeatY;

    private Date createTime;

    private Date updateTime;

    public Long getRosterId() {
        return rosterId;
    }

    public void setRosterId(Long rosterId) {
        this.rosterId = rosterId;
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

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName == null ? null : rosterName.trim();
    }

    public Integer getRosterCourseCountRest() {
        return rosterCourseCountRest;
    }

    public void setRosterCourseCountRest(Integer rosterCourseCountRest) {
        this.rosterCourseCountRest = rosterCourseCountRest;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}