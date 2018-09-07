package com.simon.basics.service;

import com.simon.basics.model.User;

/**
 * @author fengtianying
 * @date 2018/9/6 16:47
 */
public interface UserService {

    User findById(Long userId);
}
