package com.example.user.login;

import com.example.user.utils.login.impl.PasswordVerifier;

/**
 * 移动端 ：登录名 + 登录密码
 */
public class AppLoginVerifier extends PasswordVerifier {

  public AppLoginVerifier(String loginName, String password) {
    super(loginName, password);
  }

}
