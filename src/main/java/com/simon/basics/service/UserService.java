package com.simon.basics.service;

import com.simon.basics.model.Account;
import com.simon.basics.model.User;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/6 16:47
 */
public interface UserService {


    User findByUserName(String userName);

    User findByAccountId(Long accountId);

    List<User> findListByPage(User user, int pageNum, int pageSize);

    int add(User user);

    int update(User user);

    Account addManager(Account account);

    /**
     * 删除用户
     * @return
     */
    int deleteUser(Long accountId);
}
