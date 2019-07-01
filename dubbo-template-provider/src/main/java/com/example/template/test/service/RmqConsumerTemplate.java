package com.example.template.test.service;

import com.example.template.config.rabbitmq.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter(RabbitMqConfig.class)

@RabbitListener(queues = RabbitMqConfig.QUEUE_A)
public class RmqConsumerTemplate {
  private final Logger logger = LoggerFactory.getLogger(RmqConsumerTemplate.class);

  @RabbitHandler
  public void process(String content) {
    logger.info("处理器two接收处理队列A当中的消息： " + content);
  }
}
