package com.simon.basics.model;

import java.math.BigDecimal;
import java.util.Date;

public class RefundOrder {
    private Long refundId;

    private Long orderId;

    private Long courseId;

    private Long accountId;

    private BigDecimal amount;

    private BigDecimal actualAmount;

    private BigDecimal courseAmount;

    private String orderPayWay;

    private Integer courseTotal;

    private Integer refundCourseTotal;

    private String refundStatus;

    private Date createTime;

    private Date updateTime;

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getCourseAmount() {
        return courseAmount;
    }

    public void setCourseAmount(BigDecimal courseAmount) {
        this.courseAmount = courseAmount;
    }

    public String getOrderPayWay() {
        return orderPayWay;
    }

    public void setOrderPayWay(String orderPayWay) {
        this.orderPayWay = orderPayWay == null ? null : orderPayWay.trim();
    }

    public Integer getCourseTotal() {
        return courseTotal;
    }

    public void setCourseTotal(Integer courseTotal) {
        this.courseTotal = courseTotal;
    }

    public Integer getRefundCourseTotal() {
        return refundCourseTotal;
    }

    public void setRefundCourseTotal(Integer refundCourseTotal) {
        this.refundCourseTotal = refundCourseTotal;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
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