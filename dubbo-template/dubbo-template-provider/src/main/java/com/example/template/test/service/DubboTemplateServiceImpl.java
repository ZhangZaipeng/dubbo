package com.example.template.test.service;

import com.example.template.DubboTemplateService;
import org.springframework.stereotype.Service;

@Service("loginService")
public class DubboTemplateServiceImpl implements DubboTemplateService {

  @Override
  public String SayHello(String p) {
    return "测试 dubbo 调用" + p;
  }

}
