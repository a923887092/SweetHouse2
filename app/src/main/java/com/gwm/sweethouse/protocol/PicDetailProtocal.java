package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.bean.Company;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/2.
 */
public class PicDetailProtocal extends BaseProtocol<ArrayList<BeautifulPic>> {
    private static final String TAG = "PicDetailProtocal";
    private String mCache;
    public PicDetailProtocal(String url,String mCache) {
        super(url);
        this.mCache = mCache;
    }

    @Override
    public ArrayList<BeautifulPic> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<BeautifulPic> arrayList = gson.fromJson(json, new TypeToken<ArrayList<BeautifulPic>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCache;
    }
}
