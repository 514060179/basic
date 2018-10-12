package com.simon.basics.dao;

import com.simon.basics.model.Account;
import com.simon.basics.model.User;

import java.util.List;

public interface UserMapper {

    int insertSelective(User record);

    int insertAccountSelective(Account account);

    int updateByPrimaryKeySelective(User record);

    int delete(Long accountId);

    User findByUserName(String userName);

    List<User> findListByCondition(User user);
}