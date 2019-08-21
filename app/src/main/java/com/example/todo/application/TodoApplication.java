package com.example.todo.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.pm.PackageInfoCompat;

import org.apache.commons.lang3.StringUtils;

/**
 * Todoアプリ Application
 */
public class TodoApplication extends Application {
    private static String LOG_TAG = "TodoApplication";

    private static Context sContext;
    private static long sApplicationVersionCode;
    private static String sApplicationVersionName;

    public static Context getContext() {
        return sContext;
    }

    /**
     * バージョン情報を取得・キャッシュする
     */
    private static void initAppVersionInfo() {
        if (sApplicationVersionCode < 1 || StringUtils.isEmpty(sApplicationVersionName)) {
            final PackageManager pm = sContext.getPackageManager();
            try {
                final PackageInfo info = pm.getPackageInfo(
                        sContext.getPackageName(), PackageManager.GET_ACTIVITIES);
                sApplicationVersionCode = PackageInfoCompat.getLongVersionCode(info);
                sApplicationVersionName = info.versionName;
            } catch (Exception e) {
                Log.w(LOG_TAG, e.getMessage());
            }
        }
    }

    /**
     * バージョンコードの取得
     *
     * @return
     */
    public static long getAppVerCode() {
        initAppVersionInfo();
        return sApplicationVersionCode;
    }

    /**
     * バージョン名の取得
     *
     * @return
     */
    public static String getAppVerName() {
        initAppVersionInfo();
        return sApplicationVersionName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TodoApplication.sContext = getApplicationContext();
    }
}
