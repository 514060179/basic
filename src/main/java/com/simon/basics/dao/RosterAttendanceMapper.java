package com.simon.basics.dao;

import com.simon.basics.model.RosterAttendance;
import org.apache.ibatis.annotations.Param;

public interface RosterAttendanceMapper {

    int insertSelective(RosterAttendance record);

    RosterAttendance selectByPrimaryKey(Integer attendanceId);

    RosterAttendance selectByCourseAndNum(@Param("courseId") Long courseId, @Param("courseCurrent")int courseCurrent);

    int updateByPrimaryKeySelective(RosterAttendance record);

    int updateByCourseAndNum(RosterAttendance record);

}