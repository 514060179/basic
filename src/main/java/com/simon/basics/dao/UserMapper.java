package com.simon.basics.dao;

import com.simon.basics.model.User;

import java.util.List;

public interface UserMapper {

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User findByUserName(String userName);

    List<User> findListByCondition(User user);
}