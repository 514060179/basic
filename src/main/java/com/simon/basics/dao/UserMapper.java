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

    int updatePassword(@Param("accountId") Long accountId, @Param("password") String password);

    User findByUserName(String userName);

    User findBySchoolNumber(String schoolNumber);

    List<User> findListByCondition(User user);

    List<User> noChoiceSeatList(Long courseId);

    List<User> additionalUserList(Long courseId);
}