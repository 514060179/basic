package com.simon.basics.threadpool;

import com.google.common.util.concurrent.ListenableFuture;
import com.simon.basics.service.CourseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author fengtianying
 * @date 2018/11/21 10:43
 */
@Component
public class PayOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseOrderService courseOrderService;

    @Async("orderRebackExecutor")
    public void paySuccess(Long orderId, String orderNo, String payWay){
        int i = courseOrderService.paySuccess(orderId,orderNo,payWay);
        if (i<=0){
            logger.error("支付回调失败，orderId={},orderNo={}",orderId,orderNo);
        }
        logger.info("支付回调成功，orderId={},orderNo={}",orderId,orderNo);
    }

    @Async("orderPayExecutor")
    public void wechatPay(Long orderId,String wechatUrl){
        int i = courseOrderService.updateWechatUrl(orderId,wechatUrl);
        if (i<=0){
            logger.error("保存微信支付url失败，orderId={},wechatUrl={}",orderId,wechatUrl);
        }
        logger.info("保存微信支付url成功，orderId={},wechatUrl={}",orderId,wechatUrl);
    }
}
