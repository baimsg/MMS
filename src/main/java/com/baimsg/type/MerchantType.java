package com.baimsg.type;

public enum MerchantType {

    Release("上架"), Revoke("下架"), Revise("修改");

    private String msg;

    MerchantType(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
