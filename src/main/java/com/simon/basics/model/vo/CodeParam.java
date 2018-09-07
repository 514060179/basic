package com.simon.basics.model.vo;

/**
 * @author fengtianying
 * @date 2018/9/4 13:29
 */
public class CodeParam {

    public final static String success = "200";

    public final static String successMsg = "操作成功！";

    public final static String missParam = "300";

    public final static String missParamMsg = "缺少参数！";

    public final static String noHandlerFound = "404";

    public final static String noHandlerFoundMsg = "找不到资源/请求路径有误";

    public final static String sessionOverdue = "100";

    public final static String sessionOverdueMsg = "未登陆/登陆过期";

    public final static String incorrectCredentials = "101";

    public final static String incorrectCredentialsMsg = "密码错误！";

    public final static String systemError = "999";

    public final static String systemErrorMsg = "操作异常！";

    @Override
    public String toString() {
        return "success='" + success + '\'' +successMsg+
               ", error='" + systemError + '\'' +systemErrorMsg+
                '}';
    }
}
