package com.example.user.login.service.impl;

import com.example.common.crypt.BCrypt;
import com.example.common.date.DateUtils;
import com.example.common.exception.ResultErrException;
import com.example.common.utils.StringUtils;
import com.example.platform.Conv;
import com.example.platform.YvanUtil;
import com.example.user.bean.RedisUtils;
import com.example.user.login.AppLoginVerifier;
import com.example.user.login.domain.UserAgent;
import com.example.user.login.mapper.UserAgentMapper;
import com.example.user.utils.ex.AuthenticationException;
import com.example.user.utils.login.AuthenticationProvider;
import com.example.user.utils.login.Principal;
import com.example.user.utils.login.Verifier;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

  Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

  private static final String USER_CACHE = "USER_AGENT";
  private static final String LOGIN_TOKEN = "LOGIN_TOKEN";

  @Autowired
  private UserAgentMapper userAgentMapper;

  @Override
  public Principal get(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = RedisUtils.get(cacheKey);
    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }
    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);
    String ids = map.get("id").toString();
    if (ids.startsWith("USER:")) {

      UserAgent userAgent = getUserAgentFromCache(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        return userAgent;
      }
      userAgent = userAgentMapper.selectByPrimaryKey(Conv.NL(ids.substring(5)));
      if (userAgent != null) {
        addToCache(userAgent);
        return userAgent;
      }
    }
    return null;
  }

  @Override
  public boolean rm(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;
    return RedisUtils.del(cacheKey);
  }

  @Override
  public Principal authenticate(Verifier verifier) throws AuthenticationException {

    if (verifier instanceof AppLoginVerifier) {
      // 前段
      AppLoginVerifier appLoginVerifier = (AppLoginVerifier) verifier;

      UserAgent userAgent = userAgentMapper.selectByLoginName(appLoginVerifier.getLoginName());

      if (userAgent == null) {
        throw new AuthenticationException("用户不存在");
      }
      // -1-冻结, 0-未激活, 1-正常, 9-异常
      userStatusCheck(userAgent);

      if (!BCrypt.checkpw(appLoginVerifier.getPassword(), userAgent.getLoginPwd())) {
        throw new AuthenticationException("用户 密码错误");
      }

      // lastLoginTime
      Long lastLoginTime = System.currentTimeMillis() / 1000;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      // 更新登录次数 最后登录时间
      userAgentMapper.loginSuccess(userAgent.getUserAgentId(),
          DateUtils.format(new Date(lastLoginTime*1000),"yyyy-MM-dd HH:mm:ss"));
      userAgent.setLastLoginTime(lastLoginTime);

      addToCache(userAgent);

      userAgent.setLoginName(appLoginVerifier.getLoginName());

      logger.info(">>>>> 用户 {} >>>>> 在时间 {} >>>>> 密码登录校验成功", userAgent.getUserId(), lastLoginTime );

      return userAgent;

    }

    throw new ResultErrException("");
  }

  @Override
  public Principal authenticate(Verifier verifier, String ip) throws AuthenticationException {
    // 暂不支持
    throw new ResultErrException("暂不支持");
  }

  @Override
  public Date getLastRequestTime(String uuid) {
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    String jv = RedisUtils.get(cacheKey);

    if (StringUtils.isNullOrEmpty(jv)) {
      return null;
    }

    Map<String, Object> map = (Map<String, Object>) YvanUtil.jsonToMap(jv);

    Long lastRequestTime = Conv.NL(map.get("lastRequestTime"));

    return new Date(lastRequestTime);
  }

  @Override
  public void setLastRequestTime(Date lastRequestTime, Serializable id, String uuid) {

    Map<String, Object> map = new HashMap<String, Object>();
    String cacheKey = LOGIN_TOKEN + ":" + uuid;

    map.put("id", id);
    map.put("lastRequestTime", Conv.NS(lastRequestTime.getTime()));

    String ids = id.toString();
    int expireSec = 0;
    if (ids.startsWith("USER:")) {  // 1个小时
      // expireSec = Integer.MAX_VALUE;
      expireSec = 1 * 24 * 60 * 60 ;
    }

    String jsonValue = YvanUtil.toJson(map);

    RedisUtils.setex(cacheKey, expireSec, jsonValue);

  }

  @Override
  public Principal authenticate_auto(Verifier verifier) throws AuthenticationException {
    throw new ResultErrException("不支持");
  }

  private void addToCache(UserAgent userAgent) {
    String cacheKey = USER_CACHE + ":" + userAgent.getUserAgentId();

    String jsonValue = YvanUtil.toJson(userAgent);
    // 存放到redis中
    RedisUtils.setex(cacheKey, 120, jsonValue);

  }

  private UserAgent getUserAgentFromCache(Long custAgentId) {
    String cacheKey = USER_CACHE + ":" + custAgentId;

    String jv = RedisUtils.get(cacheKey);

    if (!StringUtils.isNullOrEmpty(jv)) {
      return YvanUtil.jsonToObj(jv, UserAgent.class);
    }

    return null;
  }

  private void userStatusCheck(UserAgent userAgent){
    if (userAgent.getDeleted() == -1) {
      throw new AuthenticationException("账户 冻结");
    }

    if (userAgent.getDeleted() == 0) {
      throw new AuthenticationException("");
    }

    if (userAgent.getDeleted() == 9) {
      throw new AuthenticationException("");
    }
  }
}
