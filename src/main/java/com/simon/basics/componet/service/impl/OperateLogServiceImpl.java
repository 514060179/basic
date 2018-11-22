package com.simon.basics.componet.service.impl;

import com.simon.basics.componet.service.OperateLogService;
import com.simon.basics.dao.OperateLogMapper;
import com.simon.basics.model.OperateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fengtianying
 * @date 2018/11/21 17:18
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;
    @Override
    public int add(OperateLog operateLog) {
        return operateLogMapper.insertSelective(operateLog);
    }
}
