package com.example.user.config;

import com.example.user.config.mybatis.MyBatisConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 描述: 加载配置
 *
 * @author zhangzaipeng
 **/
@Configuration
// @PropertySource("classpath:dubbo/dubbo.properties")
// @ImportResource({"classpath:dubbo/*.xml"})
@ImportResource({"classpath:dubbo/dubbo-provider.xml"})
public class DubboConfig {

}
