package com.example.template.test.service;

import com.example.base.ts.template.DubboTemplateService;
import org.springframework.stereotype.Service;

@Service("templateService")
public class DubboTemplateServiceImpl implements DubboTemplateService {

  /**
   * dubbo 接口实现类
   * @param p
   * @return
   */
  @Override
  public String SayHello(String p) {
    return "测试 dubbo 调用" + p;
  }

}
