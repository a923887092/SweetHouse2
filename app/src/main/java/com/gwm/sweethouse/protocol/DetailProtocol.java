package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.ComDetail;
import com.gwm.sweethouse.bean.Company;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/27.
 */
public class DetailProtocol extends BaseProtocol<ArrayList<ComDetail>> {
    private static final String TAG = "DetailProtocol";
    private String mCache;
    public DetailProtocol(String url, String mCache) {
        super(url);
        this.mCache = mCache;
    }

    @Override
    public ArrayList<ComDetail> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<ComDetail> arrayList = gson.fromJson(json, new TypeToken<ArrayList<ComDetail>>() {
        }.getType());

        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCache;
    }
}
