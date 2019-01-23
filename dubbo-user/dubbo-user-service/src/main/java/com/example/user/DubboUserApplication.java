package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class DubboUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboUserApplication.class, args);
  }

}

