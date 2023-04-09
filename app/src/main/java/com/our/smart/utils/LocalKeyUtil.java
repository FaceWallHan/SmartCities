package com.our.smart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences的本地存储类
 * */
public class LocalKeyUtil {
    private static final String tokenKey="tokenKey";
    private static SharedPreferences preferences;
    private LocalKeyUtil() {}

    public static void initSingle(Context context) {
        preferences= PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static String getToken() {
        return preferences.getString(tokenKey,"");
    }

    public static void setToken(String token) {
        preferences.edit().putString(tokenKey,token).apply();
    }
}
