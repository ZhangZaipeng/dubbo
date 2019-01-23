package com.example.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述: 加载配置
 *
 * @author zhangzaipeng
 **/
@Configuration
@ImportResource({"classpath:dubbo/*.xml"})
public class DubboConfig {

}
