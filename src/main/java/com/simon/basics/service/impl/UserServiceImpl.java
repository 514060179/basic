package com.simon.basics.service.impl;

import com.simon.basics.dao.RoleMapper;
import com.simon.basics.dao.UserMapper;
import com.simon.basics.model.Role;
import com.simon.basics.model.User;
import com.simon.basics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fengtianying
 * @date 2018/9/6 16:48
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public User findById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Transactional
    @Override
    public int updateTest() {
        User user = new User();
        user.setUid(2);
        user.setPassword("1231231231");
        userMapper.updateByPrimaryKeySelective(user);
        Role role = new Role();
        role.setRid(1);
        role.setRname("admin");
        int i = roleMapper.updateByPrimaryKeySelective(role);
        if (i==1){
            throw new RuntimeException();
        }
        return 0;
    }
}
