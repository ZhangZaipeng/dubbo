package com.example.api.controller;

import com.example.template.DubboTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired
  private DubboTemplateService templateService;

  @GetMapping("/test")
  public String test(){
    return templateService.SayHello("123213");
  }
}
