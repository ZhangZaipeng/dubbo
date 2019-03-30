package com.example.user.config.filter;

import com.example.platform.springmvc.WebContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFilterRegister {

  @Bean
  public FilterRegistrationBean WebContextFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new WebContextFilter());
    registration.addUrlPatterns("/*");
    registration.setName("webContextFilter");
    registration.setOrder(1);
    return registration;
  }

}
