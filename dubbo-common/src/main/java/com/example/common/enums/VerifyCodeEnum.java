package com.example.common.enums;

public enum VerifyCodeEnum {

  // 1000 注册验证码,1001 注册图片验证码
  register(1000, "注册操作", "VCR"),
  registerPic(1001, "注册图片验证码", "VCRP"),

  //2000  找回验证码,2001 找回图片验证码
  find(2000, "找回操作", "VCR"),
  findPic(2001, "找回图片验证码", "VCRP"),

  //3000 绑定(认证)验证码
  bind(3000, "认证操作", "VCB"),

  //4000 修改验证码, 4001 修改图片验证码
  modifier(4000,"修改操作","VCM"),
  modifierPic(4001,"修改验证码","VCMP"),

  //5000 提币验证码
  withDraw(5000,"提币操作","VCW"),

  //6000 添加地址验证码
  addAddress(6000,"添加地址操作","VCA"),

  //7000 创建api密钥验证码
  createApiKey(7000,"创建api密钥操作","VCC"),

  //8000 修改钱包验证码
  updateWallet(8000,"修改钱包操作","VWA"),

  //8001 后台管理系统充值验证码
  recharge(8001,"后台管理系统充值操作","REC"),

  //8001 后台管理系统充值验证码
  adminLogin(8002,"后台管理系统登录操作","LOG"),

  //9000 修改管理系统账号
  updateAdmin(9000,"修改管理系统账号操作","ADM")
  ;

  private Integer codeType;
  private String codeName;
  private String codeKey;

  VerifyCodeEnum(Integer codeType, String codeName, String codeKey) {
    this.codeType = codeType;
    this.codeName = codeName;
    this.codeKey = codeKey;
  }

  public static String getCodeKey(Integer codeType){
    if (codeType == 1000) {
      return VerifyCodeEnum.register.codeKey;
    } else if (codeType == 1001) {
      return VerifyCodeEnum.registerPic.codeKey;
    } else if (codeType == 2000) {
      return VerifyCodeEnum.find.codeKey;
    } else if (codeType == 2001) {
      return VerifyCodeEnum.findPic.codeKey;
    } else if (codeType == 3000) {
      return VerifyCodeEnum.bind.codeKey;
    } else if (codeType == 4000) {
      return VerifyCodeEnum.modifier.codeKey;
    } else if (codeType == 4001) {
      return VerifyCodeEnum.modifierPic.codeKey;
    } else if (codeType == 5000) {
      return VerifyCodeEnum.withDraw.codeKey;
    } else if (codeType == 6000) {
      return VerifyCodeEnum.addAddress.codeKey;
    } else if (codeType == 7000) {
      return VerifyCodeEnum.createApiKey.codeKey;
    } else if (codeType == 8000) {
      return VerifyCodeEnum.updateWallet.codeKey;
    }else if (codeType == 8001) {
      return VerifyCodeEnum.recharge.codeKey;
    }else if (codeType == 8002) {
      return VerifyCodeEnum.adminLogin.codeKey;
    } else if (codeType == 9000) {
      return VerifyCodeEnum.updateAdmin.codeKey;
    } else {
      throw new RuntimeException("不支持的业务类型");
    }
  }

  public static String getCodeName(Integer codeType){
    if (codeType == 1000) {
      return VerifyCodeEnum.register.codeName;
    } else if (codeType == 1001) {
      return VerifyCodeEnum.registerPic.codeName;
    } else if (codeType == 2000) {
      return VerifyCodeEnum.find.codeName;
    } else if (codeType == 2001) {
      return VerifyCodeEnum.findPic.codeName;
    } else if (codeType == 3000) {
      return VerifyCodeEnum.bind.codeName;
    } else if (codeType == 4000) {
      return VerifyCodeEnum.modifier.codeName;
    } else if (codeType == 4001) {
      return VerifyCodeEnum.modifierPic.codeName;
    } else if (codeType == 5000) {
      return VerifyCodeEnum.withDraw.codeName;
    } else if (codeType == 6000) {
      return VerifyCodeEnum.addAddress.codeName;
    } else if (codeType == 7000) {
      return VerifyCodeEnum.createApiKey.codeName;
    } else if (codeType == 8000) {
      return VerifyCodeEnum.updateWallet.codeName;
    } else if (codeType == 8001) {
      return VerifyCodeEnum.recharge.codeName;
    } else if (codeType == 8002) {
      return VerifyCodeEnum.adminLogin.codeName;
    } else if (codeType == 9000) {
      return VerifyCodeEnum.updateAdmin.codeName;
    } else {
      throw new RuntimeException("不支持的业务类型");
    }
  }

}
