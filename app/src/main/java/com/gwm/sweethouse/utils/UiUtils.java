package com.gwm.sweethouse.utils;

import android.content.Context;
import android.content.res.Resources;

import com.gwm.sweethouse.base.BaseApplication;

/**
 * Created by Administrator on 2015/9/25.
 */
public class UiUtils {

    public static String[] getStringArray(int tab_names) {
        return getResource().getStringArray(tab_names);
    }

    public static Resources getResource(){
        return BaseApplication.getApplication().getResources();
    }

    public static Context getcontext(){
        return BaseApplication.getApplication();
    }

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static void runOnUiThread(Runnable runnable) {
        if (android.os.Process.myTid() == BaseApplication.getMainTid()){
            runnable.run();
        } else {
            BaseApplication.getHandler().post(runnable);
        }
    }
}
