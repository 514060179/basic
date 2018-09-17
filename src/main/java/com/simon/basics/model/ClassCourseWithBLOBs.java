package com.simon.basics.model;

public class ClassCourseWithBLOBs extends ClassCourse {
    private String courseAbstract;

    private String courseRemark;

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
}