package com.simon.basics.controller;

import com.simon.basics.model.ClassCourseWithBLOBs;
import com.simon.basics.model.CourseRoster;
import com.simon.basics.model.SeatLayout;
import com.simon.basics.model.vo.ReturnParam;
import com.simon.basics.service.ClassCourseService;
import com.simon.basics.service.CourseRosterService;
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
 *
 * @author fengtianying
 * @date 2018/10/23 8:55
 */
@Api(tags = "classRoster", description = "课程名单")
@RestController
@RequestMapping("/api/classRoster")
public class CourseRosterController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CourseRosterService courseRosterService;
    @Autowired
    private ClassCourseService classCourseService;
    @PostMapping("list")
    @ApiOperation("获取课程名单")
    public ReturnParam<List<CourseRoster>> list(@RequestParam Long courseId){
        return ReturnParam.success(courseRosterService.findListByCourseId(courseId));
    }
    @PostMapping("update")
    public ReturnParam<List<CourseRoster>> update(@RequestParam Long courseId, @RequestParam String[] ids){
        ClassCourseWithBLOBs classCourseWithBLOBs = (ClassCourseWithBLOBs)classCourseService.findOne(courseId,null,null);
        if (Objects.isNull(classCourseWithBLOBs)){
            logger.warn("更新课程名单失败,无效资源courseId="+courseId);
            return ReturnParam.noHandlerFound("资源不存在:"+courseId);
        }
        SeatLayout seatLayout = classCourseWithBLOBs.getSeatLayout();
        if (Objects.isNull(seatLayout)){
            logger.warn("更新课程名单资源不存在(课程未选择座位)"+courseId);
            return ReturnParam.noHandlerFound("资源不存在(课程未选择座位):"+courseId);
        }
        int x = seatLayout.getSeatLeft()+seatLayout.getSeatMid()+seatLayout.getSeatRight()-1;
        int y = seatLayout.getSeatRows()-1;
        List<CourseRoster> updateList = new ArrayList<CourseRoster>();
        for (int i = 0; i < ids.length; i++) {
            String[] param = ids[i].split(",");
            if (param.length!=3){
                logger.error("更新课程名单,格式有误:id="+ids[i]);
                return ReturnParam.paramiolationException("ids格式有误,非[param1,prarm2,param3]");
            }
            Long rostrId = Long.parseLong(param[0]);
            int seatX = Integer.parseInt(param[1]);
            int seatY = Integer.parseInt(param[2]);
            if (seatX>x||seatY>y||seatX<0||seatY<0){
                logger.error("更新课程名单,格式有误[param1,prarm2,param3]=[{},{},{}]坐标参数小于0或超出座位范围",rostrId,seatX,seatY);
                return ReturnParam.paramiolationException("ids格式有误,坐标参数小于0或超出座位范围");
            }
            CourseRoster roster = new CourseRoster();
            roster.setRosterId(rostrId);
            roster.setRosterSeatX(seatX);
            roster.setRosterSeatY(seatY);
            updateList.add(roster);
        }
        if (courseRosterService.updateCourseRosterByList(updateList)>0){
            return ReturnParam.success(courseRosterService.findListByCourseId(courseId));
        }else{
            return ReturnParam.systemError("更新课程名单失败!");
        }
    }
}
