package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.ComDetail;
import com.gwm.sweethouse.bean.CompanyPic;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CompanyPicProtocal  extends BaseProtocol<ArrayList<CompanyPic>>{
    private static final String TAG = "DetailProtocol";
    private String mCache;
    public CompanyPicProtocal(String url, String s) {
        super(url);
        this.mCache = s;
    }

    @Override
    public ArrayList<CompanyPic> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<CompanyPic> arrayList = gson.fromJson(json, new TypeToken<ArrayList<CompanyPic>>() {
        }.getType());

        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCache;
    }
}
