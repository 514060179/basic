package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.RosterIncomeWithOther;

import java.util.List;

/**
 * @Author simon.feng
 * @Date Created in 15:00 2018/9/29/029
 * @Modificd
 */
public interface RosterIncomeService {

    PageInfo<RosterIncomeWithOther> findListByPage(RosterIncome rosterIncome, Integer pageNum, Integer pageSize);

    int handle(String incomeIds, Boolean handled);
}
