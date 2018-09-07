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
    @GetMapping("/findById/{userId}")
    public ReturnParam findById(@PathVariable("userId") Long userId){
        logger.error("sadasdas1");
        logger.info("sadasdas2");
        return ReturnParam.success(userService.findById(userId));
    }
    @GetMapping("/findById2")
    public ReturnParam findById2(@RequestParam Long userId){
        logger.error("sadasdas3");
        logger.info("sadasdas4");
        return ReturnParam.success(userService.findById(userId));
    }
    @GetMapping("/login")
    public ReturnParam login(@RequestParam Long userId){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token =
                new UsernamePasswordToken("simon","1233");
        if (!subject.isAuthenticated()) {
            //使用shiro来验证
//			token.setRememberMe(true);
            try {
                subject.login(token);//验证角色和权限
            } catch (IncorrectCredentialsException e1) {
                System.out.println("密码验证失败: " + e1.getMessage());
                return ReturnParam.incorrectCredentials();
            }catch (AuthenticationException e2) {
                System.out.println("登陆失败: " + e2.getMessage());
                return ReturnParam.incorrectCredentials();
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
    public ReturnParam redis(@RequestParam Long userId){
        User user = userService.findById(userId);
        jedisService.put("user",user);
        return ReturnParam.success(jedisService.getObject("user",User.class));
    }
}
