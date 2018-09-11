package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.model.User;
import com.simon.basics.service.UserService;
import com.simon.basics.util.SaltEncryUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

/**
 * 用户登录、退出、注册
 * @author fengtianying
 * @date 2018/9/4 13:22
 */
@RestController
@Validated
@RequestMapping("/user")
@Api(tags = "user",description = "用户登录、退出、注册(学生)")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private JedisService jedisService;
    @PostMapping("/login")
    public ReturnParam login(@RequestParam String userName, @RequestParam String password, @RequestParam(required = false)@Email String email, @RequestParam(required = false)@Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$",message = "手机号码格式有误！") String phone, @RequestParam(required = false)@Max(value = 60,message = "得分必须小于60")int socre){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(userName, SaltEncryUtil.getMD5SaltString(userName,password));
        if (!subject.isAuthenticated()) {
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
        return ReturnParam.success(SecurityUtils.getSubject().getPrincipal());
    }

    @GetMapping("/logout")
    public ReturnParam logout(){
        SecurityUtils.getSubject().logout();
        return ReturnParam.success();
    }

    @GetMapping("/list")
    public ReturnParam list(){
        return ReturnParam.success(userService.findListByPage(new User(),0,1));
    }

    @GetMapping("/register")
    public ReturnParam register(){

        return ReturnParam.success(userService.findListByPage(new User(),0,1));
    }


}
