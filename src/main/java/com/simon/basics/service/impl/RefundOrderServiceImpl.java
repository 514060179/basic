package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.CourseOrderMapper;
import com.simon.basics.dao.RefundOrderMapper;
import com.simon.basics.model.*;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.service.RefundOrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author fengtianying
 * @date 2018/11/1 15:28
 */
@Service
public class RefundOrderServiceImpl implements RefundOrderService {

    @Autowired
    private RefundOrderMapper refundOrderMapper;
    @Autowired
    private CourseOrderMapper courseOrderMapper;
    @Override
    public PageInfo<RefundOrderWithUser> getListByPage(RefundOrder refundOrder, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(refundOrderMapper.findListByCondition(refundOrder));
    }

    @Override
    public RefundOrder findOneByKey(Long refundId) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        Long accountId = null;
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            accountId = user.getAccountId();
        }
        return refundOrderMapper.selectByPrimaryKey(refundId,accountId);
    }

    @Override
    public int refunded(RefundOrder refundOrder) {
        //1.更新退款订单实际回退金额以及状态
        RefundOrder updateRefund = new RefundOrder();
        updateRefund.setRefundId(refundOrder.getRefundId());
        BigDecimal actualAmount = refundOrder.getActualAmount();
        if (Objects.isNull(actualAmount)||actualAmount.equals(0.00)){
            actualAmount = refundOrder.getAmount();
        }
        updateRefund.setActualAmount(actualAmount);
        updateRefund.setRefundStatus(EnumCode.OrderStatus.ORDER_REBACK.getValue());
        int i = refundOrderMapper.updateByPrimaryKeySelective(updateRefund);
        //2.更新支付订单状态
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setOrderId(refundOrder.getOrderId());
        courseOrder.setOrderStatus(EnumCode.OrderStatus.ORDER_REBACK.getValue());
        i += courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
        if (i!=2){
              throw new RuntimeException("确认回退款失败！");
        }
        return i;
    }

    @Override
    public RefundOrder findOneByOrderId(Long orderId) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        Long accountId = null;
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            accountId = user.getAccountId();
        }
        return refundOrderMapper.findOneByOrderId(orderId,accountId);
    }
}