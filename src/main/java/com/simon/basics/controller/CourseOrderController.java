package com.simon.basics.controller;

import com.simon.basics.componet.exception.PayExcetion;
import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.qianying.pay.QianyingPayService;
import com.simon.basics.qianying.pay.tools.MD5;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.JSONUtil;
import com.simon.basics.util.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private QianyingPayService qianyingPayService;

    @PostMapping("list")
    @ApiOperation("查询订单列表")
    public ReturnParam list(CourseOrder courseOrder, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return ReturnParam.success(courseOrderService.getListByPage(courseOrder,pageNum,pageSize));
    }

    @PostMapping("create")
    @ApiOperation("学生创建订单")
    public ReturnParam create(@RequestParam Long courseId) {
        ClassCourse classCourse = classCourseService.findOne(courseId,null,null);
        if(Objects.isNull(classCourse)){
            logger.warn("找不到资源：courseId=" + courseId);
            return ReturnParam.noHandlerFound("找不到资源：courseId=" + courseId);
        }
        if(!EnumCode.CourseStatus.COURSE_ACTION.getValue().equals(classCourse.getCourseStatus())){
            logger.warn("课程已开始或未发布：courseId=" + courseId);
            return ReturnParam.courseActite();
        }
        CourseOrder courseOrder = courseOrderService.findOneByCourseId(courseId);
        if (courseOrder!=null){
            logger.warn("重复下单：");
            return ReturnParam.repeatOrder();
        }
        return ReturnParam.success(courseOrderService.create(classCourse));
    }

    @PostMapping("orderId")
    @ApiOperation("模拟支付成功")
    public ReturnParam paySuccess(@RequestParam Long orderId){
        return ReturnParam.success(courseOrderService.paySuccess(orderId,new SnowflakeIdWorker().nextId()+"","101"));
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
