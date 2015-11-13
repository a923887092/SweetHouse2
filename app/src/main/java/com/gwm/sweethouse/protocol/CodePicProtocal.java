package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.bean.CodePic;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/3.
 */
public class CodePicProtocal extends BaseProtocol<ArrayList<CodePic>> {
    private static final String TAG = "CodePicProtocal";
    public CodePicProtocal(String url) {
        super(url);
    }

    @Override
    public ArrayList<CodePic> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<CodePic> arrayList = gson.fromJson(json, new TypeToken<ArrayList<CodePic>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return "CodePicProtocal";
    }
}
