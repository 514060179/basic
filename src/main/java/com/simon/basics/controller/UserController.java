package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.dao.UserMapper;
import com.simon.basics.model.ReturnParam;
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
    @GetMapping("/findById/{userId}")
    public ReturnParam findById(@PathVariable("userId") Integer userId){
        logger.error("sadasdas1");
        logger.info("sadasdas2");
        return ReturnParam.success(userService.findById(userId));
    }
    @GetMapping("/findById2")
    public ReturnParam findById2(@RequestParam Integer userId){
        logger.error("sadasdas3");
        logger.info("sadasdas4");
        return ReturnParam.success(userService.findById(userId));
    }
    @GetMapping("/login")
    public ReturnParam login(@RequestParam Integer userId){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token =
                new UsernamePasswordToken("simon","1233");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        if (!subject.isAuthenticated()) {
            //使用shiro来验证
//			token.setRememberMe(true);
            try {
                subject.login(token);//验证角色和权限
            } catch (IncorrectCredentialsException e1) {
                System.out.println("密码验证失败: " + e1.getMessage());
            }catch (AuthenticationException e2) {
                System.out.println("登陆失败: " + e2.getMessage());
            }
        }
        return ReturnParam.success(userService.findById(userId));
    }
    @GetMapping("/logout")
    public ReturnParam logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return ReturnParam.success();
    }
    @GetMapping("/redis")
    public ReturnParam redis(@RequestParam Integer userId){
        User user = userService.findById(userId);
        jedisService.put("user",user);
        return ReturnParam.success(jedisService.getObject("user",User.class));
    }
    @GetMapping("/update")
    public ReturnParam update(@RequestParam Integer userId){
        userService.updateTest();
        return ReturnParam.success();
    }
}
