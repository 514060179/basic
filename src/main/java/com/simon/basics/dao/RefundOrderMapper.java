package com.simon.basics.dao;

import com.simon.basics.model.RefundOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RefundOrderMapper {
    int deleteByPrimaryKey(Long refundId);

    int insert(RefundOrder record);

    int insertSelective(RefundOrder record);

    RefundOrder selectByPrimaryKey(@Param("refundId") Long refundId, @Param("accountId")Long accountId);

    RefundOrder findOneByOrderId(@Param("orderId") Long orderId, @Param("accountId")Long accountId);

    int updateByPrimaryKeySelective(RefundOrder record);

    int updateByPrimaryKey(RefundOrder record);

    List<RefundOrder> findListByCondition(RefundOrder record);
}