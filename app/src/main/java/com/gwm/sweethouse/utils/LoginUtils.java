package com.gwm.sweethouse.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/10/23.
 */
public class LoginUtils {
    public static SharedPreferences preferences;
    public static boolean islogin(Context context){
        preferences =
                context.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preferences == null ){
            return false;
        }else{
            boolean loginState = preferences.getBoolean("loginState", false);
            if(loginState == false){
                return  false;
            }else{
                return true;
            }
        }
    }

    public static int  getUser_id(Context context){
        preferences =
                context.getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preferences != null ){
            int user_id = preferences.getInt("user_id",0);
            return user_id;
        }
        return 0;
    }
}
