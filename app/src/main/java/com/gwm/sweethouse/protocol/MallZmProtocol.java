package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.bean.SubClass;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class MallZmProtocol extends BaseProtocol<ArrayList<SubClass>> {
    private static final String TAG = "MallZmProtocol";
    private String mCacheDir;
    public MallZmProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<SubClass> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<SubClass> arrayList = gson.fromJson(json, new TypeToken<ArrayList<SubClass>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
