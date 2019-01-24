package com.example.user.service;

import com.example.user.LoginService;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

  @Override
  public String SayHello(String p) {
    return "测试 dubbo 调用" + p;
  }

}
