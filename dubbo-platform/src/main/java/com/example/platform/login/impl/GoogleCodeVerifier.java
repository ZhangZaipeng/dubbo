package com.example.platform.login.impl;

import com.example.platform.login.KeepLoginStatusVerifier;

public class GoogleCodeVerifier extends KeepLoginStatusVerifier {

  private final String loginName;
  private final String password;
  private final int googleCode;

  public GoogleCodeVerifier(String loginName, String password, int googleCode) {
    this(loginName, password, googleCode,false, DEFAULT_KEEP_LOGIN_DAY);
  }

  public GoogleCodeVerifier(String loginName, String password, int googleCode, boolean keepLoginStatus) {
    this(loginName, password, googleCode, keepLoginStatus, DEFAULT_KEEP_LOGIN_DAY);
  }

  public GoogleCodeVerifier(String loginName, String password, int googleCode, boolean keepLoginStatus,
      int keepLoginDay) {
    super(keepLoginStatus, keepLoginDay);
    this.loginName = loginName;
    this.password = password;
    this.googleCode = googleCode;
  }

  public String getPassword() {
    return password;
  }

  public String getLoginName() {
    return loginName;
  }

  public int getGoogleCode() {
    return googleCode;
  }
}
