package com.our.smart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.our.smart.bean.post.Login;

/**
 * SharedPreferences的本地存储类
 */
public class LocalKeyUtil {
    private static final String tokenKey = "tokenKey";
    private static final String loginKey = "loginKey";
    private static SharedPreferences preferences;

    private LocalKeyUtil() {
    }

    public static void initSingle(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getToken() {
        return preferences.getString(tokenKey, "");
    }

    public static void setToken(String token) {
        preferences.edit().putString(tokenKey, token).apply();
    }

    public static Login getLogin() {
        String defaultValue = new Gson().toJson(new Login());
        String value = preferences.getString(loginKey, defaultValue);
        return new Gson().fromJson(value, Login.class);
    }

    public static void setLogin(Login login) {
        preferences.edit().putString(loginKey, new Gson().toJson(login)).apply();
    }
}
