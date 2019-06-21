package com.example.base.enums;

public enum RecordTypeEnum {
  // 1.充值，2.提现，3.买入，4.卖出，5.买入手续费，6.卖出手续费
  RECHARGE(1, "充值", "RRG"),

  WITHDRAW(2, "提现", "WRA"),

  BUY(3, "买入", "BUY"),

  SELL(4, "卖出", "SELL"),

  BUY_FEE(5, "买入手续费", "BFE"),

  SELL_FEE(6, "卖出手续费", "SFE"),

  FEE_GET(7 , "收入分配", "SFP"),

  FEE_BACK(8, "手续费返还", "SFH"),

  FEE_RABATE(9, "邀请返佣", "RBT"),

  CANCEL(7000, "撤单", "CAL"),;

  private Integer recordType;
  private String recordDesc;
  private String transNo;

  RecordTypeEnum(Integer recordType, String recordDesc, String transNo) {
    this.recordType = recordType;
    this.recordDesc = recordDesc;
    this.transNo = transNo;
  }

  public String getTransNo() {
    return transNo;
  }

  public Integer getRecordType() {
    return recordType;
  }

  public void setRecordType(int recordType) {
    this.recordType = recordType;
  }

  public String getRecordDesc() {
    return recordDesc;
  }

  public void setRecordDesc(String recordDesc) {
    this.recordDesc = recordDesc;
  }
}
