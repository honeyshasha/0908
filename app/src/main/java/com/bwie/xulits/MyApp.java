
package com.bwie.xulits;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 小傻瓜 on 2017/8/31.
 */

public class MyApp extends Application {

    private Context appContext;
    public static Context mcontext;
        @Override
        public void onCreate() {
            super.onCreate();
            appContext = getApplicationContext();
            mcontext=this;
            x.Ext.init(this);
            x.Ext.setDebug(true);
            //激光推送
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
}
}
