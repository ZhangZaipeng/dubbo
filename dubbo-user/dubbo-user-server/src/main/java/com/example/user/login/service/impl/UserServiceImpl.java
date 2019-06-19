package com.example.user.login.service.impl;

import com.example.common.utils.crypt.BCrypt;
import com.example.common.exception.ResultErrException;
import com.example.common.response.ResponseCode;
import com.example.common.response.ResponseModel;
import com.example.user.login.AppLoginVerifier;
import com.example.user.login.domain.User;
import com.example.user.login.domain.UserAgent;
import com.example.user.login.mapper.UserAgentMapper;
import com.example.user.login.mapper.UserMapper;
import com.example.user.login.service.UserService;
import com.example.user.utils.login.IdentityValidator;
import com.example.user.utils.login.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private IdentityValidator identityValidator;

  @Autowired
  private UserAgentMapper userAgentMapper;

  @Autowired
  private UserMapper userMapper;

  @Override
  public ResponseModel login(String userName, String pwd) {

    // 登录
    Principal principal = identityValidator.login(new AppLoginVerifier(userName, pwd));

    return ResponseModel.getModel(ResponseCode.SUCCESS, principal);
  }

  @Override
  public ResponseModel loginOut() {
    // 清除cookie
    identityValidator.logout();
    return ResponseModel.getModel(ResponseCode.SUCCESS);
  }

  @Override
  public ResponseModel register(String userName, String pwd) {

    // 判断用户是否注册
    UserAgent userAgent = userAgentMapper.selectByLoginName(userName);
    if (userAgent != null) {
      throw new ResultErrException("已经注册");
    }

    // 注册用户
    User user = new User();

    UserAgent regUserAgent = new UserAgent();
    regUserAgent.setLoginName(userName);
    String cryptString = BCrypt.hashpw(pwd, BCrypt.gensalt());
    regUserAgent.setLoginPwd(cryptString);
    regUserAgent.setDeleted((short)1);
    regUserAgent.setLoginCount(0);

    user.setUname(userName);

    this.regUser(user, regUserAgent);

    // 完成登录
    ResponseModel responseModel = login(userName, pwd);

    return ResponseModel.getModel(ResponseCode.SUCCESS, responseModel);
  }

  @Override
  public ResponseModel getInfo(String userName) {
    User u = userMapper.findUserByUserName(userName);
    return ResponseModel.getModel(ResponseCode.SUCCESS, u);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public boolean regUser(User user, UserAgent userAgent) {

    int r = userMapper.insertSelective(user);
    userAgent.setUserId(user.getUserId());
    int r1 = userAgentMapper.insertSelective(userAgent);


    boolean flag = (r == 1 && r1 == 1 );

    return flag;

  }
}
