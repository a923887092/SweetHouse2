package com.gwm.sweethouse.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/9/28.
 */
public class FilesUtils {
    private static final String ROOT = "/GooglePlay";//缓存的根路径
    private static final String CACHE = "cache";//数据缓存路径
    private static final String ICON = "icon";//图片缓存路径

    /**
     * 得到相对应的路径
     * @param str
     * @return
     */
    public static File getDris(String str){
        StringBuilder sb = new StringBuilder();
        if (isSdAvailable()){
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append(File.separator);
            sb.append(ROOT);
            sb.append(File.separator);
            sb.append(str);
        } else {
            File cacheDir = UiUtils.getcontext().getCacheDir();
            sb.append(cacheDir.getAbsolutePath());
            sb.append(File.separator);
            sb.append(str);
        }
        File file = new File(sb.toString());
        if (!file.exists() || !file.isDirectory()){
            file.mkdirs();
        }

        return file;
    }

    /*
    判断SD卡是否挂载（存在）
     */
    private static boolean isSdAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取数据缓存路径
     * @return
     */
    public static File getCacheDri(){
        return getDris(CACHE);
    }

    /**
     * 获取图片缓存路径
     * @return
     */
    public static File getIconDri(){
        return getDris(ICON);
    }
}
