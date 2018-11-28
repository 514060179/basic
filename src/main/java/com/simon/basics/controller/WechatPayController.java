package com.simon.basics.controller;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.simon.basics.config.WechatConfig;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.JSONUtil;
import com.simon.basics.util.SnowflakeIdWorker;
import com.simon.basics.util.wx.WxApi;
import com.simon.basics.util.wx.WxApiClient;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author fengtianying
 * @date 2018/11/26 13:52
 */

@Controller
@RequestMapping("pay")
public class WechatPayController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String GetOpenIdURL = "http://ccjkjy.com/pay/jsapi";
    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private CourseOrderService courseOrderService;

    @GetMapping("jsapi")
    @ApiOperation("微信调起支付")
    public String getOpenId(ModelMap modelMap,HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
//        String redirectUrl4Vx = GetOpenIdURL + "?redirectUrl=" + redirectUrl;
//        String url = WxApi.getOAuthCodeUrl(appId, redirectUrl4Vx, "snsapi_base", state);
        String openId = httpServletRequest.getParameter("openId");
        String orderId = httpServletRequest.getParameter("orderId");
        if (!Objects.isNull(openId)){
            //统一下单
            //配置参数
            logger.warn("openId={}",openId);
            CourseOrder courseOrder = courseOrderService.findOneByOrderId(Long.parseLong(orderId));
            WxPayService wxPayService = new WxPayServiceImpl();
            WxPayConfig wxPayConfig = new WxPayConfig();

            wxPayConfig.setMchId(wechatConfig.getMchId());
            wxPayConfig.setAppId(wechatConfig.getAppId());
            wxPayConfig.setKeyPath(wechatConfig.getCertLocalPath());//证书位置
            wxPayConfig.setMchKey(wechatConfig.getMchKey());
            wxPayConfig.setNotifyUrl(wechatConfig.getNotifyUrl());//回调地址
            wxPayConfig.setTradeType("JSAPI");//交易类型

            wxPayService.setConfig(wxPayConfig);
            // 微信统一下单请求对象
            WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
            request.setDeviceInfo("web");
            request.setBody(courseOrder.getOrderName());
            request.setDetail(courseOrder.getOrderName());
            request.setAttach(courseOrder.getOrderId()+"");
            request.setOutTradeNo(""+new SnowflakeIdWorker().nextId());
            request.setFeeType("CNY");//币种类型
            request.setTotalFee(courseOrder.getOrderCost().multiply(new BigDecimal(100)).intValue());//总额,单位分
            request.setSpbillCreateIp("127.0.0.1");
            request.setTimeStart(null);
            request.setTimeExpire(null);
            request.setGoodsTag(null);
            request.setOpenid(openId);
            request.setNotifyUrl(wechatConfig.getNotifyUrl());
            request.setTradeType("JSAPI");
            request.setProductId(orderId+"");
            request.setLimitPay(null);
            try {
                WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(request);
                String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                String nonceStr = String.valueOf(System.currentTimeMillis());
                modelMap.put("appId", wechatConfig.getAppId());
                modelMap.put("timeStamp", timestamp);
                modelMap.put("nonceStr", nonceStr);
                modelMap.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                modelMap.put("signType", "MD5");
                modelMap.put("paySign", SignUtils.createSign(modelMap,null, wechatConfig.getMchKey(), new String[0]));
                logger.warn("wxPayUnifiedOrderResult={}",JSONUtil.objectToJson(wxPayUnifiedOrderResult));
                logger.warn("modelMap={}",JSONUtil.objectToJson(modelMap));
                return "pay";
            } catch (WxPayException e) {
                e.printStackTrace();
            }
        }
        String code = httpServletRequest.getParameter("code");
        String redirectUrl = httpServletRequest.getParameter("redirectUrl");
        if (Objects.isNull(code)){
            //获取code
            String redirectUrl4Vx = GetOpenIdURL + "?redirectUrl=" + GetOpenIdURL+"?orderId="+orderId;
            logger.warn("redirectUrl4Vx={}",redirectUrl4Vx);
            String url = WxApi.getOAuthCodeUrl(wechatConfig.getAppId(), redirectUrl4Vx, "snsapi_base", "");
            logger.warn("获取code返回跳转url={}",url);
            response.sendRedirect(url);
            return null;
        }else {
            openId = WxApiClient.getOAuthOpenId(wechatConfig.getAppId(), wechatConfig.getMchKey(), code);
            logger.warn("获取openId={}",openId);
            if (redirectUrl.indexOf("?") > 0) {
                redirectUrl += "&openId=" + openId;
            } else {
                redirectUrl += "?openId=" + openId;
            }
            logger.warn("获取openId跳转redirectUrl={}",redirectUrl);
            response.sendRedirect(redirectUrl);//跳转下单
            return null;
        }
    }
}
