package com.example.common.service;

import com.example.wallet.WalletService;
import org.springframework.stereotype.Service;

@Service("walletService")
public class WalletServiceImpl implements WalletService {

  @Override
  public String SayHello(String p) {
    return "wallet 测试 dubbo 调用" + p;
  }

}
