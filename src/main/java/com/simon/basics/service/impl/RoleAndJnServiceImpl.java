package com.simon.basics.service.impl;

import com.simon.basics.dao.RoleMapper;
import com.simon.basics.model.Role;
import com.simon.basics.service.RoleAndJnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fengtianying
 * @date 2018/9/7 11:52
 */
public class RoleAndJnServiceImpl implements RoleAndJnService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public List<Map<String, String>> findCustomRolesAuthorization() {
        return roleMapper.findCustomRolesAuthorization();
    }

    @Override
    public List<Role> findListByUserId(Long userId) {
        return roleMapper.findListByUserId(userId);
    }
}
