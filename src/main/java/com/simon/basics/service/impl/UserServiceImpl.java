package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.RoleMapper;
import com.simon.basics.dao.UserMapper;
import com.simon.basics.dao.UserRoleMapper;
import com.simon.basics.model.Account;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.User;
import com.simon.basics.model.UserRole;
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

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public User findByAccountId(Long accountId) {
        User user = new User();
        user.setAccountId(accountId);
        List<User> userList = userMapper.findListByCondition(user);
        if (userList!=null&&userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<User> findListByPage(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<User>(userMapper.findListByCondition(user));
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
        String type = user.getType();
        UserRole userRole = new UserRole();
        userRole.setAccountId(user.getAccountId());
        if (EnumCode.UserType.TYPE_MANAGER.getValue().equals(type)){//管理员
            userRole.setRoleId(3L);
        }else if(EnumCode.UserType.TYPE_TEACHER.getValue().equals(type)){//老师
            userRole.setRoleId(2L);
        }else if (EnumCode.UserType.TYPE_SUPER.getValue().equals(type)){//超级用户
            userRole.setRoleId(4L);
        }else{//学生
            userRole.setRoleId(1L);
        }
        userRoleMapper.insertSelective(userRole);
        return i;
    }

    @Override
    public int update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        return 0;
    }

    @Override
    public Account addManager(Account account) {
        userMapper.insertAccountSelective(account);
        return account;
    }

    @Override
    public int deleteUser(Long accountId) {
        return userMapper.delete(accountId);
    }


}
