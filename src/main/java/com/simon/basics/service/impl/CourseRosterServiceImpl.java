package com.simon.basics.service.impl;

import com.simon.basics.dao.CourseRosterMapper;
import com.simon.basics.model.CourseRoster;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.User;
import com.simon.basics.service.CourseRosterService;
import com.simon.basics.util.JSONUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fengtianying
 * @date 2018/10/23 9:46
 */
@Service
public class CourseRosterServiceImpl implements CourseRosterService {

    @Autowired
    private CourseRosterMapper courseRosterMapper;
    @Override
    public List<CourseRoster> findListByCourseId(Long courseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        CourseRoster courseRoster = new CourseRoster();
        courseRoster.setCourseId(courseId);
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())) {
            courseRoster.setAccountId(user.getAccountId());
        }
        return courseRosterMapper.findOneByConditon(courseRoster);
    }

    @Transactional
    @Override
    public int updateCourseRosterByList(List<CourseRoster> courseRosterList) {
        int result = 0;
        for (CourseRoster courseRoster:courseRosterList
             ) {
            int j = courseRosterMapper.updateByPrimaryKeySelective(courseRoster);
            if (j<=0){
                throw new RuntimeException("更新失败:courseRoster="+ JSONUtil.objectToJson(courseRoster));
            }
            result += j;
        }
        return result;
    }
}
