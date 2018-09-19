package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
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
    public List<CourseOrder> getListByPage(CourseOrder courseOrder, int pageNum, int pageSize) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())){//学生 查询自己
            courseOrder.setAccountId(user.getAccountId());
        }else{//管理员 查询所有
            courseOrder.setAccountId(null);
        }
        PageHelper.startPage(pageNum,pageSize);
        return courseOrderMapper.findListByCondition(courseOrder);
    }

    @Override
    public CourseOrder create(ClassCourse classCourse) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setAccountId(user.getAccountId());
        courseOrder.setCourseId(classCourse.getCourseId());
        courseOrder.setOrderNo(new SnowflakeIdWorker().nextId()+"");
        courseOrder.setOrderCost(classCourse.getCourseCost());
        int i = courseOrderMapper.insertSelective(courseOrder);
        if(i>0){
            return courseOrder;
        }
        return null;
    }

    @Override
    public CourseOrder findOneByCourseId(Long courseId) {
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setCourseId(courseId);
        List<CourseOrder> findCourseOrderList = courseOrderMapper.findListByCondition(courseOrder);
        if (findCourseOrderList==null||findCourseOrderList.size()==0){
            return null;
        }
        return findCourseOrderList.get(0);
    }
}
