package com.example.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class DubboApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboApplication.class, args);
  }

}
