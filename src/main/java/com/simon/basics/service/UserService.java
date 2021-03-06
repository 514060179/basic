package com.simon.basics.service;

import com.simon.basics.model.User;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/6 16:47
 */
public interface UserService {


    User findByUserName(String userName);

    List<User> findListByPage(User user, int pageNum, int pageSize);
}
