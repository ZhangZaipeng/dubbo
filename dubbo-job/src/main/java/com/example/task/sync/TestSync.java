package com.example.task.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// 加仓 提醒
// 加仓线 抵押率上浮15%  平仓线 抵押率上浮10%
// 一个小时一次
@Component
public class TestSync {

  private static final Logger logger = LoggerFactory.getLogger(TestSync.class);

  private static boolean isRunning = false;

  // 每天中午 12点触发
  //@Scheduled(cron = "0 0 12 * * ?") @Scheduled(cron = "0 0/1 * * * ?")
  @Scheduled(cron = "0 0 12 * * ?")
  public void execute() {
    if (isRunning) {
      return;
    }
    isRunning = true;

    try {

      logger.info("============= 运行中 ===========");

    } catch (Exception e) {
      logger.error("============= 有异常 ===========");
      e.printStackTrace();
    } finally {
      isRunning = false;
    }

  }
}
