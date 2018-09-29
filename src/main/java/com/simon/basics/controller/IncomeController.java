package com.simon.basics.controller;

import com.simon.basics.model.RosterIncome;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.RosterIncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ReturnParam list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize){
        return ReturnParam.success(rosterIncomeService.findListByPage(new RosterIncome(),pageNum,pageSize));
    }
}
