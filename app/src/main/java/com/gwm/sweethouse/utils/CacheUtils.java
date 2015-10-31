package com.gwm.sweethouse.utils;

import android.content.Context;

/**
 * Created by Administrator on 2015/10/23.
 */
public class CacheUtils {
    /*
    * 设置缓存 其key是URL 其value是JSON数据
    * */
    public static void setCache(String key,String value,Context context){
        PrefUtils.setString(context,key,value);
    }

    /*读取缓存  其key是URL
    * */
    public  static  String getCache(String key,Context context){
        return PrefUtils.getString(context,key,null);
    }
}
