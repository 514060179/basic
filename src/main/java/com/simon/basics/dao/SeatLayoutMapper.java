package com.simon.basics.dao;

import com.simon.basics.model.SeatLayout;

public interface SeatLayoutMapper {
    int deleteByPrimaryKey(Long seatId);

    int insert(SeatLayout record);

    int insertSelective(SeatLayout record);

    SeatLayout selectByPrimaryKey(Long seatId);

    int updateByPrimaryKeySelective(SeatLayout record);

    int updateByPrimaryKey(SeatLayout record);
}