package com.simon.basics.dao;

import com.simon.basics.model.RefundOrder;

public interface RefundOrderMapper {
    int deleteByPrimaryKey(Long refundId);

    int insert(RefundOrder record);

    int insertSelective(RefundOrder record);

    RefundOrder selectByPrimaryKey(Long refundId);

    int updateByPrimaryKeySelective(RefundOrder record);

    int updateByPrimaryKey(RefundOrder record);
}