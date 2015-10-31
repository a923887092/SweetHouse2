package com.gwm.sweethouse.protocol;

import android.os.Environment;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.utils.FilesUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class HomeProtocol extends BaseProtocol<ArrayList<Recommend>> {
    private static final String TAG = "HomeProtocol";
    private String dir;
    public HomeProtocol(String url, String dir) {
        super(url);
        this.dir = dir;
    }

    @Override
    public ArrayList<Recommend> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Recommend> arrayList = gson.fromJson(json, new TypeToken<ArrayList<Recommend>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return dir;
    }
}
