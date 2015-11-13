package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.bean.Saled;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class SaledProtocol extends BaseProtocol<ArrayList<Saled>> {
    private static final String TAG = "SaledProtocol";
    private String dir;
    public SaledProtocol(String url, String dir) {
        super(url);
        this.dir = dir;
    }

    @Override
    public ArrayList<Saled> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Saled> arrayList = gson.fromJson(json, new TypeToken<ArrayList<Saled>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return dir;
    }
}
