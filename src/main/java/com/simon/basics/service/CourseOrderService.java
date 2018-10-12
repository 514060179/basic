package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.CourseOrder;

import java.util.List;

/**
 * @Author simon.feng
 * @Date Created in 21:00 2018/9/18/018
 * @Modificd
 */
public interface CourseOrderService {

    /**
     * 获取列表
     *
     * @param courseOrder 条件
     * @param pageNum     页码
     * @param pageSize    页数
     * @return
     */
    PageInfo<CourseOrder> getListByPage(CourseOrder courseOrder, int pageNum, int pageSize);

    /**
     * 创建订单
     *
     * @param classCourse
     * @return
     */
    CourseOrder create(ClassCourse classCourse);
    /**
     * 根据课程id查询订单
     *
     * @param courseId
     * @return
     */
    CourseOrder findOneByCourseId(Long courseId);


    int paySuccess(Long orderId);
}
