package com.simon.basics.service;

import com.simon.basics.model.SeatLayout;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:40
 */
public interface SeatLayoutService {

    /**
     * 获取所有列表
     * @return
     */
    List<SeatLayout> findAllList();

}
