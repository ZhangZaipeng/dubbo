package com.example.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class DubboTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboTaskApplication.class, args);
  }

}

