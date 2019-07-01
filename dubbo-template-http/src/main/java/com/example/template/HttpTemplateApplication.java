package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class HttpTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(HttpTemplateApplication.class, args);
  }

}

