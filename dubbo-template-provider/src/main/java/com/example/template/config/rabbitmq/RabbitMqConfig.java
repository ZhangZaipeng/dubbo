package com.example.template.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  // 定义交换机、路由key、队列名词
  public static final String Template_Exchange = "Template_Exchange";
  public static final String Template_Routingkey = "Template_Routingkey";
  public static final String Template_Queue = "Template_Queue";

  @Bean
  Binding binding_A(){
    // 构建对象 Queue
    Queue queueA = new Queue(Template_Queue,true,false,false);
    // 构建对象 Exchange
    DirectExchange exchangeA= new DirectExchange(RabbitMqConfig.Template_Exchange,true,false);
    return BindingBuilder.bind(queueA).to(exchangeA).with(RabbitMqConfig.Template_Routingkey);
  }


}
