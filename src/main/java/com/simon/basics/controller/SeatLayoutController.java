package com.simon.basics.controller;

import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.SeatLayoutService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengtianying
 * @date 2018/9/18 17:44
 */
@RestController
@RequestMapping("api/seatLayout")
@Api(tags = "seatLayout", description = "座位结构")
public class SeatLayoutController {

    @Autowired
    private SeatLayoutService seatLayoutService;

    @PostMapping("list")
    public ReturnParam list() {
        return ReturnParam.success(seatLayoutService.findAllList());
    }

    @PostMapping("choice")
    public ReturnParam choice(@RequestParam Long courseId, @RequestParam Long seatId, @RequestParam int seatX, @RequestParam int seatY) {
       s
        return null;
    }
}
