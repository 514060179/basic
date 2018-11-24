package com.simon.basics.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.util.SignUtils;
import com.simon.basics.config.WechatConfig;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.OperateLog;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.threadpool.OperateLogPool;
import com.simon.basics.threadpool.PayOrderService;
import com.simon.basics.util.JSONUtil;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author fengtianying
 * @date 2018/10/31 15:51
 */
@RestController
@RequestMapping("common")
@Api(hidden = true)
public class CallbackController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private OperateLogPool operateLogPool;

//    @RequestMapping("alipay/callback")
//    public void alipay(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
////            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
//            params.put(name, valueStr);
//        }
//        boolean isSign = AlipaySignature.rsaCheckV1(params,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgN84KEPWIS0FTJrjgyOeY4uiK6+Qrw2CHcCT3pSP/MFoI4LLvQvceslzxy/Athp78dkR5B4mJUp8Gj80iEN+d6baIwvvCv9giQ8O1G4qleZ4y2C+lHvUY1nsXdnifVvrzVeIv55oIy9k4IrNHAazMaPZPhtxUEZDOtJduDTax+rQLXhUWE5jRtTe+c4vHIuHB6CVGiTEmh8Bwdhs13aNcJzETZJLrRpHlLIEMEaYHCp1GwdSrIyxwa0KmIenyOVwKTweb4cYmbmsjiheZGDIfaCsRVjyzxNZTHJpgt/ZN4mdUcpH6oqw2T/bziibLZFjquiAm8up79L3lYxnNeh+IQIDAQAB","utf-8","RSA2");
//        String outTradeNo = params.get("out_trade_no");
//        String result = "fail";
//        if (isSign){
//            CourseOrder courseOrder = courseOrderService.findOneByOrderNo(outTradeNo);
//            if (!Objects.isNull(courseOrder)){
//                if (courseOrderService.paySuccess(1L,"","")>0){
//                    result = "success";
//                }
//            }
//        }
//        response.setContentType("text/html");
//        PrintWriter pw = null;
//        try {
//            pw = response.getWriter();
//            pw.print(result);
//        } catch (IOException e) {
//            logger.error("alipay支付回调异常",e);
//            e.printStackTrace();
//        }finally {
//            if (pw != null){
//                pw.close();
//            }
//        }
//    }
//
//    @RequestMapping("alipay/wechat")
//    public String wechat(HttpServletRequest request, HttpServletResponse response) throws IOException, WxPayException {
//
//        String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
//        WxPayService wxPayService = new WxPayServiceImpl();
//        WxPayOrderNotifyResult params = WxPayOrderNotifyResult.fromXML(xmlResult);
//        //校验结果是否成功
//        if (!"SUCCESS".equalsIgnoreCase(params.getResultCode())) {
//            logger.error("returnCode={},resultCode={},errCode={},errCodeDes={}", params.getReturnCode(), params.getResultCode(), params.getErrCode(), params.getErrCodeDes());
//            return WxPayNotifyResponse.fail("支付失败!");
//        }
//        wxPayService.setConfig(this.wxPayConfig);
//        wxPayService.parseOrderNotifyResult(xmlResult);
//
//        if (!SignUtils.checkSign(params,null,"")){
//            return WxPayNotifyResponse.fail("签名验证失败!");
//        }
//        String orderNo = params.getOutTradeNo();
//        CourseOrder courseOrder = courseOrderService.findOneByOrderNo(orderNo);
//        if (!Objects.isNull(courseOrder)){
//            if (courseOrderService.paySuccess(1L,"","")>0){
//                return WxPayNotifyResponse.success("回调成功！");
//            }
//        }
//        return WxPayNotifyResponse.fail("回调失败!");
//    }

    @RequestMapping("wechat/callback")
    public String wechat(HttpServletRequest request, HttpServletResponse response) throws IOException, WxPayException {
        OperateLog operateLog = new OperateLog();

        // 获取请求地址
        String requestUrl = request.getRequestURI();
        operateLog.setReqeustUrl(requestUrl);
        // 请求所在类
        String className = this.getClass().getName();
        operateLog.setClassName(className);
        String method = "wechat";
        operateLog.setMethod(method);
        logger.info("======微信支付回调======");
        String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
        operateLog.setDescribe(xmlResult);
        logger.warn("响应参数：{}",xmlResult);
        WxPayOrderNotifyResult params = WxPayOrderNotifyResult.fromXML(xmlResult);
        operateLog.setParam(JSONUtil.objectToJson(params));
        operateLog.setStatus("fail");
        //校验结果是否成功
        if (!"SUCCESS".equalsIgnoreCase(params.getResultCode())) {
            operateLog.setRemark("支付失败！");
            operateLogPool.addLog(operateLog);
            logger.error("returnCode={},resultCode={},errCode={},errCodeDes={}", params.getReturnCode(), params.getResultCode(), params.getErrCode(), params.getErrCodeDes());
            return WxPayNotifyResponse.fail("支付失败!");
        }

        logger.warn("微信支付回调参数：{}",JSONUtil.objectToJson(params));
        if (!SignUtils.checkSign(params,null,wechatConfig.getMchKey())){
            operateLog.setRemark("签名验证失败！");
            operateLogPool.addLog(operateLog);
            return WxPayNotifyResponse.fail("签名验证失败!");
        }

        Long orderId = Long.parseLong(params.getAttach());
        String orderNo = params.getOutTradeNo();
        CourseOrder courseOrder = courseOrderService.findOneByOrderId(orderId);
        if (!Objects.isNull(courseOrder)){
//            if (courseOrderService.paySuccess(orderId,orderNo,"")>0){
//                logger.info("======微信支付回调成功======");
//                return WxPayNotifyResponse.success("回调成功！");
//            }
            if (params.getTotalFee()!=courseOrder.getOrderCost().multiply(new BigDecimal(100)).intValue()){
                String remark = String.format("支付金额与订单金额不一致,支付金额={"+params.getTotalFee()+"},订单金额={"+courseOrder.getOrderCost().intValue()*100+"}");
                operateLog.setRemark(remark);
                operateLog.setStatus("FAIL");
                logger.error("支付金额与订单金额不一致,支付金额={},订单金额={}",params.getTotalFee(),courseOrder.getOrderCost().intValue()*100);
                operateLogPool.addLog(operateLog);
                return WxPayNotifyResponse.fail("处理失败!");
            }
            payOrderService.paySuccess(orderId,orderNo,"");
            operateLog.setStatus("SUCCESS");
            operateLogPool.addLog(operateLog);
            return WxPayNotifyResponse.success("回调成功！");
        }
        operateLog.setRemark("微信回调失败！");
        operateLogPool.addLog(operateLog);
        logger.error("======微信支付回调失败======");
        return WxPayNotifyResponse.fail("回调失败!");
    }

}
