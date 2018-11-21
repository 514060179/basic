package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class CourseOrder {
    @ApiModelProperty(value = "课程订单id")
    private Long orderId;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "订单名称")
    private String orderName;
    @ApiModelProperty(value = "课程id")
    private Long courseId;
    @ApiModelProperty(value = "用户（老师）id")
    private Long accountId;
    @ApiModelProperty(value = "总课时")
    private Integer courseTotal;
    @ApiModelProperty(value = "订单状态:0未支付1成功2申请退款3退款")
    private String orderStatus;
    @ApiModelProperty(value = "支付金额")
    private BigDecimal orderCost;
    @ApiModelProperty(value = "支付状态")
    private String orderPayStatus;
    @ApiModelProperty(value = "支付状态",hidden = true)
    private String orderPayWay;
    @ApiModelProperty(value = "扫码支付url")
    private String wechatPayUrl;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderPayTime;

    @ApiModelProperty(value = "筛选条件:日期.格式:2018/10")
    private String[] date;

    public String getWechatPayUrl() {
        return wechatPayUrl;
    }

    public void setWechatPayUrl(String wechatPayUrl) {
        this.wechatPayUrl = wechatPayUrl;
    }

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
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

    public Integer getCourseTotal() {
        return courseTotal;
    }

    public void setCourseTotal(Integer courseTotal) {
        this.courseTotal = courseTotal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderPayStatus() {
        return orderPayStatus;
    }

    public void setOrderPayStatus(String orderPayStatus) {
        this.orderPayStatus = orderPayStatus == null ? null : orderPayStatus.trim();
    }

    public String getOrderPayWay() {
        return orderPayWay;
    }

    public void setOrderPayWay(String orderPayWay) {
        this.orderPayWay = orderPayWay;
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