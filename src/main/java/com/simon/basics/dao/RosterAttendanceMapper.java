package com.simon.basics.dao;

import com.simon.basics.model.RosterAttendance;

public interface RosterAttendanceMapper {
    int deleteByPrimaryKey(Integer attendanceId);

    int insert(RosterAttendance record);

    int insertSelective(RosterAttendance record);

    RosterAttendance selectByPrimaryKey(Integer attendanceId);

    int updateByPrimaryKeySelective(RosterAttendance record);

    int updateByPrimaryKey(RosterAttendance record);
}