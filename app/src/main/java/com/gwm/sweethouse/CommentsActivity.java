package com.gwm.sweethouse;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Comment;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.TabIndicatorView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {
    private static final String TAG = "CommentsActivity";
    private TabIndicatorView listHeader1;
    private TabIndicatorView listHeader2;
    private TabIndicatorView listHeader3;
    private TabIndicatorView listHeader4;
    private View view;
    private String url;
    private ArrayList<Comment> allComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initView();
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.configCurrentHttpCacheExpiry(500);
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtils.d(TAG + " getDataFromServer1");
                String result = responseInfo.result;
                LogUtils.d(TAG + " getDataFromServer2");
                Gson gson = new Gson();
                allComments = gson.fromJson(result, new TypeToken<ArrayList<Comment>>() {
                }.getType());
                LogUtils.d(TAG + " getDataFromServer3");
                if (allComments != null) {
//                    commentDivide(allComments);
//                    initTabHostView();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initView() {
        view = View.inflate(this, R.layout.comment_header, null);
        listHeader1 = (TabIndicatorView) view.findViewById(R.id.list_header1);
        listHeader2 = (TabIndicatorView) view.findViewById(R.id.list_header2);
        listHeader3 = (TabIndicatorView) view.findViewById(R.id.list_header3);
        listHeader4 = (TabIndicatorView) view.findViewById(R.id.list_header4);
        listHeader1.setTabTitle("全部评价");
        listHeader2.setTabTitle("好评");
        listHeader3.setTabTitle("中评");
        listHeader4.setTabTitle("差评");
    }
}
