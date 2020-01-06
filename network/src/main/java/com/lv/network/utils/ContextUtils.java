package com.lv.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @Author lvzhou
 * @Date 2019/11/6
 * @describe context（需要在application里初始化）
 */
public final class ContextUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        ContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }
}