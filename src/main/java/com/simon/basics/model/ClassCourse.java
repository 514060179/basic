package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date courseStartTime;

    @ApiModelProperty(value = "开始日期",hidden = true)
    private String courseStartDateStr;
    @ApiModelProperty(value = "上课时间",hidden = true)
    private String classStartTimeStr;
    @ApiModelProperty(value = "结束日期",hidden = true)
    private String courseEndDateStr;
    @ApiModelProperty(value = "下课时间",hidden = true)
    private String classEndTimeStr;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期")
    private Date courseEndTime;

    @ApiModelProperty(value = "课程状态：-1取消0新建未发布1已发布2进行中3结束")
    private String courseStatus;

    @ApiModelProperty(value = "上下课状态：0上课1下课")
    private String classStatus;

    @ApiModelProperty(value = "收费类型：1按时2按提成")
    private String chargeType;

    @ApiModelProperty(value = "当前课时")
    private Integer courseCurrent;

    @ApiModelProperty(value = "总课时")
    private Integer courseTotal;

    @ApiModelProperty(hidden = true)
    private Boolean deleted;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Date updateTime;


    @ApiModelProperty(value = "课程描述")
    private String courseAbstract;

    @ApiModelProperty(value = "课程备注")
    private String courseRemark;


    @ApiModelProperty(value = "(默认半小时,暂不用)收费类型为1时：按【averageHour】小时收费",hidden = true)
    private BigDecimal averageHour;
    @ApiModelProperty(value = "收费类型为2时:每节课提成点",required = true)
    private BigDecimal percentage;
    @ApiModelProperty(value = "收费类型为1时：超过人数【exceedNum】才有提成【extraCharge】",required = true)
    private Integer exceedNum;
    @ApiModelProperty(value = "收费类型为1时 按【averageHour】小时收费【averageHourCost】",required = true)
    private BigDecimal averageHourCost;
    @ApiModelProperty(value = "收费类型为1时 超过【exceedNum】提成【extraCharge】元",required = true)
    private BigDecimal extraCharge;


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

    public String getClassStatus() {
        return classStatus;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
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

    public String getCourseStartDateStr() {
        if (courseStartTime!=null){
            return new SimpleDateFormat("yyyy-MM-dd").format(courseStartTime);
        }
        return courseStartDateStr;
    }

    public void setCourseStartDateStr(String courseStartDateStr) {
        this.courseStartDateStr = courseStartDateStr;
    }

    public String getClassStartTimeStr() {
        if (courseStartTime!=null){
            return new SimpleDateFormat("HH:mm:ss").format(courseStartTime);
        }
        return classStartTimeStr;
    }

    public void setClassStartTimeStr(String classStartTimeStr) {
        this.classStartTimeStr = classStartTimeStr;
    }

    public String getCourseEndDateStr() {
        if (courseEndTime!=null){
            return new SimpleDateFormat("yyyy-MM-dd").format(courseEndTime);
        }
        return courseEndDateStr;
    }

    public void setCourseEndDateStr(String courseEndDateStr) {
        this.courseEndDateStr = courseEndDateStr;
    }

    public String getClassEndTimeStr() {
        if (courseEndTime!=null){
            return new SimpleDateFormat("HH:mm:ss").format(courseEndTime);
        }
        return classEndTimeStr;
    }

    public BigDecimal getAverageHour() {
        return averageHour;
    }

    public void setAverageHour(BigDecimal averageHour) {
        this.averageHour = averageHour;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getExceedNum() {
        return exceedNum;
    }

    public void setExceedNum(Integer exceedNum) {
        this.exceedNum = exceedNum;
    }

    public BigDecimal getAverageHourCost() {
        return averageHourCost;
    }

    public void setAverageHourCost(BigDecimal averageHourCost) {
        this.averageHourCost = averageHourCost;
    }

    public BigDecimal getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(BigDecimal extraCharge) {
        this.extraCharge = extraCharge;
    }

    public void setClassEndTimeStr(String classEndTimeStr) {
        this.classEndTimeStr = classEndTimeStr;
    }
}