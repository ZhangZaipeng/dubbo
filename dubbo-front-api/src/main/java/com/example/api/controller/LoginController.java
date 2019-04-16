package com.example.api.controller;

import com.example.user.DubboLoginService;
import com.example.wallet.DubboWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired
  private DubboLoginService loginService;

  @Autowired
  private DubboWalletService walletService;

  @GetMapping(value = "/test.json")
  public String test() {
    return loginService.SayHello("9999");
  }

  @GetMapping(value = "/test1.json")
  public String test1() {
    return walletService.SayHello("8888");
  }
}
