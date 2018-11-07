package com.simon.basics.controller;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.RefundOrder;
import com.simon.basics.model.RefundOrderWithUser;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.RefundOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author fengtianying
 * @date 2018/11/1 15:11
 */
@RestController
@RequestMapping("api/refundOrder")
@Api(tags = "refundOrder", description = "退费订单")
public class RefundOrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RefundOrderService refundOrderService;


    @ApiOperation("获取退费列表")
    @PostMapping("list")
    public ReturnParam<PageInfo<RefundOrderWithUser>> list(RefundOrder refundOrder, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(refundOrderService.getListByPage(refundOrder,pageNum,pageSize));
    }

    @ApiOperation("确认退款")
    @PostMapping("confirm")
    public ReturnParam confirm(@RequestParam Long refundId, BigDecimal actualAmount){
        RefundOrder refundOrder = refundOrderService.findOneByKey(refundId);
        if (Objects.isNull(refundOrder)){
            logger.warn("找不到资源：refundId=" + refundId);
            return ReturnParam.noHandlerFound("找不到资源：refundId=" + refundId);
        }
        refundOrder.setActualAmount(actualAmount);
        return ReturnParam.success(refundOrderService.refunded(refundOrder));
    }
    @ApiOperation("根据订单id退款详情")
    @PostMapping("detail")
    public ReturnParam<RefundOrder> detail(@RequestParam Long orderId){
        return ReturnParam.success(refundOrderService.findOneByOrderId(orderId));
    }


}
