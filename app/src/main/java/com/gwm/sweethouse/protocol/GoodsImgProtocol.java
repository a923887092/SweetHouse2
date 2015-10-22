package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.ProductImg;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class GoodsImgProtocol extends BaseProtocol<ArrayList<ProductImg>> {
    private static final String TAG = "GoodsImgProtocol";
    private String mCacheDir;
    public GoodsImgProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<ProductImg> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<ProductImg> goodImgs = gson.fromJson(json, new TypeToken<ArrayList<ProductImg>>() {
        }.getType());
        return goodImgs;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
