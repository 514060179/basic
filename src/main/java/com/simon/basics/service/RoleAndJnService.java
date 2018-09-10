package com.simon.basics.service;

import com.simon.basics.model.Role;

import java.util.List;
import java.util.Map;

/**
 * @author fengtianying
 * @date 2018/9/7 11:52
 */
public interface RoleAndJnService {

    List<Role> findAll();

    List<Map<String,String>> findCustomRolesAuthorization();

    List<String> findListByAccountId(Long userId);
}
