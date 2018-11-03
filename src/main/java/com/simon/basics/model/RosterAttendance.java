package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class RosterAttendance {

    @ApiModelProperty(value = "id")
    private Long attendanceId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "用户id")
    private Long accountId;

    @ApiModelProperty(value = "串课x坐标")
    private Integer additionalSeatX;

    @ApiModelProperty(value = "串课y坐标")
    private Integer additionalSeatY;

    @ApiModelProperty(value = "课程的节数")
    private Integer attendSectionNum;

    @ApiModelProperty(value = "考勤名字")
    private String attendName;

    @ApiModelProperty(value = "备注")
    private String attendRemark;

    @ApiModelProperty(value = "出勤类型:1学生2老师3串课4缺席")
    private String attendType;

    @ApiModelProperty(value = "下课时间",hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
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