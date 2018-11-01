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

    private static int appId = 1400090334;

    private static String appKey = "0440aaf8a840d845941d2be38313cfe8";

    private static int templateId = 124647;

    private static String smsSign = "麦芽闲置";

    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    public static boolean sendSMS(String phoneNumber,String msg) {
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
        System.out.println(sendSMS("15919161025","测试用的，"));
    }
}
