package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.SubClass;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class GoodsProtocol extends BaseProtocol<ArrayList<Product>> {
    private static final String TAG = "GoodsProtocol";
    private String mCacheDir;
    public GoodsProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<Product> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Product> arrayList = gson.fromJson(json, new TypeToken<ArrayList<Product>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
