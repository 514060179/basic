package com.simon.basics.dao;

import com.simon.basics.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findListByUserId(Long userId);

    List<Role> findAll();

    List<Map<String,String>> findCustomRolesAuthorization();
}