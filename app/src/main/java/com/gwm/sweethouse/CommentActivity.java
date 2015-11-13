package com.gwm.sweethouse;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Comment;
import com.gwm.sweethouse.fragment.comment.AllsFragment;
import com.gwm.sweethouse.fragment.comment.BadFragment;
import com.gwm.sweethouse.fragment.comment.GoodFragment;
import com.gwm.sweethouse.fragment.comment.MiddleFragment;
import com.gwm.sweethouse.fragment.comment.OtherFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.view.TabIndicatorView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class CommentActivity extends FragmentActivity implements TabHost.OnTabChangeListener {


    private static final String TAB_OTHER = "other_comments";
    private static final String TAB_ALL = "all_comments";
    private static final String TAB_GOOD = "good_comments";
    private static final String TAB_MIDDLE = "middle_comments";
    private static final String TAB_BAD = "bad_comments";
    private static final String TAG = "CommentActivity";
    private FragmentTabHost tabhost;
    public TabIndicatorView otherIndicator;
    public TabIndicatorView allIndicator;
    private TabIndicatorView badIndicator;
    private TabIndicatorView goodIndicator;
    private TabIndicatorView middleIndicator;
    private ImageView ivBack;
    private int productId;

    private ArrayList<Comment> allComments = new ArrayList<>();
    private ArrayList<Comment> goodComments = new ArrayList<>();
    private ArrayList<Comment> middleComments = new ArrayList<>();
    private ArrayList<Comment> badComments = new ArrayList<>();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        productId = getIntent().getIntExtra("productId", 0);
        url = GlobalContacts.COMMENT_URL + productId;
        LogUtils.d(TAG + "onCreate:" + productId);
        initView();
        LogUtils.d(TAG + "onCreate:initView");
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
                    commentDivide(allComments);
                    initTabHostView();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void initTabHostView() {
        Bundle bundle = new Bundle();
        tabhost.clearAllTabs();
        LogUtils.d(TAG + "initTabHostView");
        //2新建tabSpec
        TabHost.TabSpec spec = tabhost.newTabSpec(TAB_ALL);
        allIndicator = new TabIndicatorView(this);
        allIndicator.setTabTitle("全部评价");
        allIndicator.setTabNum(String.valueOf(allComments.size()));
        spec.setIndicator(allIndicator);
        bundle.putSerializable("allComments", allComments);
        //3添加TabSpec
        tabhost.addTab(spec, AllsFragment.class, bundle);

        //2新建tabSpec
        TabHost.TabSpec spec1 = tabhost.newTabSpec(TAB_GOOD);
        goodIndicator = new TabIndicatorView(this);
        goodIndicator.setTabTitle("好评");
        goodIndicator.setTabNum(String.valueOf(goodComments.size()));
        spec1.setIndicator(goodIndicator);
        bundle.putSerializable("goodComments", goodComments);
        //3添加TabSpec
        tabhost.addTab(spec1, GoodFragment.class, bundle);

        //2新建tabSpec
        TabHost.TabSpec spec2 = tabhost.newTabSpec(TAB_MIDDLE);
        middleIndicator = new TabIndicatorView(this);
        middleIndicator.setTabTitle("中评");
        middleIndicator.setTabNum(String.valueOf(middleComments.size()));
        spec2.setIndicator(middleIndicator);
        bundle.putSerializable("middleComments", middleComments);
        //3添加TabSpec
        tabhost.addTab(spec2, MiddleFragment.class, bundle);

        //2新建tabSpec
        TabHost.TabSpec spec3 = tabhost.newTabSpec(TAB_BAD);
        badIndicator = new TabIndicatorView(this);
        badIndicator.setTabTitle("差评");
        badIndicator.setTabNum(String.valueOf(badComments.size()));
        spec3.setIndicator(badIndicator);
        bundle.putSerializable("badComments", badComments);
        //3添加TabSpec
        tabhost.addTab(spec3, BadFragment.class, bundle);

        // 去掉分割线
        tabhost.getTabWidget().setDividerDrawable(android.R.color.white);
        // 初始化 tab选中
        tabhost.setCurrentTabByTag(TAB_ALL);
        allIndicator.setTabSelected(true);

        tabhost.setOnTabChangedListener(this);

    }

    private void commentDivide(ArrayList<Comment> allComments) {
        for (Comment comment : allComments) {
            float grade = comment.getComment_grade();
            if (grade >= 3.5 && grade <= 5.0) {
                goodComments.add(comment);
            } else if (grade >= 2 && grade < 3.5) {
                middleComments.add(comment);
            } else if (grade >= 0 && grade < 2.5) {
                badComments.add(comment);
            }
        }

        LogUtils.d(TAG + "commentDivide：" + allComments.size() + "::" + goodComments.size() +
        "::" + middleComments.size() + "::" + badComments.size());
    }


    private void initView() {
        LogUtils.d(TAG+ " initView");
        //1初始化TabHost
        tabhost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.activity_home_container);

        //2新建tabSpec
        TabHost.TabSpec spec = tabhost.newTabSpec(TAB_OTHER);
        otherIndicator = new TabIndicatorView(this);
        otherIndicator.setTabTitle("评价");
        otherIndicator.setTabNum("11");
        spec.setIndicator(otherIndicator);
        //3添加TabSpec
        tabhost.addTab(spec, OtherFragment.class, null);
        LogUtils.d(TAG + "initView tabhost");
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onTabChanged(String tabId) {
        allIndicator.setTabSelected(false);
        goodIndicator.setTabSelected(false);
        middleIndicator.setTabSelected(false);
        badIndicator.setTabSelected(false);

        if (TAB_ALL.equals(tabId)) {
            allIndicator.setTabSelected(true);
        } else if (TAB_GOOD.equals(tabId)) {
            goodIndicator.setTabSelected(true);
        } else if (TAB_MIDDLE.equals(tabId)) {
            middleIndicator.setTabSelected(true);
        } else if (TAB_BAD.equals(tabId)) {
            badIndicator.setTabSelected(true);
        }
    }
}
