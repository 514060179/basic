package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.User;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.UserService;
import com.simon.basics.util.UtilToString;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

/**
 * @Author simon.feng
 * @Date Created in 21:12 2018/9/18/018
 * @Modificd
 */
@RestController
@RequestMapping("api/user")
@Validated
@Api(tags = "user", description = "用户管理。学生、教师、管理员列表、修改、删除、新增")
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private JedisService jedisService;


    @PostMapping("list")
    @ApiOperation(value = "学生/教师/管理员列表。type：0管理员1学生2教师")
    public ReturnParam list(User user, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize,@RequestParam(defaultValue = "1") String type) {
        return ReturnParam.success(userService.findListByPage(user, pageNum, pageSize));
    }

    @PostMapping("sendCode")
    @ApiOperation(value = "添加用户（学生/教师）发送验证码")
    public ReturnParam sendCode(@RequestParam @Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$", message = "手机号码格式有误！") String phone) {
        String verification = jedisService.getString(phone);
        String code = UtilToString.getRandomString(6);
        if (StringUtils.isEmpty(verification)) {
            jedisService.put(phone, code, 60 * 10);
            //发送验证码
        } else {
            logger.warn("重复发送验证码{}", phone);
            return ReturnParam.verifing();
        }
        return ReturnParam.success(code);
    }

    @PostMapping("add")
    @ApiOperation(value = "添加用户（学生/教师） type=1 学生 type=2教师")
    public ReturnParam add(User user, @Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$", message = "手机号码格式有误！")
                                        @RequestParam String phone, @RequestParam(defaultValue = "1") String type,@RequestParam String name,
                                        @RequestParam Integer age, @RequestParam String username, @RequestParam String password,
                                        @RequestParam String verification,@RequestParam String cardNum) {
        //验证码验证
        String code = jedisService.getString(phone);
        if (StringUtils.isEmpty(code)) {
            logger.warn("新增用户未获取验证码{}", phone);
            return ReturnParam.noVerification();
        }
        if (code.equals(verification)) {
            logger.warn("新增用户{}验证码{}验证错误！", phone, verification);
            return ReturnParam.noVerification();
        }
        User u = userService.findByUserName(user.getName());
        if (u != null) {
            logger.warn("新增用户{}已存在", user.getName());
            ReturnParam.userExist();
        }
        return ReturnParam.success(userService.add(user));
    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户（学生/教师）信息")
    public ReturnParam update(User user, @RequestParam Long accountId) {
        return ReturnParam.success(userService.update(user));
    }
}
