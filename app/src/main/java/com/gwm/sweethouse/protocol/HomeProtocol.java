package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.gwm.sweethouse.utils.FilesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2015/10/12.
 */
public class HomeProtocol {
    private String MURL = "";

    /*
         *加载并解析数据
         * @param index
         */
    public void loadData(int index){
        String json = loadLocal(index);
        if (json == null){
            json = loadServer(index);
            if (json != null){
                saveLocal(json, index);
            }
        }

        if (json != null){
            paserJson(json);
        }
    }

    /*
    解析json数据
     */
    private void paserJson(String json) {
        Gson gson = new Gson();
//        gson.fromJson(json, )
    }

    /*
    缓存数据到本地
     */
    private void saveLocal(String json, int index) {
        BufferedWriter bw = null;
        try {
            File file = new File("cache", "home_" + index);
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
    private String loadServer(int index) {
        HttpUtils httpUtils = new HttpUtils();
        final String[] mResult = new String[1];
        httpUtils.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET, MURL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        mResult[0] = responseInfo.result;
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                        mResult[0] = null;
                    }
                });
        return mResult[0];
    }

    /*
    从本地缓存得到数据
     */
    private String loadLocal(int index) {
        File dri = FilesUtils.getCacheDri();
        File file = new File(dri, "home_" + index);
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
