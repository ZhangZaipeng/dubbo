package com.example.userp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableTransactionManagement
public class DubboUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboUserApplication.class, args);
  }

}

