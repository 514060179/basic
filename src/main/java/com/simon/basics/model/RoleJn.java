package com.simon.basics.model;

import java.util.Date;

public class RoleJn {
    private Long roleJnId;

    private Long roleId;

    private Long jnId;

    private Date createTime;

    private Date updateTime;

    public Long getRoleJnId() {
        return roleJnId;
    }

    public void setRoleJnId(Long roleJnId) {
        this.roleJnId = roleJnId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getJnId() {
        return jnId;
    }

    public void setJnId(Long jnId) {
        this.jnId = jnId;
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