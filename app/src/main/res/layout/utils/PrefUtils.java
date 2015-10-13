package layout.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/10/12.
 */
public class PrefUtils {
    public  static final String PRE_NAME="config";
    public static boolean getBoolean(Context ctx,String key,boolean defaultValue) {
        //更新sp--表示已经展示了新手引导
        SharedPreferences sp = ctx.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key,defaultValue);
    }
    public static  void setBoolean(Context ctx,String key,boolean value){
        SharedPreferences sp = ctx.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
