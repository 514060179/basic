package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SeatLayout {
    private Long seatId;

    private Integer seatLeft;

    private Integer seatMid;

    private Integer seatRight;

    private Integer seatRows;

    private Boolean deleted;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Integer getSeatLeft() {
        return seatLeft;
    }

    public void setSeatLeft(Integer seatLeft) {
        this.seatLeft = seatLeft;
    }

    public Integer getSeatMid() {
        return seatMid;
    }

    public void setSeatMid(Integer seatMid) {
        this.seatMid = seatMid;
    }

    public Integer getSeatRight() {
        return seatRight;
    }

    public void setSeatRight(Integer seatRight) {
        this.seatRight = seatRight;
    }

    public Integer getSeatRows() {
        return seatRows;
    }

    public void setSeatRows(Integer seatRows) {
        this.seatRows = seatRows;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}