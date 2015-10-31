package com.gwm.sweethouse.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.gwm.sweethouse.R;
import com.lidroid.xutils.BitmapUtils;


/**
 * Created by Administrator on 2015/9/25.
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;

    public static BitmapUtils bitmapUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();
        bitmapUtils = new BitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_loading);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_failed);
    }

    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
