package com.simon.basics.controller;

import com.github.pagehelper.PageInfo;
import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.Account;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.User;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.UserService;
import com.simon.basics.util.SaltEncryUtil;
import com.simon.basics.util.SmsUtil;
import com.simon.basics.util.UtilToString;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Objects;

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
    public ReturnParam<PageInfo<User>> list(User user, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "20") int pageSize, @RequestParam(defaultValue = "1") String type) {
        return ReturnParam.success(userService.findListByPage(user, pageNum, pageSize));
    }

    @PostMapping("detail")
    @ApiOperation(value = "学生/教师/管理员详情")
    public ReturnParam<User> detail(@RequestParam Long accountId) {
        return ReturnParam.success(userService.findByAccountId(accountId));
    }

    @PostMapping("sendCode")
    @ApiOperation(value = "添加用户（学生/教师）发送验证码")
    public ReturnParam sendCode(@RequestParam @Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$", message = "手机号码格式有误！") String phone) {
        String verification = jedisService.getString(phone);
        String code = UtilToString.getRandomString(6);
//        if (StringUtils.isEmpty(verification)) {
            jedisService.put(phone, code, 180);
            //发送验证码
            new Thread(() -> {
                if (!SmsUtil.sendSMS(phone,code)){
                    logger.error("发送验证码失败，tel={}",phone);
                }
            }).start();
//        } else {
////            logger.warn("重复发送验证码{}", phone);
////            return ReturnParam.verifing();
//        }
        return ReturnParam.success("获取成功!");
    }

    @PostMapping("add")
    @ApiOperation(value = "添加用户（学生/教师） type=1 学生 type=2教师")
    public ReturnParam<User> add(User user, @Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$", message = "手机号码格式有误！")
    @RequestParam String phone, @RequestParam(defaultValue = "1") String type, @RequestParam String name,
                           @RequestParam String username, @RequestParam String password,
                           @RequestParam String verification,
                           @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$|^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$", message = "身份证号码格式有误！") @RequestParam(required = false) String cardNum) {
        //验证码验证
        String code = jedisService.getString(phone);
        if (StringUtils.isEmpty(code)) {
            logger.warn("新增用户未获取验证码{}", phone);
            return ReturnParam.noVerification();
        }
        if (!code.equals(verification)) {
            logger.warn("新增用户{}验证码{}验证错误！", phone, verification);
            return ReturnParam.noVerification();
        }
        if (!Objects.isNull(user.getSchoolNumber())){
            if (!Objects.isNull(userService.findBySchoolNumber(user.getSchoolNumber()))){
                logger.warn("重复学号!{}", user.getSchoolNumber());
                return ReturnParam.repeatResource("重复学号!");
            }
        }
        User u = userService.findByUserName(user.getName());
        if (u != null) {
            logger.warn("新增用户{}已存在", user.getName());
            return ReturnParam.userExist();
        }
        user.setPassword(SaltEncryUtil.getMD5SaltString(username, password));
        return ReturnParam.success(userService.add(user));
    }

    @PostMapping("addManager")
    @ApiOperation(value = "新增管理员")
    public ReturnParam<Account> addManager(@RequestParam String username,String name,@RequestParam String verification,@Pattern(regexp = "^((1[358][0-9])|(14[57])|(17[0678])|(19[7]))\\d{8}$", message = "手机号码格式有误！") @RequestParam(required = false) String phone,@RequestParam String password, @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$|^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$", message = "身份证号码格式有误！") @RequestParam String cardNum) {
        //验证码验证
        String code = jedisService.getString(phone);
        if (StringUtils.isEmpty(code)) {
            logger.warn("新增管理员未获取验证码{}", phone);
            return ReturnParam.noVerification();
        }
        if (!code.equals(verification)) {
            logger.warn("新增管理员{}验证码{}!={}验证错误！", phone, verification,code);
            return ReturnParam.noVerification();
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(SaltEncryUtil.getMD5SaltString(username,password));
        account.setType(EnumCode.UserType.TYPE_MANAGER.getValue());
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(EnumCode.UserType.TYPE_MANAGER.getValue());
        user.setCardNum(cardNum);
        user.setName(name);
        user.setPhone(phone);
        return ReturnParam.success(userService.add(user));
    }

    @PostMapping("update")
    @ApiOperation(value = "修改用户（学生/教师）信息")
    public ReturnParam update(User user, @RequestParam Long accountId) {
        return ReturnParam.success(userService.update(user));
    }

    @PostMapping("updPassword")
    @ApiOperation(value = "修改密码(学生,老师只能修改自己的密码(accountId不用传,oldPassword必传);管理员可以修改任意用户密码,oldPassword不用传,accountId可选传[不传代表自己])")
    public ReturnParam updPassword(Long accountId,String oldPassword,@RequestParam String newPassword) throws MissingServletRequestParameterException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String salt =null;
        if (EnumCode.UserType.TYPE_SUPER.getValue().equals(user.getType())||EnumCode.UserType.TYPE_MANAGER.getValue().equals(user.getType())) {//管理员可以修改其他用户密码
            if (Objects.isNull(accountId)||user.getAccountId().longValue()==accountId){//修改自己
                salt = user.getUsername();
                accountId = user.getAccountId();
                if (Objects.isNull(oldPassword)){
                    throw new MissingServletRequestParameterException("oldPassword","String type [oldPassword] is misss");
                }
                String old = SaltEncryUtil.getMD5SaltString(salt, oldPassword);
                if (!old.equalsIgnoreCase(user.getPassword())){
                    return ReturnParam.incorrectCredentials();
                }
                SecurityUtils.getSubject().logout();
            }else{
                User other = userService.findByAccountId(accountId);
                salt = other.getUsername();
            }
        }else{  //修改自己密码
            salt = user.getUsername();
            if (Objects.isNull(oldPassword)){
                throw new MissingServletRequestParameterException("oldPassword","String type [oldPassword] is misss");
            }
            String old = SaltEncryUtil.getMD5SaltString(salt, oldPassword);
            if (!old.equalsIgnoreCase(user.getPassword())){
                return ReturnParam.incorrectCredentials();
            }
        }
        String password = SaltEncryUtil.getMD5SaltString(salt, newPassword);

        return ReturnParam.success(userService.updatePassword(accountId,password));
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除或恢复用户信息")
    public ReturnParam delete(@RequestParam Long accountId) {
        User user = userService.findByAccountId(accountId);
        if (user!=null&&userService.deleteUser(accountId,user.getDeleted()?false:true)>0){
            return ReturnParam.success();
        }else{
            return ReturnParam.noHandlerFound("删除失败,不存在用户"+accountId);
        }
    }
}
