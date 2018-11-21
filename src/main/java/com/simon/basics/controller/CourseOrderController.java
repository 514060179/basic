package com.simon.basics.controller;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.github.pagehelper.PageInfo;
import com.simon.basics.componet.exception.PayExcetion;
import com.simon.basics.config.WechatConfig;
import com.simon.basics.model.*;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.qianying.pay.QianyingPayService;
import com.simon.basics.qianying.pay.tools.MD5;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.service.CourseRosterService;
import com.simon.basics.threadpool.PayOrderService;
import com.simon.basics.util.JSONUtil;
import com.simon.basics.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

/**
 * @Author simon.feng
 * @Date Created in 20:45 2018/9/18/018
 * @Modificd
 */
@RestController
@RequestMapping("api/courseOrder")
@Api(tags = "courseOrder", description = "课程订单")
public class CourseOrderController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseOrderService courseOrderService;
    @Autowired
    private ClassCourseService classCourseService;
    @Autowired
    private CourseRosterService courseRosterService;
    @Autowired
    private QianyingPayService qianyingPayService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private WechatConfig wechatConfig;

    @PostMapping("list")
    @ApiOperation("查询订单列表")
    public ReturnParam<PageInfo<CourseOrder>> list(CourseOrder courseOrder, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(courseOrderService.getListByPage(courseOrder,pageNum,pageSize));
    }

    @PostMapping("detail")
    @ApiOperation("订单详情")
    public ReturnParam<CourseOrder> detail(Long orderId) {
        return ReturnParam.success(courseOrderService.findOneByOrderId(orderId));
    }

    @PostMapping("create")
    @ApiOperation("学生创建订单")
    public ReturnParam<CourseOrder> create(@RequestParam Long courseId) {
        ClassCourse classCourse = classCourseService.findOne(courseId,null,null);
        if(Objects.isNull(classCourse)){
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        if(EnumCode.CourseStatus.COURSE_ACTION.getValue().equals(classCourse.getCourseStatus())||EnumCode.CourseStatus.COURSE_IN.getValue().equals(classCourse.getCourseStatus())){
            CourseOrder courseOrder = courseOrderService.findOneByCourseId(courseId,null);
            if (courseOrder!=null){
                logger.warn("重复下单：");
                return ReturnParam.repeatOrder();
            }
            return ReturnParam.success(courseOrderService.create(classCourse));
        }else{
            logger.warn("课程已开始或未发布：courseId=" + courseId);
            return ReturnParam.courseActite();
        }
    }

    @PostMapping("orderId")
    @ApiOperation("模拟支付成功")
    public ReturnParam paySuccess(@RequestParam Long orderId){
        return ReturnParam.success(courseOrderService.paySuccess(orderId,new SnowflakeIdWorker().nextId()+"","101"));
    }

//    @GetMapping("pay")
//    @ApiOperation("调起支付. ps:使用浏览器调用")
//    public void pay(HttpServletResponse response, @RequestParam Long orderId,
//                    @ApiParam(value = "支付类型:alipay,wechat",name = "type" ,required = true)@RequestParam String type,
//                    @ApiParam(value = "成功跳转url",name = "gotrue" ,required = true)@RequestParam String gotrue,@ApiParam(value = "失败跳转url",name = "gofalse" ,required = true)@RequestParam String gofalse) throws NoHandlerFoundException {
//        CourseOrder courseOrder = courseOrderService.findOneByOrderId(orderId);
//        if (courseOrder==null){
//            throw new NoHandlerFoundException("资源不存在!","资源不存在!",null);
//        }
//        if (!EnumCode.OrderStatus.ORDER_NOPAY.getValue().equals(courseOrder.getOrderStatus())){
//            PrintWriter printWriter = null;
//            try {
//                response.setCharacterEncoding("UTF-8");
//                response.setHeader("content-type", "text/html;charset=UTF-8");
//                printWriter = new PrintWriter(response.getOutputStream());
//                printWriter.print("<h1>订单已支付或已经取消</h1>");
//                printWriter.flush();
//                printWriter.close();
//                return;
//            } catch (IOException e) {
//                logger.error("获取流异常！",e);
//                e.printStackTrace();
//            }
//        }
//        if ("alipay".equals(type)){
//            type = "101";
//        }else{
//            type = "102";
//        }
//        String qianyingPayNo = new SnowflakeIdWorker().nextId()+"";//支付订单
//        qianyingPayService.submitOrder(response,courseOrder.getOrderCost().intValue()+"",qianyingPayNo,courseOrder.getOrderId()+"",type,gotrue,gofalse);
//    }


    @GetMapping("wechatPay")
    @ApiOperation("微信调起支付")
    public ReturnParam<String> wechat(@RequestParam Long orderId) throws NoHandlerFoundException {

        CourseOrder courseOrder = courseOrderService.findOneByOrderId(orderId);
        if (courseOrder==null){
            throw new NoHandlerFoundException("资源不存在!","资源不存在!",null);
        }
        if (!EnumCode.OrderStatus.ORDER_NOPAY.getValue().equals(courseOrder.getOrderStatus())){
            logger.warn("订单处于非未支付状态:{}",courseOrder.getOrderStatus());
            return ReturnParam.orderHadPay();
        }
        //配置参数
        WxPayService wxPayService = new WxPayServiceImpl();
        WxPayConfig wxPayConfig = new WxPayConfig();

        wxPayConfig.setMchId(wechatConfig.getMchId());
        wxPayConfig.setAppId(wechatConfig.getAppId());
        wxPayConfig.setKeyPath(wechatConfig.getCertLocalPath());//证书位置
        wxPayConfig.setMchKey(wechatConfig.getMchKey());
        wxPayConfig.setNotifyUrl(wechatConfig.getNotifyUrl());//回调地址
        wxPayConfig.setTradeType("NATIVE");//交易类型

        wxPayService.setConfig(wxPayConfig);
        // 微信统一下单请求对象
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setDeviceInfo("web");
        request.setBody("测试固定金额0.01:"+courseOrder.getOrderName());
        request.setDetail(courseOrder.getOrderName());
        request.setAttach(courseOrder.getOrderId()+"");
        request.setOutTradeNo(""+new SnowflakeIdWorker().nextId());
        request.setFeeType("CNY");//币种类型
        request.setTotalFee(1);//总额,单位分
        request.setSpbillCreateIp("127.0.0.1");
        request.setTimeStart(null);
        request.setTimeExpire(null);
        request.setGoodsTag(null);
        request.setNotifyUrl(wechatConfig.getNotifyUrl());
        request.setTradeType("NATIVE");
        request.setProductId(orderId+"");
        request.setLimitPay(null);
        try {
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(request);
//            wxPayUnifiedOrderResult.get
            payOrderService.wechatPay(orderId,wxPayUnifiedOrderResult.getCodeURL());
            return ReturnParam.success("succeess",wxPayUnifiedOrderResult.getCodeURL());
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("applyback")
    @ApiOperation("学生申请退款")
    public ReturnParam<RefundOrder> applyback(@RequestParam Long orderId) throws NoHandlerFoundException {
        //1 查询订单是否存在
        CourseOrder courseOrder = courseOrderService.findOneByOrderId(orderId);
        if (Objects.isNull(courseOrder)){
            throw new NoHandlerFoundException("资源不存在!","资源不存在!",null);
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClassCourseWithBLOBs classCourse = (ClassCourseWithBLOBs)classCourseService.findOne(courseOrder.getCourseId(),user.getAccountId(),null);
        if (!classCourse.getBought()){
            return ReturnParam.noHandlerFound("该课程未购买!");
        }
        //开始以及进行中的才可以退款
        if(EnumCode.CourseStatus.COURSE_ACTION.getValue().equals(classCourse.getCourseStatus()) || EnumCode.CourseStatus.COURSE_IN.getValue().equals(classCourse.getCourseStatus())){
            //获取剩余课程
            CourseRoster courseRoster = courseRosterService.findByCourseIdAndAccountId(classCourse.getCourseId(),user.getAccountId());
            if (courseRoster!=null&&courseRoster.getRosterCourseCountRest()<=0){
                return ReturnParam.courseNoAllowReback();
            }
            return ReturnParam.success(courseOrderService.applyback(classCourse,courseOrder,courseRoster));
        }else{
            return ReturnParam.courseNoAllowReback();
        }
        //2 删除课程名单
        //3 更新订单状态
        //4 添加退款记录

    }

    @GetMapping("callback")
    @ApiOperation(value = "支付回调",hidden = true)
    public void callback(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        try{
            String requestParam = JSONUtil.objectToJson(parameterMap);
            logger.warn("千应支付回调:"+ requestParam);
            //oid=39138668413190144,status=1,m1=1.00,mInt=1,sign=F60E0F08C2A1984625EADFCD765F29E7,
            // oidMy=10120181015-142548-850710,oidPay=2018101522001491290520708919,
            // time=2018/10/15 14:26:59,token=132456testalipay2,msg=success,m=1.00,uuid=0,tid=101,
            String oid = request.getParameter("oid");//支付订单编号
            String status = request.getParameter("status");//支付状态
            String m1 = request.getParameter("m1");//支付金额
            String mInt = request.getParameter("mInt");
            String sign = request.getParameter("sign");//签名
            String oidMy = request.getParameter("oidMy");//千应支付订单号
            String oidPay = request.getParameter("oidPay");//支付宝/微信支付订单号
            String time = request.getParameter("time");//支付时间
            String token = request.getParameter("token");//课程订单id
            String msg = request.getParameter("msg");//信息
            String m = request.getParameter("m");//支付金额
            String uuid = request.getParameter("uuid");//
            String tid = request.getParameter("tid");//支付方式
            //验证签名
            if (!sign.equalsIgnoreCase(MD5.convert("oid="+oid+"&status="+status+"&m="+m+qianyingPayService.getQIANYING_KEY()))){
                logger.error("千应回调签名错误！"+sign);
                throw new PayExcetion("签名验证失败!;"+requestParam);
            }
            int i = courseOrderService.paySuccess(Long.parseLong(token),oid,tid);
            if (i<=0){
                logger.error("千应回调更新订单异常！"+sign);
                throw new PayExcetion("更新订单异常!;"+parameterMap);
            }
            logger.info("支付回调业务处理成功！");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new PayExcetion("回调运行时异常!;"+JSONUtil.objectToJson(parameterMap),e);
        }
    }
}
