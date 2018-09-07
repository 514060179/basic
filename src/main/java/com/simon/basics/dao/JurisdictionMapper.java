package com.simon.basics.dao;

import com.simon.basics.model.Jurisdiction;

import java.util.List;

public interface JurisdictionMapper {
    int deleteByPrimaryKey(Long jnId);

    int insert(Jurisdiction record);

    int insertSelective(Jurisdiction record);

    Jurisdiction selectByPrimaryKey(Long jnId);

    int updateByPrimaryKeySelective(Jurisdiction record);

    int updateByPrimaryKey(Jurisdiction record);

    List<Jurisdiction> findListByRoleId(Long roleId);
}