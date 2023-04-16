package com.our.smart.bean;

public class LoginResponse extends BaseBean{
    private String token;

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
