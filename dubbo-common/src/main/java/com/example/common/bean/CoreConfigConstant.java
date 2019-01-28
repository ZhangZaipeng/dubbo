package com.example.common.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoreConfigConstant {

  public static String  emailAccount;
  public static String emailPwd;
  public static String emailType;
  public static String sysNameEn;
  public static String coinNameEn;

  @Value("${email.account}")
  public void setEmailAccount(String emailAccount) {
    CoreConfigConstant.emailAccount = emailAccount;
  }

  @Value("${email.pwd}")
  public void setEmailPwd(String emailPwd) {
    CoreConfigConstant.emailPwd = emailPwd;
  }

  @Value("${email.type}")
  public void setEmailType(String emailType) {
    CoreConfigConstant.emailType = emailType;
  }

  @Value("${exchange.sysNameEn}")
  public void setSysNameEn(String sysNameEn) {
    CoreConfigConstant.sysNameEn = sysNameEn;
  }

  @Value("${exchange.coinNameEn}")
  public void setCoinNameEn(String coinNameEn) {
    CoreConfigConstant.coinNameEn = coinNameEn;
  }
}
