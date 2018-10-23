package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CourseRoster {
    @ApiModelProperty("id")
    private Long rosterId;

    @ApiModelProperty("课程id")
    private Long courseId;

    @ApiModelProperty("账户id")
    private Long accountId;

    @ApiModelProperty("名单名称")
    private String rosterName;

    @ApiModelProperty("课程剩余数量")
    private Integer rosterCourseCountRest;

    @ApiModelProperty("座位坐标x")
    private Integer rosterSeatX;

    @ApiModelProperty("座位坐标y")
    private Integer rosterSeatY;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("用户model")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}