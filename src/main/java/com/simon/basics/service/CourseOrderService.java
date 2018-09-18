package com.simon.basics.service;

import com.simon.basics.model.ClassCourse;

/**
 * @Author simon.feng
 * @Date Created in 21:00 2018/9/18/018
 * @Modificd
 */
public interface CourseOrderService {

    /**
     * 创建订单
     * @param classCourse
     * @return
     */
    int create(ClassCourse classCourse);
}
