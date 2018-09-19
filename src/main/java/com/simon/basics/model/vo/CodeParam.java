package com.simon.basics.model.vo;

/**
 * @author fengtianying
 * @date 2018/9/4 13:29
 */
public class CodeParam {

    public final static String success = "200";

    public final static String successMsg = "操作成功！";

    public final static String courseEnding = "201";

    public final static String courseEndingMsg = "课程已结束！";

    public final static String lastCourseNoEnding = "202";

    public final static String lastCourseNoEndingMsg = "上节课程未结束！";

    public final static String repeatOrder = "203";

    public final static String repeatOrderMsg = "重复创建订单！";

    public final static String missParam = "300";

    public final static String missParamMsg = "缺少参数！";

    public final static String paramiolationException = "301";

    public final static String paramiolationExceptionMsg = "参数异常！";

    public final static String noHandlerFound = "404";

    public final static String noHandlerFoundMsg = "找不到资源/请求路径有误";

    public final static String sessionOverdue = "100";

    public final static String sessionOverdueMsg = "未登陆/登陆过期";

    public final static String incorrectCredentials = "101";

    public final static String incorrectCredentialsMsg = "密码错误！";

    public final static String userExist = "102";

    public final static String userExistMsg = "用户已注册！！";

    public final static String verifing = "103";

    public final static String verifingMsg = "10分钟内重复发送验证码！";

    public final static String noVerification = "104";

    public final static String noVerificationMsg = "请获取验证码！！";

    public final static String systemError = "999";

    public final static String systemErrorMsg = "操作异常！";

    @Override
    public String toString() {
        return "success='" + success + '\'' +successMsg+
               ", error='" + systemError + '\'' +systemErrorMsg+
                '}';
    }
}
