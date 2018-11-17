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

    public final static String courseNotEnoughOrNotHad = "204";

    public final static String courseNotEnoughOrNotHadMsg = "没有购买该类型课程或课程剩余不足！";

    public final static String courseNotBeginning = "205";

    public final static String courseNotBeginningMsg = "老师未上课!";

    public final static String repeatResource = "206";

    public final static String repeatResourceMsg = "重复资源！";

    public final static String alreadyChoose  = "207";

    public final static String alreadyChooseMsg = "座位已经被选走!";

    public final static String courseActing  = "208";

    public final static String courseActingMsg = "课程已经发布!";

    public final static String courseActite  = "209";

    public final static String courseActiteMsg = "课程已开始或未发布!";

    public final static String courseNoAllowReback  = "210";

    public final static String courseNoAllowRebackMsg = "课程已结束或剩余课时为0,不允许退款！";

    public final static String courseStarting  = "211";

    public final static String courseStartingMsg = "课程还在上课中,请前往点击下课按钮!";

    public final static String orderHadPay  = "212";

    public final static String orderHadPayMsg = "订单已支付或已经取消!";

    public final static String missParam = "300";

    public final static String missParamMsg = "缺少参数！";

    public final static String paramiolationException = "301";

    public final static String paramiolationExceptionMsg = "参数异常！";

    public final static String illegalKeyIdException = "302";

    public final static String illegalKeyIdExceptionMsg = "xxx_id非法来源或类型【enum】有误！";

    public final static String sqlWritePrerequisiteException = "303";

    public final static String sqlWritePrerequisiteExceptionMsg = "条件不足，更新失败！";

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
