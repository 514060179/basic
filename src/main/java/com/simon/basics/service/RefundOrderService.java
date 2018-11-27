package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.CourseRoster;
import com.simon.basics.model.RefundOrder;
import com.simon.basics.model.RefundOrderWithUser;

/**
 * @author fengtianying
 * @date 2018/11/1 15:18
 */
public interface RefundOrderService {

    /**
     * 获取列表
     * @param refundOrder
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<RefundOrderWithUser> getListByPage(RefundOrder refundOrder, int pageNum, int pageSize);

    /**
     *  refundId 查询详情
     * @param refundId
     * @return
     */
    RefundOrder findOneByKey(Long refundId);

    /**
     * 确定退款成功
     * @param refundOrder
     * @return
     */
    int refunded(RefundOrder refundOrder, CourseRoster courseRoster);

    /**
     * orderId 查询详情
     * @param orderId
     * @return
     */
    RefundOrder findOneByOrderId(Long orderId);

    /**
     * 取消退款
     * @param refundId
     * @param orderId
     * @return
     */
    int cancel(Long refundId,Long orderId);
}
