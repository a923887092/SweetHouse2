package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.MallActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.HomePagerContentAdapter;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.HomeProtocol;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/28.
 */
public abstract class BaseFragment extends Fragment {
    private FrameLayout frameLayout;
    private View loadingView;//当界面状态为加载中时显示的界面
    private View errorView;//当界面状态为加载出错时显示的界面
    private View emptyView;//当界面状态为加载后数据为空时的界面
    private View successView;//当界面状态为加载成功后显示的界面
    /*
    状态码
     */
    private static final int STATE_UNKNOW = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    //初始状态
    private int state = STATE_UNKNOW;

    private RelativeLayout rlSearch;
    private Button btnFailed;
    /*
    Fragment创建时调用此方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.pager_home, null);

        rlSearch = (RelativeLayout) view.findViewById(R.id.rl_search);
        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        if (frameLayout == null) {
            frameLayout = (FrameLayout) view.findViewById(R.id.fl_content);
            init();
        } else {
            ViewParent parent = frameLayout.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(frameLayout);
            }
        }
        show();
        return view;
    }
    /*
    一个表示加载数据后界面状态的枚举
     */
    public enum LoadResult {
        error(2),empty(3),success(4);
        int value;
        public int getValue() {
            return value;
        }
        LoadResult(int value) {
            this.value = value;
        }
    }
    /*
       根据加载后界面的状态显示不同状态下该显示的界面
     */
    private void show() {
        if (state == STATE_UNKNOW || state == STATE_ERROR) {
            state = STATE_LOADING;
        }
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {

            @Override
            public void run() {
                SystemClock.sleep(2000);
                final LoadResult result = load();
                System.out.println("子线程："+ result);
                UiUtils.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                        }
                    }
                });
            }
        });
        showPage();
    }
    /**
     * 加载数据，并返回相应的状态码
     * @return LoadResult
     */
    protected abstract LoadResult load();
    /*
    根据不同的状态对FrameLayout上各个界面的显示状态进行设置
     */
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKNOW || state == STATE_LOADING ? View.VISIBLE :
                    View.INVISIBLE);
        }

        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }

        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (state == STATE_SUCCESS){
            if (successView == null) {
                successView = createSuccessView();
                frameLayout.addView(successView,
                        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE:View.INVISIBLE);
            } else {
                successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
    /*
       初始化各个状态下的界面到FrameLayout上
     */
    private void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();
            frameLayout.addView(loadingView,
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (errorView == null) {
            errorView = createErrorView();
            frameLayout.addView(errorView,
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (emptyView == null) {
            emptyView = createEmptyView();
            frameLayout.addView(emptyView,
                    new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
    /**
     * 创建加载成功是的界面 并返回
     * @return View
     */
    protected abstract View createSuccessView();
    /*
       创建数据为空时的界面
     */
    private View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.empty_page, null);
        return view;
    }
    /*
       创建加载失败时的界面
     */
    private View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.error_page, null);
        btnFailed = (Button) view.findViewById(R.id.btn_failed);
        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }
    /*
       创建加载中的界面
     */
    private View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.loading_page, null);
        return view;
    }
}