package com.our.smart.net;

public  interface NetStateListener<T> {

    void onSuccess( T response);

    void onFailure(Exception e);
}
