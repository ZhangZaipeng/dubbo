package com.example.user.config;

import com.example.platform.mybatis.DefaultMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * created by pansai on 2018/10/16
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MybatisMapperScannerConfig {

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer(){
    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    mapperScannerConfigurer.setBasePackage("com.example");
    mapperScannerConfigurer.setMarkerInterface(DefaultMapper.class);
    return mapperScannerConfigurer;
  }

}
