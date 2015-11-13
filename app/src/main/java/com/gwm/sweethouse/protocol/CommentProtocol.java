package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Comment;
import com.gwm.sweethouse.bean.Product;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class CommentProtocol extends BaseProtocol<ArrayList<Comment>> {
    private static final String TAG = "DetailsProtocol";
    private String mCacheDir;
    public CommentProtocol(String url, String cacheDir) {
        super(url);
        this.mCacheDir = cacheDir;
    }

    @Override
    public ArrayList<Comment> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Comment> comments = gson.fromJson(json, new TypeToken<ArrayList<Comment>>() {
        }.getType());
        return comments;
    }

    @Override
    protected String getTypes() {
        return mCacheDir;
    }
}
