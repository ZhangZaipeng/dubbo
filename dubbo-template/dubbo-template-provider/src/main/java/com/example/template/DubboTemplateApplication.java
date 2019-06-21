package com.example.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableTransactionManagement
public class DubboTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(DubboTemplateApplication.class, args);
  }

}

