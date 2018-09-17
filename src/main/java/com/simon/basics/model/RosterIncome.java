package com.simon.basics.model;

import java.math.BigDecimal;
import java.util.Date;

public class RosterIncome {
    private Long incomeId;

    private Long accountId;

    private Long courseId;

    private Integer incomeSectionNum;

    private String incomeType;

    private Integer mustNumber;

    private Integer actualNumber;

    private BigDecimal totalHour;

    private BigDecimal averageHour;

    private BigDecimal averageHourCost;

    private BigDecimal percentage;

    private Integer exceedNum;

    private BigDecimal averageCourse;

    private BigDecimal incomeAmount;

    private Date createTime;

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