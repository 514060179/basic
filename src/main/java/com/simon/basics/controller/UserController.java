package com.simon.basics.controller;

import com.simon.basics.dao.UserMapper;
import com.simon.basics.model.ReturnParam;
import com.simon.basics.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fengtianying
 * @date 2018/9/4 13:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/findById/{userId}")
    public ReturnParam findById(@PathVariable("userId") Integer userId){
        logger.error("sadasdas1");
        logger.info("sadasdas2");
        return ReturnParam.success(userMapper.selectByPrimaryKey(userId));
    }
    @GetMapping("/findById2")
    public ReturnParam findById2(@RequestParam Integer userId){
        logger.error("sadasdas3");
        logger.info("sadasdas4");
        return ReturnParam.success(userMapper.selectByPrimaryKey(userId));
    }
    @GetMapping("/login")
    public ReturnParam login(@RequestParam Integer userId){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token =
                new UsernamePasswordToken("simon","123");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        if (!subject.isAuthenticated()) {
            //使用shiro来验证
//			token.setRememberMe(true);
            try {
                subject.login(token);//验证角色和权限
            } catch (AuthenticationException ex) {
                System.out.println("登陆失败: " + ex.getMessage());
            }
        }
        return ReturnParam.success(userMapper.selectByPrimaryKey(userId));
    }
}
