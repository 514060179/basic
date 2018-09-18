package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.simon.basics.dao.UserMapper;
import com.simon.basics.model.User;
import com.simon.basics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/6 16:48
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public List<User> findListByPage(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.findListByCondition(user);
    }


    @Transactional
    @Override
    public int add(User user) {
        int i = userMapper.insertAccountSelective(user);
        if (i>0){
            userMapper.insertSelective(user);
        }else{
            throw new RuntimeException("新增用户出错！");
        }
        return i;
    }

    @Override
    public int update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return 0;
    }


}
