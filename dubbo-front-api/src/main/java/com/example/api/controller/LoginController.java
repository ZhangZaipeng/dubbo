package com.example.api.controller;

import com.example.user.LoginService;
import com.example.wallet.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @Autowired
  private WalletService walletService;

  @GetMapping(value = "/test.json")
  public String test() {
    return loginService.SayHello("9999");
  }

  @GetMapping(value = "/test1.json")
  public String test1() {
    return walletService.SayHello("8888");
  }
}
