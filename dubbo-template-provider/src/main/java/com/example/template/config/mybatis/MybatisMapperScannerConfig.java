package com.example.template.config.mybatis;

import com.example.base.mybatis.DefaultMapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by zhangzaipeng on 2018/10/16
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
