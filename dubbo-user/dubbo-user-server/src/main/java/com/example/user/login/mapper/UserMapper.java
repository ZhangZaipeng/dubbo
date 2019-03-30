package com.example.user.login.mapper;

import com.example.platform.mybatis.DefaultMapper;
import com.example.user.login.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends DefaultMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户昵称获取用户信息
     * @param userName
     * @return
     */
    User findUserByUserName(@Param(value = "userName") String userName);

}
