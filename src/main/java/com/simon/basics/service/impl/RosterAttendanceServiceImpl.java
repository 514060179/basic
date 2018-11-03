package com.simon.basics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simon.basics.dao.RosterAttendanceMapper;
import com.simon.basics.model.EnumCode;
import com.simon.basics.model.RosterAttendance;
import com.simon.basics.model.User;
import com.simon.basics.service.RosterAttendanceService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author fengtianying
 * @date 2018/11/3 16:15
 */
@Service
public class RosterAttendanceServiceImpl implements RosterAttendanceService {

    @Autowired
    private RosterAttendanceMapper rosterAttendanceMapper;
    @Override
    public PageInfo<RosterAttendance> list(RosterAttendance rosterAttendance, int pageNum, int pageSize) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (EnumCode.UserType.TYPE_STUDENT.getValue().equals(user.getType())) {
            rosterAttendance.setAccountId(user.getAccountId());
        }
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(rosterAttendanceMapper.findByCondition(rosterAttendance));
    }
}
