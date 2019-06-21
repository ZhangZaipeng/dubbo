package com.example.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class DubboJobApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboJobApplication.class, args);
  }

}

