package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.simon.basics.dao.RosterIncomeMapper;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.User;
import com.simon.basics.service.RosterIncomeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author simon.feng
 * @Date Created in 15:02 2018/9/29/029
 * @Modificd
 */
@Service
public class RosterIncomeServiceImpl implements RosterIncomeService {

    @Autowired
    private RosterIncomeMapper rosterIncomeMapper;

    @Override
    public List<RosterIncome> findListByPage(RosterIncome rosterIncome, Integer pageNum, Integer pageSize) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (EnumCode.UserType.TYPE_TEACHER.getValue().equals(user.getType())) {
            rosterIncome.setAccountId(user.getAccountId());
        }
        PageHelper.startPage(pageNum, pageSize);
        return rosterIncomeMapper.findListByCondition(rosterIncome);
    }
}
