package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.BeautifulPic;
import com.gwm.sweethouse.bean.CodePicDetail;
import com.gwm.sweethouse.bean.Company;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/2.
 */
public class CodePicDetailProtocal extends BaseProtocol<ArrayList<CodePicDetail>> {
    private static final String TAG = "CodePicDetail";
    private String mCache;
    public CodePicDetailProtocal(String url,String mCache) {
        super(url);
        this.mCache = mCache;
    }

    @Override
    public ArrayList<CodePicDetail> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<CodePicDetail> arrayList = gson.fromJson(json, new TypeToken<ArrayList<CodePicDetail>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return mCache;
    }
}
