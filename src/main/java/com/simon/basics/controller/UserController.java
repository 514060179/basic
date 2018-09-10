package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.model.User;
import com.simon.basics.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试使用
 * @author fengtianying
 * @date 2018/9/4 13:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private JedisService jedisService;
    @PostMapping("/login")
    public ReturnParam login(@RequestParam String userName,@RequestParam String password){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(userName,password);
        if (!subject.isAuthenticated()) {
            //使用shiro来验证
//			token.setRememberMe(true);
            try {
                subject.login(token);//验证角色和权限
            } catch (IncorrectCredentialsException e1) {
                logger.error("密码验证失败！",e1);
                return ReturnParam.incorrectCredentials();
            }catch (AuthenticationException e2) {
                logger.error("密码验证失败！",e2);
                return ReturnParam.incorrectCredentials();
            }
        }
        return ReturnParam.success(subject.getSession().getAttribute("currentUser"));
    }

    @GetMapping("/logout")
    public ReturnParam logout(){
        SecurityUtils.getSubject().logout();
        return ReturnParam.success();
    }
}
