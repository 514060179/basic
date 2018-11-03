package com.simon.basics.dao;

import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.RosterIncomeWithOther;

import java.util.List;

public interface RosterIncomeMapper {
    int deleteByPrimaryKey(Long incomeId);

    int insert(RosterIncome record);

    int insertSelective(RosterIncome record);

    RosterIncome selectByPrimaryKey(Long incomeId);

    int updateByPrimaryKeySelective(RosterIncome record);

    int updateByPrimaryKey(RosterIncome record);

    List<RosterIncomeWithOther> findListByCondition(RosterIncome rosterIncome);
}