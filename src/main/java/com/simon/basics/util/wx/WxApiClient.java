package com.simon.basics.util.wx;

/**
 * @author fengtianying
 * @date 2018/11/26 11:41
 */
public class WxApiClient {

    //获取openId
    public static String getOAuthOpenId(String appid, String secret, String code){
        OAuthAccessToken token = WxApi.getOAuthAccessToken(appid, secret, code);
        if(token != null){
            if(token.getErrcode() != null){//获取失败
                System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
            }else{
                return token.getOpenid();
            }
        }
        return null;
    }


}
