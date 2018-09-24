package com.simon.basics.service.impl;

import com.simon.basics.componet.service.JedisService;
import com.simon.basics.dao.CourseRosterMapper;
import com.simon.basics.dao.SeatLayoutMapper;
import com.simon.basics.model.CourseOrder;
import com.simon.basics.model.CourseOrderWithBLOBs;
import com.simon.basics.model.CourseRoster;
import com.simon.basics.model.SeatLayout;
import com.simon.basics.service.SeatLayoutService;
import com.simon.basics.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengtianying
 * @date 2018/9/18 17:41
 */
@Service
public class SeatLayoutServiceImpl implements SeatLayoutService {

    @Autowired
    private SeatLayoutMapper seatLayoutMapper;

    @Autowired
    private CourseRosterMapper courseRosterMapper;

    @Autowired
    private JedisService jedisService;
    @Override
    public List<SeatLayout> findAllList() {

        if (jedisService.exists("seatLayoutList")){
            List<SeatLayout> list = JSONUtil.jsonToList(jedisService.getString("seatLayoutList"));
            return list;
        }else{
            pullAllToRedis();
        }
        return seatLayoutMapper.findAllList();
    }

    @Override
    public void pullAllToRedis() {
        List<SeatLayout> seatLayoutList = seatLayoutMapper.findAllList();
        List<String> seatList = new ArrayList<String>();
        //把所有的座位结构放到redis中
        Map<String,String> seatLayoutMap = new HashMap<String,String>();
        for (SeatLayout seatLayout:seatLayoutList
                ) {
            int x = seatLayout.getSeatLeft()+seatLayout.getSeatMid()+seatLayout.getSeatRight();
            int y = seatLayout.getSeatRows();
            for (int i =0 ;i<x;i++){
                for (int j =0;j<y;j++){
                    seatList.add(new StringBuffer(i+"").append(",").append(j).toString());
                }
            }
            seatLayoutMap.put(""+seatLayout.getSeatId(),JSONUtil.listToJson(seatList));
        }
        jedisService.mapPut("seatLayoutMap",seatLayoutMap);
        jedisService.put("seatLayoutList",JSONUtil.listToJson(seatLayoutList));
    }

    @Override
    public CourseRoster addRoster(CourseOrderWithBLOBs courseOrder, int seatX, int seatY) {
        CourseRoster courseRoster = new CourseRoster();
        courseRoster.setRosterCourseCountRest(courseOrder.getCourseTotal());
        courseRoster.setAccountId(courseOrder.getAccountId());
        courseRoster.setRosterSeatX(seatX);
        courseRoster.setRosterSeatY(seatY);
        courseRoster.setCourseId(courseOrder.getCourseId());
        courseRosterMapper.insertSelective(courseRoster);
        return courseRoster;
    }

//    @Override
//    public List<String> findCourseSeatLayout(Long seatId) {
//        if (jedisService.mapExists("seatLayoutMap",seatId+"")){
//            return JSONUtil.jsonToList(jedisService.mapGet("seatLayoutMap",""+seatId));
//        }else{
//            findAllList();
//            return JSONUtil.jsonToList(jedisService.mapGet("seatLayoutMap",""+seatId));
//        }
//    }
}
