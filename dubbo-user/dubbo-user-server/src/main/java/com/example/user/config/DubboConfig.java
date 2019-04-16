package com.example.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述: 加载配置
 *
 * @author zhangzaipeng
 **/
@Configuration
// @PropertySource("classpath:dubbo/dubbo.properties")
// @ImportResource({"classpath:dubbo/*.xml"})
@ImportResource({"classpath:dubbo/dubbo-consumer.xml"})
public class DubboConfig {

}
