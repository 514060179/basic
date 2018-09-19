package com.simon.basics.model;

import io.swagger.annotations.ApiModelProperty;

public class ClassCourseWithBLOBs extends ClassCourse {

    @ApiModelProperty(hidden = true)
    private CourseType courseType;

    @ApiModelProperty(hidden = true)
    private User user;

    @ApiModelProperty(hidden = true)
    private SeatLayout seatLayout;

    @ApiModelProperty(hidden = true)
    private Boolean bought;

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