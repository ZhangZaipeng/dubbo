package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class APIApplication {

  public static void main(String[] args) {
    SpringApplication.run(APIApplication.class, args);
  }

}

