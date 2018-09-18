package com.simon.basics.service.impl;

import com.simon.basics.dao.CourseOrderMapper;
import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.User;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.util.SnowflakeIdWorker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int create(ClassCourse classCourse) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setAccountId(user.getAccountId());
        courseOrder.setCourseId(classCourse.getCourseId());
        courseOrder.setOrderNo(new SnowflakeIdWorker().nextId()+"");
        courseOrder.setOrderCost(classCourse.getCourseCost());
        return courseOrderMapper.updateByPrimaryKey(courseOrder);
    }
}
