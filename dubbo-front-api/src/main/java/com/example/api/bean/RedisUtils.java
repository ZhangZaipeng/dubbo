package com.example.api.bean;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

  private static RedisTemplate<String,String> redisTemplate;

  @Autowired(required = false)
  public RedisUtils(RedisTemplate<String, String> redisTemplate) {
    RedisUtils.redisTemplate = redisTemplate;
  }

  public static String get(String cacheKey) {
    return redisTemplate.opsForValue().get(cacheKey);
  }

  public static boolean del(String cacheKey) {
    redisTemplate.delete(cacheKey);
    return true;
  }

  public static void setex(String cacheKey, int expireSec, String v) {
    redisTemplate.opsForValue().set(cacheKey, v, expireSec, TimeUnit.SECONDS);
  }


}
