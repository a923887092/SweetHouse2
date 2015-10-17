package com.gwm.sweethouse.protocol;

import android.os.AsyncTask;
import android.os.Environment;

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
        System.out.println("json == null");

        if (json != null) {
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

    private String json;
    private static class LoadTask extends AsyncTask<String, String, String>{
        DataFinishListener dataFinishListener;

        public void setFinishListener(DataFinishListener dataFinishListener) {
            this.dataFinishListener = dataFinishListener;
        }
        @Override
        protected String doInBackground(String[] params) {
            HttpHelper.HttpResult httpResult = HttpHelper.get(params[0]);
//            json = httpResult.getString();
            LogUtils.d("LoadTask:" + httpResult.getString());
            return httpResult.getString();
        }
//
        @Override
        protected void onPostExecute(String s) {
            if (s != null){
                dataFinishListener.dataFinishSuccessfully(s);
            }
        }

        public interface DataFinishListener {
            void dataFinishSuccessfully(String data);
        }
    }


    /*
    从服务器得到数据
     */
    private String loadServer() {
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.send(HttpRequest.HttpMethod.GET, MURL, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                paserJson(responseInfo.result);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });
        /*final LoadTask loadTask = new LoadTask();
        loadTask.setFinishListener(new LoadTask.DataFinishListener() {
            @Override
            public void dataFinishSuccessfully(String data) {
                json = data;
            }
        });
        loadTask.execute(MURL);
        LogUtils.d("loadServer" + json);
        return json;*/

        InputStream inputStream = null;
        LogUtils.i("out_loadServer");
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
//            String myUrl = new String(MURL.getBytes("ISO-8859-1"), "UTF-8");

//            LogUtils.i("myUrl" + myUrl);
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
            if (inputStream != null) {
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
