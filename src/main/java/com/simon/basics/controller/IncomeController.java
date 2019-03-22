package com.simon.basics.controller;

import com.github.pagehelper.PageInfo;
import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.RosterIncomeWithOther;
import com.simon.basics.model.User;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.RosterIncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author simon.feng
 * @Date Created in 14:52 2018/9/29/029
 * @Modificd
 */
@RestController
@RequestMapping("api/income")
@Api(tags = "income", description = "教师收入")
public class IncomeController {

    @Autowired
    private RosterIncomeService rosterIncomeService;
    @PostMapping("list")
    @ApiOperation("获取每节课收入情况")
    public ReturnParam<PageInfo<RosterIncomeWithOther>> list(RosterIncomeWithOther rosterIncomeWithOther, User user,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize){
        rosterIncomeWithOther.setUser(user);
        return ReturnParam.success(rosterIncomeService.findListByPage(rosterIncomeWithOther,pageNum,pageSize));
    }

    @PostMapping("handle")
    @ApiOperation(value = "清算")
    public ReturnParam handle(@ApiParam(name = "incomeIds",value = "12,23,45") @RequestParam String incomeIds, @ApiParam(name = "handled",value = "true") @RequestParam Boolean handled){
        return ReturnParam.success(rosterIncomeService.handle(incomeIds,handled));
    }
}
