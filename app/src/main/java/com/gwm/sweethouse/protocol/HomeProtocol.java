package com.gwm.sweethouse.protocol;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.utils.FilesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;

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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12.
 */
public class HomeProtocol {
    private static final String TAG = "HomeProtocol";
    private String MURL;


    public HomeProtocol(String url) {
        this.MURL = url;
    }

    /*
         * 加载并解析数据
         * @param index
         */
    public ArrayList<Recommend> loadData(){
        String json = loadLocal();
        if (json == null){
            json = loadServer();
            if (json != null){
                saveLocal(json);
            }
        }

        if (json != null){
            return paserJson(json);
        } else {
            return null;
        }
    }

    /*
    解析json数据
     */
    private ArrayList<Recommend> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Recommend> arrayList = gson.fromJson(json, new TypeToken<ArrayList<Recommend>>(){}.getType());
        return arrayList;
    }

    /*
    缓存数据到本地
     */
    private void saveLocal(String json) {
        BufferedWriter bw = null;
        try {
            File file = new File("cache", "home_recommend");
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(System.currentTimeMillis() + 1000* 60 + "");
            bw.newLine();
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    从服务器得到数据
     */
    private String loadServer() {
        /*HttpUtils httpUtils = new HttpUtils();
        final String[] mResult = new String[1];
        httpUtils.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET, MURL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String string = responseInfo.toString();
                        String result = responseInfo.result;
                        if (result != null){
                            mResult[0] =responseInfo.result;
                        }
                        System.out.println("HomeProtocol:" + string + "---" + mResult[0]);
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                        System.out.println("---------------!!!!!!!");
                    }
                });*/
        InputStream inputStream = null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            System.out.println(MURL);
            URL url = new URL(MURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            inputStream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,  "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String data1 = "";
            while ((data1 = br.readLine()) != null) {
                sb.append(data1+"\n");
            }
            System.out.println(sb.toString());
            return sb.toString();
//            while ((len = inputStream.read(data)) != -1){
//                outputStream.write(data, 0, len);
//            }
//            System.out.println("++++++" + new String(outputStream.toByteArray()));
//            String content = new String(outputStream.toByteArray());
//            return URLDecoder.decode(content, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        Log.d(TAG + ":loadServer", mResult[0]);
    }

    /*
    从本地缓存得到数据
     */
    private String loadLocal() {
        File dri = FilesUtils.getCacheDri();
        File file = new File(dri, "home_recommend");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            Long outOfData = Long.parseLong(br.readLine());
            if (System.currentTimeMillis() > outOfData){
                return null;
            } else {
                String str;
                StringWriter sw = new StringWriter();
                while((str = br.readLine()) != null){
                    sw.write(str);
                }

                return sw.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
