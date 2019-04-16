package com.example.userp.login.service;

import com.example.wallet.DubboWalletService;
import org.springframework.stereotype.Service;

@Service("walletService")
public class DubboWalletServiceImpl implements DubboWalletService {

  @Override
  public String SayHello(String p) {
    return "wallet 测试 dubbo 调用" + p;
  }

}
