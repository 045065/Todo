package com.example.todo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.todo.application.TodoApplication;

/**
 * SharedPreferenceインスタンス取得に関するUtils
 */
public class SPrefUtils {
    private static final String PREF_NAME = "TodoPreference";
    public static final String LOGIN_MAIL = "LOGIN_MAIL";
    public static final String LOGIN_PASS = "LOGIN_PASS";

    public static SharedPreferences getSPref() {
        return TodoApplication.getContext().getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor() {
        return getSPref().edit();
    }
}