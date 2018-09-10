package com.simon.basics.dao;

import com.simon.basics.model.User;

public interface UserMapper {

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User findByUserName(String userName);
}