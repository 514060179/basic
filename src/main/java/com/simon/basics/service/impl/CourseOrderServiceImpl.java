package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.CourseOrderMapper;
import com.simon.basics.dao.RefundOrderMapper;
import com.simon.basics.model.*;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.service.CourseRosterService;
import com.simon.basics.util.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author simon.feng
 * @Date Created in 21:02 2018/9/18/018
 * @Modificd
 */
@Service
public class CourseOrderServiceImpl implements CourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;
    @Autowired
    private CourseRosterService courseRosterMapper;
    @Autowired
    private RefundOrderMapper refundOrderMapper;

    @Override
    public PageInfo<CourseOrder> getListByPage(CourseOrderWithBLOBs courseOrder, int pageNum, int pageSize) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            courseOrder.setAccountId(user.getAccountId());
        }else if(EnumCode.UserType.TYPE_TEACHER.getValue().equals(user.getType())){//教师无法查询
            return null;
        }else{
            courseOrder.setAccountId(null);
        }
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(courseOrderMapper.findListByCondition(courseOrder));
    }

    @Override
    public CourseOrder create(ClassCourse classCourse) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setAccountId(user.getAccountId());
        courseOrder.setCourseId(classCourse.getCourseId());
        courseOrder.setOrderId(new SnowflakeIdWorker().nextId());
//        courseOrder.setOrderCost(classCourse.getCourseCost());
        BigDecimal adv = classCourse.getCourseCost().divide(new BigDecimal(classCourse.getCourseTotal()),2, BigDecimal.ROUND_HALF_UP);
        courseOrder.setOrderCost(adv.multiply(new BigDecimal(classCourse.getCourseTotal()-classCourse.getCourseCurrent())));
        courseOrder.setCourseTotal(classCourse.getCourseTotal()-classCourse.getCourseCurrent());
        courseOrder.setOrderName(classCourse.getCourseName());
        int i = courseOrderMapper.insertSelective(courseOrder);
        if(i>0){
            return courseOrder;
        }
        return null;
    }

    @Override
    public CourseOrder findOneByCourseId(Long courseId,Long accountId) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        CourseOrderWithBLOBs courseOrder = new CourseOrderWithBLOBs();
        courseOrder.setCourseId(courseId);
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            courseOrder.setAccountId(user.getAccountId());
        }else{//管理员 查询所有
            courseOrder.setAccountId(accountId);
        }
        List<CourseOrder> findCourseOrderList = courseOrderMapper.findListByCondition(courseOrder);
        if (findCourseOrderList==null||findCourseOrderList.size()==0){
            return null;
        }
        return findCourseOrderList.get(0);
    }

    @Override
    public CourseOrder findOneByOrderId(Long orderId) {
        return courseOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public CourseOrder findOneByOrderNo(String orderNo) {
        return courseOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public int paySuccess(Long orderId, String orderNo, String payWay) {
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setOrderId(orderId);
        courseOrder.setOrderStatus(EnumCode.OrderStatus.ORDER_PAID.getValue());
        courseOrder.setOrderNo(orderNo);
        courseOrder.setOrderPayTime(new Date());
        if ("101".equals(payWay)){
            courseOrder.setOrderPayWay("alipay");
        }else{
            courseOrder.setOrderPayWay("wechat");
        }
        return courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
    }

    @Transactional
    @Override
    public RefundOrder applyback(ClassCourse classCourse, CourseOrder courseOrder, CourseRoster courseRoster) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setRefundId(new SnowflakeIdWorker().nextId());
        refundOrder.setAccountId(courseOrder.getAccountId());
        refundOrder.setCourseId(classCourse.getCourseId());
        refundOrder.setOrderId(courseOrder.getOrderId());
        //剩余课程
        int rest = 0;
        //1 删除课程名单
        if (Objects.isNull(courseRoster)){
            rest = classCourse.getCourseTotal()-classCourse.getCourseCurrent();
        }else{
            courseRosterMapper.delByCourseIdAndAccountId(courseRoster.getCourseId(),courseRoster.getAccountId());
            rest = courseRoster.getRosterCourseCountRest();
        }
        //2 更新订单状态
        CourseOrder update = new CourseOrder();
        update.setOrderStatus(EnumCode.OrderStatus.ORDER_APPLY_REBACK.getValue());
        update.setOrderId(courseOrder.getOrderId());
        courseOrderMapper.updateByPrimaryKeySelective(update);//refundOrderMapper
        //3 添加退款记录
        int courseTotal = courseOrder.getCourseTotal();//购买时的总课时
        BigDecimal orderCost = courseOrder.getOrderCost();//购买时的总费用
        BigDecimal amount = orderCost.divide(new BigDecimal(courseTotal),2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(rest));//退款金额
        refundOrder.setAmount(amount);
        refundOrder.setCourseAmount(orderCost);//课程金额
        refundOrder.setCourseTotal(courseTotal);
        refundOrder.setRefundStatus(EnumCode.OrderStatus.ORDER_APPLY_REBACK.getValue());
        refundOrder.setOrderPayWay(courseOrder.getOrderPayWay());
        refundOrder.setRefundCourseTotal(rest);
        refundOrder.setCourseName(classCourse.getCourseName());
        refundOrderMapper.insertSelective(refundOrder);
        return refundOrder;
    }
    @Override
    public int updateWechatUrl(Long orderId,String wechatUrl) {
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setOrderId(orderId);
        courseOrder.setWechatPayUrl(wechatUrl);
        return courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
    }
}
