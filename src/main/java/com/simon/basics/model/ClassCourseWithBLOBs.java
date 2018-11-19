package com.simon.basics.model;

import io.swagger.annotations.ApiModelProperty;

public class ClassCourseWithBLOBs extends ClassCourse {

    @ApiModelProperty(hidden = true)
    private CourseType courseType;

    @ApiModelProperty(hidden = true)
    private User user;

    @ApiModelProperty(hidden = true)
    private SeatLayout seatLayout;

    @ApiModelProperty("订单状态")
    private String orderStatus;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty(hidden = true)
    private Boolean bought;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SeatLayout getSeatLayout() {
        return seatLayout;
    }

    public void setSeatLayout(SeatLayout seatLayout) {
        this.seatLayout = seatLayout;
    }
}