package com.simon.basics.service;

import com.simon.basics.model.*;

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

    /**
     * 初始化seatLayout到redis
     */
    void pullAllToRedis();

    /**
     * 添加课程名单
     * @param courseOrder
     * @param seatX
     * @param seatY
     * @return
     */
    CourseRoster addRoster(CourseOrderWithBLOBs courseOrder , int seatX, int seatY);


}
