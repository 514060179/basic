package com.simon.basics.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengtianying
 * @date 2018/10/31 16:30
 */
@Configuration
public class WechatConfig {

//    @Bean
//    public WxPayConfig wechatConfig(){
//        WxPayConfig wxPayConfig = new WxPayConfig();
//        wxPayConfig.setAppId(appId);
//        wxPayConfig.setMchId(mchId);
//        wxPayConfig.setMchKey(mchKey);
//        return wxPayConfig;
//    }
    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.mchId}")
    private String mchId;

    @Value("${wechat.mchKey}")
    private String mchKey;

    @Value("${wechat.userKey}")
    private String userKey;

    //证书路径
    @Value("${wechat.certLocalPath}")
    private String certLocalPath;

    //回调url
    @Value("${wechat.notifyUrl}")
    private String notifyUrl;

    public String getCertLocalPath() {
        return certLocalPath;
    }

    public void setCertLocalPath(String certLocalPath) {
        this.certLocalPath = certLocalPath;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
