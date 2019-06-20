package com.example.api.login.service;

import com.example.common.response.ResponseModel;
import com.example.api.login.domain.User;
import com.example.api.login.domain.UserAgent;

public interface UserService {

  /**
   * 登录
   * @param userName
   * @param pwd
   * @return
   */
  ResponseModel login(String userName, String pwd);

  /**
   * 退出登录
   * @return
   */
  ResponseModel loginOut();

  ResponseModel register(String userName, String pwd);

  ResponseModel getInfo(String userName);

  /**
   * 添加用户信息
   * @param user
   * @param userAgent
   * @return
   */
  boolean regUser(User user, UserAgent userAgent);
}
