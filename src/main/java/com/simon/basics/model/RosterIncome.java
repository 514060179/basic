package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class RosterIncome {

    @ApiModelProperty(value = "id")
    private Long incomeId;

    @ApiModelProperty(value = "id")
    private Long accountId;

    @ApiModelProperty(value = "id")
    private Long courseId;

    @ApiModelProperty(value = "课程章节数")
    private Integer incomeSectionNum;

    @ApiModelProperty(value = "收入类型:1按时收入2按分成")
    private String incomeType;

    @ApiModelProperty(value = "需到人数")
    private Integer mustNumber;

    @ApiModelProperty(value = "实到人数")
    private Integer actualNumber;

    @ApiModelProperty(value = "总上课时长")
    private BigDecimal totalHour;

    @ApiModelProperty(value = "收费类型为1时,每【average_hour】时为一个收费单位,固定0.5小时为一个单位",hidden = true)
    private BigDecimal averageHour;

    @ApiModelProperty(value = "收费类型为1时,每一个收费单位(0.5小时)收取的费用")
    private BigDecimal averageHourCost;

    @ApiModelProperty(value = "收费类型为1时,超过【exceedNum】人数,提成【percentage】个点;收费类型为2时,每节课提成点")
    private BigDecimal percentage;

    @ApiModelProperty(value = "收费类型为1时,提成条件:超过【exceedNum】人数")
    private Integer exceedNum;

    @ApiModelProperty(value = "课程每节收费")
    private BigDecimal averageCourse;

    @ApiModelProperty(value = "收入费用")
    private BigDecimal incomeAmount;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getIncomeSectionNum() {
        return incomeSectionNum;
    }

    public void setIncomeSectionNum(Integer incomeSectionNum) {
        this.incomeSectionNum = incomeSectionNum;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType == null ? null : incomeType.trim();
    }

    public Integer getMustNumber() {
        return mustNumber;
    }

    public void setMustNumber(Integer mustNumber) {
        this.mustNumber = mustNumber;
    }

    public Integer getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(Integer actualNumber) {
        this.actualNumber = actualNumber;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getAverageHour() {
        return averageHour;
    }

    public void setAverageHour(BigDecimal averageHour) {
        this.averageHour = averageHour;
    }

    public BigDecimal getAverageHourCost() {
        return averageHourCost;
    }

    public void setAverageHourCost(BigDecimal averageHourCost) {
        this.averageHourCost = averageHourCost;
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

    public BigDecimal getAverageCourse() {
        return averageCourse;
    }

    public void setAverageCourse(BigDecimal averageCourse) {
        this.averageCourse = averageCourse;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
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