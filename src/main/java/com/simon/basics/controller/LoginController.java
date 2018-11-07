package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.model.User;
import com.simon.basics.service.UserService;
import com.simon.basics.util.SaltEncryUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/pass")
@Api(tags = "pass",description = "用户登录、退出")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private JedisService jedisService;
    @PostMapping("/toLogin")
    public ReturnParam<User> login(@RequestParam String userName, @RequestParam String password){

        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(userName, SaltEncryUtil.getMD5SaltString(userName,password));
        SecurityUtils.getSubject().logout();
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

//    @GetMapping("/list")
//    @ApiOperation(value = "获取列表(学生或教师)")
//    public ReturnParam list(User user,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
//        return ReturnParam.success(userService.findListByPage(new User(),pageNum,pageSize));
//    }

    @GetMapping("/register")
    @ApiOperation(value = "注册（暂不用）")
    public ReturnParam register(){

        return ReturnParam.success(userService.findListByPage(new User(),0,1));
    }


}
