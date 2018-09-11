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

    /**
     * 通过accountId获取权限
     * @param accountId
     * @return
     */
    List<String> findListByAccountId(Long accountId);

    /**
     * 通过accountId获取角色
     * @param accountId
     * @return
     */
    List<String> findRoleListByAccountId(Long accountId);
}
