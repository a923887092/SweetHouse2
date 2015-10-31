package com.gwm.sweethouse.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/10/23.
 */
public class LoginUtils {

    public  static boolean islogin(Context context){
        SharedPreferences preferences =
                context.getSharedPreferences("login", context.MODE_PRIVATE);
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
}
