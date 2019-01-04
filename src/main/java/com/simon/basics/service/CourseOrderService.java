package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.*;

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
    PageInfo<CourseOrder> getListByPage(CourseOrderWithBLOBs courseOrder, int pageNum, int pageSize);

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
    CourseOrder findOneByCourseId(Long courseId,Long accountId);

    /**
     * 根据订单id查询订单
     *
     * @param orderId
     * @return
     */
    CourseOrder findOneByOrderId(Long orderId);

    /**
     * 根据订单编号查询订单
     *
     * @param orderNo
     * @return
     */
    CourseOrder findOneByOrderNo(String orderNo);


    int paySuccess(Long orderId, String orderNo, String payWay);

    /**
     * 申请退款
     * @param classCourse
     * @param courseOrder
     * @param courseRoster
     * @return
     */
    RefundOrder applyback(ClassCourse classCourse, CourseOrder courseOrder, CourseRoster courseRoster);


    /**
     * 保存微信支付url
     * @param orderId
     * @param wechatUrl
     * @return
     */
    int updateWechatUrl(Long orderId,String wechatUrl);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    int cancel(Long orderId);
}
