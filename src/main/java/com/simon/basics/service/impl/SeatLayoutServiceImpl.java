package com.simon.basics.service.impl;

import com.simon.basics.dao.SeatLayoutMapper;
import com.simon.basics.model.SeatLayout;
import com.simon.basics.service.SeatLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 17:41
 */
@Service
public class SeatLayoutServiceImpl implements SeatLayoutService {

    @Autowired
    private SeatLayoutMapper seatLayoutMapper;
    @Override
    public List<SeatLayout> findAllList() {
        return seatLayoutMapper.findAllList();
    }
}
