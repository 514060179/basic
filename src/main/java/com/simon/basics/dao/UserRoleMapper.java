package com.simon.basics.dao;

import com.simon.basics.model.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long userRoleId);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long userRoleId);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}