package com.example.template.config.rabbitmq;

import com.example.template.config.rabbitmq.listener.RabbitMqConsumerTemplate;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqListenerConfig {

  @Bean("TemplateContainer")
  public MessageListenerContainer btcListenerContainer(
      ConnectionFactory connectionFactory,
      RabbitMqConsumerTemplate rabbitMqConsumerTemplate) {

    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(RabbitMqConfig.Template_Queue);
    container.setMessageListener(rabbitMqConsumerTemplate);
    // 设置消费者手动确认
    container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    return container;
  }

}
