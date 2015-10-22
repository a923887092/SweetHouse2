package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Product;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class DetailsProtocol extends BaseProtocol<ArrayList<Product>> {
    private static final String TAG = "DetailsProtocol";
    private String mCacheDir;
    public DetailsProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<Product> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Product> goods = gson.fromJson(json, new TypeToken<ArrayList<Product>>() {
        }.getType());
        return goods;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
