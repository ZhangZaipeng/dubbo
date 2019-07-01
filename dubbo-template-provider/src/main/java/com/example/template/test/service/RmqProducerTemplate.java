package com.example.template.test.service;

import com.example.template.config.rabbitmq.RabbitMqConfig;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RmqProducerTemplate implements RabbitTemplate.ConfirmCallback{

  private final Logger logger = LoggerFactory.getLogger(RmqProducerTemplate.class);

  private RabbitTemplate rabbitTemplate;
  @Autowired
  public RmqProducerTemplate(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
    //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    rabbitTemplate.setConfirmCallback(this);
  }

  @Override
  public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    // ack为true，代表MQ已经准确收到消息
    if (ack) {
      return;
    }
    // 是否需要消息补偿
    String id = correlationData.getId();
    logger.warn("发送不成功警告：id " + id);
  }

  /**
   * 发送MQ消息
   * @param content
   */
  public void sendMsg(Object content) {
    // CorrelationData 当收到消息回执时，会附带上这个参数
    CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
    //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
    rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_A, RabbitMqConfig.ROUTINGKEY_A, content, correlationId);
  }

}
