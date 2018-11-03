package com.simon.basics.controller;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.CourseRosterAttendance;
import com.simon.basics.model.RosterAttendance;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.RosterAttendanceService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengtianying
 * @date 2018/11/3 15:42
 */
@RestController
@RequestMapping("api/rosterAttendance")
@Api(tags = "rosterAttendance", description = "出勤情况")
public class RosterAttendanceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RosterAttendanceService rosterAttendanceService;
    @PostMapping("list")
    public ReturnParam<PageInfo<RosterAttendance>> list(RosterAttendance rosterAttendance , @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        return ReturnParam.success(rosterAttendanceService.list(rosterAttendance,pageNum,pageSize));
    }
}
