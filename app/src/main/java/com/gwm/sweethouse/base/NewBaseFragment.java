package com.gwm.sweethouse.base;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.utils.UiUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/29.
 */
public abstract class NewBaseFragment<T> extends Fragment{
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
    private int mLayoutId;

    public NewBaseFragment(int mLayoutId) {
        this.mLayoutId = mLayoutId;
    }

    private Button btnFailed;



    /*
    Fragment创建时调用此方法
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), mLayoutId, null);
        initFragment(view);
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

    protected abstract void initFragment(View view);

    /*
       根据加载后界面的状态显示不同状态下该显示的界面
     */
    protected void show() {
        if (state == STATE_UNKNOW || state == STATE_ERROR) {
            state = STATE_LOADING;
        }
        showPage();
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, getUrl(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ArrayList<T> list = parseJson(result);
                SystemClock.sleep(2000);
                if (list == null){
                    state = STATE_ERROR;
                } else {
                    if (list.size() == 0){
                        state = STATE_EMPTY;
                    } else {
                        state = STATE_SUCCESS;
                    }
                }
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                state = STATE_ERROR;
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                    }
                });
            }
        });
    }

    protected abstract String getUrl();

    protected abstract ArrayList<T> parseJson(String result);
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
