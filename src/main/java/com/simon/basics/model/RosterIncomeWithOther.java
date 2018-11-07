package com.simon.basics.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fengtianying
 * @date 2018/11/3 11:10
 */
public class RosterIncomeWithOther extends RosterIncome{

    @ApiModelProperty(value = "课程名字")
    private String courseName;

    @ApiModelProperty(value = "筛选条件:日期.格式:2018/10")
    private String[] date;

    @ApiModelProperty(value = "日期字符串")
    private String dateStr;

    @ApiModelProperty(value = "时间字符串")
    private String timeStr;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateStr() {
        if (getCreateTime()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = sdf.format(getCreateTime());
        }
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStr() {
        if (getCreateTime()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            timeStr = sdf.format(getCreateTime());
            if (getTotalHour()!=null){
                int amount = getTotalHour().multiply(new BigDecimal(60)).intValue();
                timeStr = timeStr + "-"+sdf.format(DateUtils.addMinutes(getCreateTime(),amount));
            }
        }
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
