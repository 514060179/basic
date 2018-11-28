package com.simon.basics.util.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fengtianying
 * @date 2018/11/26 11:41
 */
public class WxApiClient {

    private static Logger logger = LoggerFactory.getLogger(WxApiClient.class);
    //获取openId
    public static String getOAuthOpenId(String appid, String secret, String code){
        OAuthAccessToken token = WxApi.getOAuthAccessToken(appid, secret, code);
        logger.warn("获取openId返回token={}",token);
        if(token != null){
            if(token.getErrcode() != null){//获取失败
                System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
            }else{
                return token.getOpenid();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getOAuthOpenId("wx254a867fd60891bd","345d84236a2b381f418ce887c186bf5f","001eT84G1PLIo00KMH3G1rbO3G1eT84P"));
    }

}
