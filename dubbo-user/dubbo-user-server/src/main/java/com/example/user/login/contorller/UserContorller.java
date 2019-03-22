package com.example.user.login.contorller;

import com.example.common.response.ResponseModel;
import com.example.platform.platform.HttpParameterParser;
import com.example.user.login.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value="/login", description="用户登录", position = 2)
public class UserContorller {

  @Autowired
  private UserService userService;

  @ApiOperation(value = "登录", httpMethod = "POST")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "userName",
          required = true, paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "pwd", value = "pwd",
          required = true, paramType = "query", dataType = "string"),
  })
  @RequestMapping(value = "/login.json", method = RequestMethod.POST)
  public ResponseModel login(HttpServletRequest request) {
    HttpParameterParser httpParameterParser = HttpParameterParser.newInstance(request);

    String userName = httpParameterParser.getString("userName");
    String pwd = httpParameterParser.getString("pwd");
    return userService.login(userName, pwd);
  }

  @ApiOperation(value = "注册", httpMethod = "POST")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "userName",
          required = true, paramType = "query", dataType = "string"),
      @ApiImplicitParam(name = "pwd", value = "pwd",
          required = true, paramType = "query", dataType = "string"),
  })
  @RequestMapping(value = "/register.json", method = RequestMethod.POST)
  public ResponseModel register(HttpServletRequest request) {
    HttpParameterParser httpParameterParser = HttpParameterParser.newInstance(request);

    String userName = httpParameterParser.getString("userName");
    String pwd = httpParameterParser.getString("pwd");
    return userService.register(userName, pwd);
  }

  @ApiOperation(value = "注册", httpMethod = "POST")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userName", value = "userName",
          required = true, paramType = "query", dataType = "string"),
  })
  @RequestMapping(value = "/getInfo.json", method = RequestMethod.POST)
  public ResponseModel getInfo(HttpServletRequest request) {
    HttpParameterParser httpParameterParser = HttpParameterParser.newInstance(request);
    String userName = httpParameterParser.getString("userName");
    return userService.getInfo(userName);
  }

}
