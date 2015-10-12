package com.gwm.sweethouse.protocol;

import android.os.Environment;

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
public class HomeProtocol {
    private static final String TAG = "HomeProtocol";
    private String MURL;


    public HomeProtocol(String url) {
        String ab = Environment.getExternalStorageDirectory().toString();
        System.out.println("============" + ab);
        this.MURL = url;
    }

    /*
         * 加载并解析数据
         * @param index
         */
    public ArrayList<Recommend> loadData(){
        String json = loadLocal();
        if (json == null){
            System.out.println("json == null");
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
            File dir = FilesUtils.getCacheDri();
            File file = new File(dir, "home_recommend");
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
                System.out.println("++++" + sw.toString());
                return sw.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
