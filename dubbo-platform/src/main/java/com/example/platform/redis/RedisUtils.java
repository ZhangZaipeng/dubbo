package com.example.platform.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.StringUtils;
import com.example.platform.YvanUtil;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisUtils {

  private static RedisTemplate<Object, Object> redisTemplate;
  private static StringRedisTemplate stringRedisTemplate;

  @Autowired(required = false)
  public RedisUtils(RedisTemplate<Object, Object> redisTemplate,
      StringRedisTemplate stringRedisTemplate) {
    RedisUtils.redisTemplate = redisTemplate;
    RedisUtils.stringRedisTemplate = stringRedisTemplate;
  }


  public static RedisTemplate getRedisTemplate() {
    return redisTemplate;
  }

  public static StringRedisTemplate getStringRedisTemplate() {
    return stringRedisTemplate;
  }

  /**
   * 删除缓存<br> 根据key精确匹配删除
   */
  @SuppressWarnings("unchecked")
  public static void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(CollectionUtils.arrayToList(key));
      }
    }
  }

  /**
   * 批量删除<br> （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
   */
  public static void batchDel(String... pattern) {
    for (String kp : pattern) {
      redisTemplate.delete(redisTemplate.keys(kp + "*"));
    }
  }

  /**
   * 取得缓存（int型）
   */
  public static Integer getInt(String key) {
    String value = stringRedisTemplate.boundValueOps(key).get();
    if (!StringUtils.isEmpty(value)) {
      return Integer.valueOf(value);
    }
    return null;
  }

  /**
   * 取得缓存（字符串类型）
   */
  public static String getStr(String key) {
    return stringRedisTemplate.boundValueOps(key).get();
  }

  /**
   * 取得缓存（字符串类型）
   */
  public static String getStr(String key, boolean retain) {
    String value = stringRedisTemplate.boundValueOps(key).get();
    if (!retain) {
      redisTemplate.delete(key);
    }
    return value;
  }

  /**
   * 获取缓存<br> 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
   */
  public static Object getObj(String key) {
    return redisTemplate.boundValueOps(key).get();
  }

  /**
   * 获取缓存<br> 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
   *
   * @param retain 是否保留
   */
  public static Object getObj(String key, boolean retain) {
    Object obj = redisTemplate.boundValueOps(key).get();
    if (!retain) {
      redisTemplate.delete(key);
    }
    return obj;
  }

  /**
   * 获取缓存<br> 注：该方法暂不支持Character数据类型
   *
   * @param key key
   * @param clazz 类型
   */
  @SuppressWarnings("unchecked")
  public static <T> T get(String key, Class<T> clazz) {
    return (T) redisTemplate.boundValueOps(key).get();
  }

  /**
   * 获取缓存json对象<br>
   *
   * @param key key
   * @param clazz 类型
   */
  public static <T> T getJson(String key, Class<T> clazz) {
    return YvanUtil.jsonToObj(key, clazz);
  }

  /**
   * 将value对象写入缓存
   *
   * @param time 失效时间(秒)
   */
  public static void set(String key, Object value, Date time) {
    if (value.getClass().equals(String.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Integer.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Double.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Float.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Short.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Long.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else if (value.getClass().equals(Boolean.class)) {
      stringRedisTemplate.opsForValue().set(key, value.toString());
    } else {
      redisTemplate.opsForValue().set(key, value);
    }
    if (time.getTime() > 0) {
      redisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
    }
  }

  public static void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  /**
   * 将value对象以JSON格式写入缓存
   *
   * @param time 失效时间(秒)
   */
  public static void setJson(String key, Object value, Date time) {
    stringRedisTemplate.opsForValue().set(key, YvanUtil.toJson(value));
    if (time.getTime() > 0) {
      stringRedisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
    }
  }

  public static void setJson(String key, Object value) {
    stringRedisTemplate.opsForValue().set(key, YvanUtil.toJson(value));
  }

  /**
   * 更新key对象field的值
   *
   * @param key 缓存key
   * @param field 缓存对象field
   * @param value 缓存对象field值
   */
  public static void setJsonField(String key, String field, String value) {
    JSONObject obj = JSON.parseObject(stringRedisTemplate.boundValueOps(key).get());
    obj.put(field, value);
    stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
  }


  /**
   * 递减操作
   */
  public static double decr(String key, double by) {
    return redisTemplate.opsForValue().increment(key, -by);
  }

  /**
   * 递增操作
   */
  public static double incr(String key, double by) {
    return redisTemplate.opsForValue().increment(key, by);
  }

  /**
   * 获取double类型值
   */
  public static double getDouble(String key) {
    String value = stringRedisTemplate.boundValueOps(key).get();
    if (!StringUtils.isEmpty(value)) {
      return Double.valueOf(value);
    }
    return 0d;
  }

  /**
   * 设置double类型值
   *
   * @param time 失效时间(秒)
   */
  public static void setDouble(String key, double value, Date time) {
    stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
    if (time.getTime() > 0) {
      stringRedisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
    }
  }

  /**
   * 设置double类型值
   *
   * @param time 失效时间(秒)
   */
  public static void setInt(String key, int value, Date time) {
    stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
    if (time.getTime() > 0) {
      stringRedisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
    }
  }

  /**
   * 将map写入缓存
   *
   * @param time 失效时间(秒)
   */
  public static <T> void setMap(String key, Map<String, T> map, Date time) {
    redisTemplate.opsForHash().putAll(key, map);
  }

  /**
   * <p>通过key获取所有的field和value</p>
   */
  public static Map<Object, Object> hgetall(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * <p>通过key同时设置 hash的多个field</p>
   *
   * @return 返回OK 异常返回null
   */
  public static void hmset(String key, Map<String, String> hash) {
    redisTemplate.boundHashOps(key).putAll(hash);
  }

  /**
   * 向key对应的map中添加缓存对象
   */
  public static <T> void addMap(String key, Map<String, T> map) {
    redisTemplate.opsForHash().putAll(key, map);
  }

  /**
   * 向key对应的map中添加缓存对象
   *
   * @param key cache对象key
   * @param field map对应的key
   * @param value 值
   */
  public static void addMap(String key, String field, String value) {
    redisTemplate.opsForHash().put(key, field, value);
  }

  /**
   * 向key对应的map中添加缓存对象
   *
   * @param key cache对象key
   * @param field map对应的key
   * @param obj 对象
   */
  public static <T> void addMap(String key, String field, T obj) {
    redisTemplate.opsForHash().put(key, field, obj);
  }

  /**
   * 获取map缓存中的某个对象
   */
  @SuppressWarnings("unchecked")
  public static <T> T getMapField(String key, String field, Class<T> clazz) {
    return (T) redisTemplate.boundHashOps(key).get(field);
  }

  /**
   * 删除map中的某个对象
   *
   * @param key map对应的key
   * @param field map中该对象的key
   * @date 2016年8月10日
   */
  public void delMapField(String key, String... field) {
    BoundHashOperations<Object, String, ?> boundHashOperations = redisTemplate.boundHashOps(key);
    boundHashOperations.delete(field);
  }

  /**
   * 指定缓存的失效时间
   *
   * @param key 缓存KEY
   * @param time 失效时间(秒)
   * @date 2016年8月14日
   */
  public static void expire(String key, Date time) {
    if (time.getTime() > 0) {
      redisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
    }
  }

  /**
   * 添加set
   */
  public static void sadd(String key, String... value) {
    redisTemplate.boundSetOps(key).add(value);
  }

  /**
   * 通过key随机删除一个set中的value并返回该值
   */
  public static Object spop(String key) {
    return redisTemplate.boundSetOps(key).pop();
  }

  /**
   * 通过key获取set中随机的value,不删除元素
   */
  public static Object srandmember(String key) {
    return redisTemplate.boundSetOps(key).randomMember();
  }

  public static Set<Object> members(String key) {
    return redisTemplate.boundSetOps(key).members();
  }

  public static Long ssize(String key) {
    return redisTemplate.boundSetOps(key).size();
  }

  /**
   * 通过key判断value是否是set中的元素
   */
  public static Boolean sismember(String key, Object member) {
    return redisTemplate.boundSetOps(key).isMember(member);
  }


  /**
   * 删除set集合中的对象
   */
  public static Long srem(String key, String... value) {
    return redisTemplate.boundSetOps(key).remove(value);
  }

  /**
   * set重命名
   */
  public static void srename(String oldkey, String newkey) {
    redisTemplate.boundSetOps(oldkey).rename(newkey);
  }

  /**
   * 短信缓存
   *
   * @author fxl
   * @date 2016年9月11日
   */
  public static void setIntForPhone(String key, Object value, int time) {
    stringRedisTemplate.opsForValue().set(key, YvanUtil.toJson(value));
    if (time > 0) {
      stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
  }

  /**
   * 模糊查询keys
   */
  public static Set<Object> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }


  /**
   * set无序集合类型: 返回指定的所有集合的成员的交集。如果key不存在则被认为是一个空的集合，当给定的集合为空的时候，结果也为空(一个集合为空，结果一直为空)
   */
  public static Set<byte[]> sinter(final byte[]... bytes) {
    return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
      @Override
      public Set<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
        return redisConnection.sInter(bytes);
      }
    });
  }
}
