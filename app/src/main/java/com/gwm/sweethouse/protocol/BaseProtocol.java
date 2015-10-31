package com.gwm.sweethouse.protocol;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.http.HttpHelper;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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
import java.net.InterfaceAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public abstract class BaseProtocol<T> {
    private String MURL;
//    private T list = ;

    public BaseProtocol(String url) {
        this.MURL = url;
    }

    /*
         * 加载并解析数据
         * @param index
         */
    public T loadData() {
        String json = loadLocal();
        if (json == null) {
            json = loadServer();
            if (json != null) {
                saveLocal(json);
            }
        }
        /*if (json.equals("")){
            LogUtils.d("isEmpty");
            return null;
        }*/
        if (json != null) {
            System.out.println("json:" + json);
            return paserJson(json);
        } else {
            return null;
        }
    }

    /*
    解析json数据
     */
    public abstract T paserJson(String json);
//

    /*
    缓存数据到本地
     */
    private void saveLocal(String json) {
        BufferedWriter bw = null;
        try {
            File dir = FilesUtils.getCacheDri();
            File file = new File(dir, getTypes());
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis() + 1000 * 60 + "");
            bw.newLine();
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract String getTypes();

    /*
    从服务器得到数据
     */
    private String loadServer() {
        InputStream inputStream = null;
        LogUtils.i("out_loadServer");
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            System.out.println(MURL);
            URL url = new URL(MURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("contentType", "utf-8");
            inputStream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String data1 = "";
            while ((data1 = br.readLine()) != null) {
                sb.append(data1 + "\n");
            }
            LogUtils.i("in_loadServer");
            System.out.println("sb:"+ sb.toString());
            if (TextUtils.isEmpty(sb.toString())){
                LogUtils.d("TextUtils");
                LogUtils.d("eq:" + ("" == null));
                return "";
            } else {
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    从本地缓存得到数据
     */
    private String loadLocal() {
        File dri = FilesUtils.getCacheDri();
        File file = new File(dri, getTypes());
        LogUtils.d("loadLocal");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            Long outOfData = Long.parseLong(br.readLine());
            if (System.currentTimeMillis() > outOfData) {
                return null;
            } else {
                String str;
                StringWriter sw = new StringWriter();
                while ((str = br.readLine()) != null) {
                    sw.write(str);
                }
                System.out.println("++++" + sw.toString());
                return sw.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
