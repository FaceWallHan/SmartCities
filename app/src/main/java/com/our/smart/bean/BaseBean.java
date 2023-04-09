package com.our.smart.bean;

import androidx.annotation.NonNull;

public class BaseBean {
    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
