package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class RefundOrder {

    @ApiModelProperty(value = "id")
    private Long refundId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "用户id")
    private Long accountId;

    @ApiModelProperty(value = "申请退款金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "实际退款金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "课程总额")
    private BigDecimal courseAmount;

    @ApiModelProperty(value = "订单支付方式")
    private String orderPayWay;

    @ApiModelProperty(value = "课程名字")
    private String courseName;

    @ApiModelProperty(value = "总课时")
    private Integer courseTotal;

    @ApiModelProperty(value = "回退课时")
    private Integer refundCourseTotal;

    @ApiModelProperty(value = "状态2申请中3退款成功")
    private String refundStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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