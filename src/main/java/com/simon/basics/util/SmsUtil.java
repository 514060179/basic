package com.simon.basics.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author fengtianying
 * @date 2018/11/1 8:47
 */
public class SmsUtil {

    private static int appId = 1400165245;

    private static String appKey = "35d67f2089b51cf88f847ad510deaa4d";

    private static int templateId1 = 236241;

    private static int templateId2 = 236292;

    private static String smsSign = "金珂教育培训学校";

    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public static boolean sendCodeMsg(String phoneNumber,String msg){
        return sendSMS(phoneNumber,msg,templateId1);
    }

    public static boolean sendCourseMsg(String phoneNumber,String msg){
        return sendSMS(phoneNumber,msg,templateId2);
    }
    private static boolean sendSMS(String phoneNumber,String msg,int templateId) {
        String method = "【发送验证码】";
        try {
            //数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            String[] params = {msg};
            SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            if (result.result==0){
                return true;
            }
            logger.error("{}发送验证码失败tel={},response={}",method,phoneNumber,JSONUtil.objectToJson(result));
        } catch (HTTPException e) {
            // HTTP响应码错误
            logger.error(method+"异常",e);
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            logger.error("{},异常",method);
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            logger.error(method+"异常",e);
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(sendCodeMsg("15919161025","123456"));
        System.out.println(sendCourseMsg("15919161025","simonfeng"));
    }
}
