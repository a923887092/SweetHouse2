package com.gwm.sweethouse.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Product;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CartGoodsUtils {
    private static Gson gson = new Gson();
    public static ArrayList<Product> getProduct(Context context){
        String cart_goods = PrefUtils.getString(context, "cart_goods", "");
        ArrayList<Product> goods = gson.fromJson(cart_goods, new TypeToken<ArrayList<Product>>() {
        }.getType());
        return goods;
    }

    public static void addProduct(Context context) {

    }
}
