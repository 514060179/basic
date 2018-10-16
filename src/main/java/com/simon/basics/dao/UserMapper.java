package com.simon.basics.dao;

import com.simon.basics.model.Account;
import com.simon.basics.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int insertSelective(User record);

    int insertAccountSelective(Account account);

    int updateByPrimaryKeySelective(User record);

    int delete(@Param("accountId") Long accountId, @Param("deleted") Boolean deleted);

    User findByUserName(String userName);

    List<User> findListByCondition(User user);
}