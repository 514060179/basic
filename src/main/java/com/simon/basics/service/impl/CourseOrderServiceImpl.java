package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.CourseOrderMapper;
import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.User;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author simon.feng
 * @Date Created in 21:02 2018/9/18/018
 * @Modificd
 */
@Service
public class CourseOrderServiceImpl implements CourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;

    @Override
    public PageInfo<CourseOrder> getListByPage(CourseOrder courseOrder, int pageNum, int pageSize) {
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
        courseOrder.setOrderCost(classCourse.getCourseCost());
        int i = courseOrderMapper.insertSelective(courseOrder);
        if(i>0){
            return courseOrder;
        }
        return null;
    }

    @Override
    public CourseOrder findOneByCourseId(Long courseId) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setCourseId(courseId);
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            courseOrder.setAccountId(user.getAccountId());
        }else{//管理员 查询所有
            courseOrder.setAccountId(null);
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
    public int paySuccess(Long orderId, String orderNo, String payWay) {
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setOrderId(orderId);
        courseOrder.setOrderStatus(EnumCode.OrderStatus.ORDER_PAID.getValue());
        courseOrder.setOrderNo(orderNo);
        if ("101".equals(payWay)){
            courseOrder.setOrderPayWay("alipay");
        }else{
            courseOrder.setOrderPayWay("wechat");
        }
        return courseOrderMapper.updateByPrimaryKeySelective(courseOrder);
    }
}
