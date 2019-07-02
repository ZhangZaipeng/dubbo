package com.example.template.config.rabbitmq.listener;

import com.example.template.config.rabbitmq.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitMqConsumerTemplate implements ChannelAwareMessageListener {
  private final Logger logger = LoggerFactory.getLogger(RabbitMqConsumerTemplate.class);

  /**
   *
   * @param channel 注意:Channel的包名易混淆
   * @param message 注意:Message的包名易混淆
   */
  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    String content = new String(message.getBody(), "UTF-8");
    logger.info("处理器two接收处理队列A当中的消息： " + content);

    try {
      // 业务处理 ....

      // 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉
      // 否则消息服务器以为这条消息没处理掉 后续还会在发
      channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    } catch (IOException e) {
      e.printStackTrace();
      // 1.丢弃这条消息 basicNack（批量拒绝） basicReject（单个拒绝）
      // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
      // channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
      System.out.println("receiver fail");

    }
  }
}
