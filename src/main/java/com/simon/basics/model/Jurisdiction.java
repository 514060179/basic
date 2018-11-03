package com.simon.basics.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Jurisdiction {
    private Long jnId;

    private Long jnPid;

    private String jnName;

    private String jnUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getJnId() {
        return jnId;
    }

    public Long getJnPid() {
        return jnPid;
    }

    public void setJnPid(Long jnPid) {
        this.jnPid = jnPid;
    }

    public void setJnId(Long jnId) {
        this.jnId = jnId;
    }

    public String getJnName() {
        return jnName;
    }

    public void setJnName(String jnName) {
        this.jnName = jnName == null ? null : jnName.trim();
    }

    public String getJnUrl() {
        return jnUrl;
    }

    public void setJnUrl(String jnUrl) {
        this.jnUrl = jnUrl == null ? null : jnUrl.trim();
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