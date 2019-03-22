package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.RosterIncomeMapper;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.RosterIncomeWithOther;
import com.simon.basics.model.User;
import com.simon.basics.service.RosterIncomeService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public PageInfo<RosterIncomeWithOther> findListByPage(RosterIncome rosterIncome, Integer pageNum, Integer pageSize) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (EnumCode.UserType.TYPE_TEACHER.getValue().equals(user.getType())) {
            rosterIncome.setAccountId(user.getAccountId());
        }
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<RosterIncomeWithOther>(rosterIncomeMapper.findListByCondition(rosterIncome));
    }

    @Override
    public int handle(String incomeIds, Boolean handled) {
        return rosterIncomeMapper.handle(Arrays.asList(incomeIds.split(",")),handled);
    }
}
