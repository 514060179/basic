package com.simon.basics.service;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.RosterAttendance;


/**
 * @author fengtianying
 * @date 2018/11/3 16:14
 */
public interface RosterAttendanceService {

    PageInfo<RosterAttendance> list(RosterAttendance rosterAttendance , int pageNum, int pageSize);
}
