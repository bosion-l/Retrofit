package com.lv.retrofit;

import android.app.Application;

import com.lv.network.utils.ContextUtils;
import com.lv.network.utils.LLog;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //获取应用context
        ContextUtils.init(this);
        //日志初始化开关
        LLog.init(BuildConfig.DEBUG);
    }
}
