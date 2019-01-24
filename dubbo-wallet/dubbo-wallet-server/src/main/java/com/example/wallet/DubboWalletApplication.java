package com.example.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class DubboWalletApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboWalletApplication.class, args);
  }

}

