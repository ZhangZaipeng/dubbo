package com.example.api.controller;

import com.example.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @GetMapping(value = "/test.json")
  public String test() {
    return loginService.SayHello("9999");
  }
}
