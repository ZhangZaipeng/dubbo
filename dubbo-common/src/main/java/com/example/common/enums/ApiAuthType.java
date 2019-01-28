package com.example.common.enums;

/**
 * Created by ZhangZP on 2018/7/19.
 * API 权限枚举
 */
public enum ApiAuthType {

    QueryAsset(1,"资产查询"),

    EntrustTrade(2,"委托交易"),

    RechargeWithdraw(3, "充值提现")
    ;

    // ID
    private Integer authId;
    // 描述
    private String remark;

    ApiAuthType(Integer authId, String remark) {
        this.authId = authId;
        this.remark = remark;
    }

    public static Integer getAuthId(ApiAuthType apiAuthType) {
        if (ApiAuthType.QueryAsset == apiAuthType) {
            return ApiAuthType.QueryAsset.authId;
        } else if (ApiAuthType.EntrustTrade == apiAuthType) {
            return ApiAuthType.EntrustTrade.authId;
        } else if (ApiAuthType.RechargeWithdraw == apiAuthType) {
            return ApiAuthType.RechargeWithdraw.authId;
        }
        throw new RuntimeException("");
    }

    public static String getRemark(ApiAuthType apiAuthType) {
        if (ApiAuthType.QueryAsset == apiAuthType) {
            return ApiAuthType.QueryAsset.remark;
        } else if (ApiAuthType.EntrustTrade == apiAuthType) {
            return ApiAuthType.EntrustTrade.remark;
        } else if (ApiAuthType.RechargeWithdraw == apiAuthType) {
            return ApiAuthType.RechargeWithdraw.remark;
        }
        throw new RuntimeException("");
    }

    public static String getRemark(Integer authId) {
        if (ApiAuthType.QueryAsset.authId == authId) {
            return ApiAuthType.QueryAsset.remark;
        } else if (ApiAuthType.EntrustTrade.authId == authId) {
            return ApiAuthType.EntrustTrade.remark;
        } else if (ApiAuthType.RechargeWithdraw.authId == authId) {
            return ApiAuthType.RechargeWithdraw.remark;
        }
        throw new RuntimeException("");
    }

    public static int[] getAuthIds() {
        ApiAuthType [] apiAuthTypes = ApiAuthType.values();
        if (apiAuthTypes.length <= 0) {
            return null ;
        }

        int[] authIds = new int[apiAuthTypes.length];
        for (int i = 0; i < ApiAuthType.values().length; i++) {
            authIds[i] = ApiAuthType.values()[i].authId;
        }

        return authIds;

    }


    public static void main(String[] args) {
        /*for (ApiAuthType apiAuthType :ApiAuthType.values()){
            System.out.println(apiAuthType.remark);
        }*/
        System.out.println(getAuthIds().length);
    }
}

