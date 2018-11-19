package com.simon.basics.controller;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.model.*;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseOrderService;
import com.simon.basics.service.SeatLayoutService;
import com.simon.basics.util.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fengtianying
 * @date 2018/9/18 17:44
 */
@RestController
@RequestMapping("api/seatLayout")
@Api(tags = "seatLayout", description = "座位结构")
public class SeatLayoutController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeatLayoutService seatLayoutService;
    @Autowired
    private ClassCourseService classCourseService;
    @Autowired
    private CourseOrderService courseOrderService;
    @Autowired
    private JedisService jedisService;

    @PostMapping("list")
    @ApiOperation("获取所有座位")
    public ReturnParam list() {
        return ReturnParam.success(seatLayoutService.findAllList());
    }

    @PostMapping("choice")
    @ApiOperation("选择座位")
    public ReturnParam<CourseRoster> choice(@RequestParam Long courseId, @RequestParam int seatX, @RequestParam int seatY,Long accountId) {
        //redis 存储
        //获取课程
        CourseOrderWithBLOBs courseOrder = (CourseOrderWithBLOBs)courseOrderService.findOneByCourseId(courseId,accountId);
        if (Objects.isNull(courseOrder)|| !EnumCode.OrderStatus.ORDER_PAID.getValue().equals(courseOrder.getOrderStatus())){
            logger.warn("选择座位，课程courseId={}未购买!",courseId);
            return ReturnParam.courseNotEnoughOrNotHad();
        }
        Long seatId = courseOrder.getSeatId();
        if (!jedisService.exists("course-seatLayout:"+courseId)){
            seatLayoutService.pullAllToRedis();
            jedisService.put("course-seatLayout:"+courseId,jedisService.mapGet("seatLayoutMap",seatId+""));
        }
        List<String> seatList = JSONUtil.jsonToList(jedisService.getString("course-seatLayout:"+courseId));
        if (seatList.contains(seatX+","+seatY)){
            CourseRoster courseRoster =seatLayoutService.addRoster(courseOrder,seatX,seatY);
            if(courseRoster!=null){
                //移除
                seatList.remove(seatX+","+seatY);
                //重置
                jedisService.put("course-seatLayout:"+courseId,JSONUtil.listToJson(seatList));
            }
            return ReturnParam.success(courseRoster);

        }else{
            logger.warn("{}座位{}已被选走",seatId,seatX+","+seatY);
            return ReturnParam.alreadyChoose();
        }
    }
}
