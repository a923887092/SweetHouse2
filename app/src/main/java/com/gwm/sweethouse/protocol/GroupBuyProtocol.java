package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.MyActivity;
import com.gwm.sweethouse.bean.Product;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class GroupBuyProtocol extends BaseProtocol<ArrayList<MyActivity>> {
    private static final String TAG = "GroupBuyProtocol";
    private String mCacheDir;
    public GroupBuyProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<MyActivity> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<MyActivity> arrayList = gson.fromJson(json, new TypeToken<ArrayList<MyActivity>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
