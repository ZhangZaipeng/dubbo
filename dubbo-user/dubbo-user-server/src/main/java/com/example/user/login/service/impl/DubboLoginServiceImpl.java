package com.example.user.login.service.impl;

import com.example.user.DubboLoginService;
import org.springframework.stereotype.Service;

@Service("loginService")
public class DubboLoginServiceImpl implements DubboLoginService {

  @Override
  public String SayHello(String p) {
    return "测试 dubbo 调用" + p;
  }

}
