package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.simon.basics.dao.ClassCourseMapper;
import com.simon.basics.model.ClassCourse;
import com.simon.basics.model.ClassCourseWithBLOBs;
import com.simon.basics.model.User;
import com.simon.basics.service.ClassCourseService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/9/18 14:29
 */
@Service
public class ClassCourseServiceimpl implements ClassCourseService {

    @Autowired
    private ClassCourseMapper classCourseMapper;
    @Override
    public ClassCourse findOne(Long courseId) {
        return classCourseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public List<ClassCourseWithBLOBs> findListByPage(ClassCourse classCourse, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return classCourseMapper.findListByCondition(classCourse);
    }

    @Override
    public int update(ClassCourse classCourse) {
        return classCourseMapper.updateByPrimaryKeySelective(classCourse);
    }

    @Override
    public int delete(Long courseId) {
        return classCourseMapper.updateStatus(courseId,true);
    }

    @Override
    public int add(ClassCourse classCourse) {
        User user=(User)SecurityUtils.getSubject().getPrincipal();
        classCourse.setAccountId(user.getAccountId());
        return classCourseMapper.insertSelective(classCourse);
    }
}
