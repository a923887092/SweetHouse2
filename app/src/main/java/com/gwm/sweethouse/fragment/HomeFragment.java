package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.R;
import com.gwm.sweethouse.SearchActivity;
import com.gwm.sweethouse.adapter.HomePagerContentAdapter;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.HeaderGridView;
import com.gwm.sweethouse.view.RefreshLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/28.
 */
public class HomeFragment extends Fragment {
    private FrameLayout frameLayout;
    private View loadingView;
    private View errorView;
    private View emptyView;
    private CirclePageIndicator mIndicator;

    private static final int[] images = new int[]{R.mipmap.top1, R.mipmap.top2, R.mipmap.top1};

    private static final int STATE_UNKNOW = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    private int state = STATE_UNKNOW;
    private View successView;

    private Handler handler;
    private ViewPager vpTop;
    private RelativeLayout rlSearch;
    private GridViewWithHeaderAndFooter lvRecommend;
    private RefreshLayout mRefreshLayout;

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
        showPage();
        show();
        if (handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int currentItem = vpTop.getCurrentItem();
                    if (currentItem < images.length - 1){
                        currentItem++;
                    } else {
                        currentItem = 0;
                    }
                    vpTop.setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);
        }

        return view;
    }

    private void show() {
        if (state == STATE_UNKNOW || state == STATE_ERROR) {
            state = STATE_LOADING;
        }


        state = STATE_SUCCESS;
        showPage();
    }

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

    private View createSuccessView() {
//        TextView view = new TextView(getActivity());
//        view.setText("成功了");
        View headerView = View.inflate(getActivity(), R.layout.pager_home_content_header, null);
        View view = View.inflate(getActivity(), R.layout.pager_home_content, null);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        lvRecommend = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_recommend);
        lvRecommend.addHeaderView(headerView);
        mRefreshLayout.setChildView(lvRecommend);
        lvRecommend.setAdapter(new GridViewAdapter());
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);


        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("-------------------");
            }
        });
        vpTop = (ViewPager) headerView.findViewById(R.id.vp_top);
        vpTop.setAdapter(new HomePagerContentAdapter(images, getActivity()));
        mIndicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(vpTop);
        System.out.println("成功界面创建了");
        return view;
    }



    private View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.empty_page, null);
        return view;
    }

    private View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.error_page, null);
        return view;
    }

    private View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.loading_page, null);
        return view;
    }


    class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RecommendViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(getActivity(), R.layout.item_list_reconmmend, null);
                holder = new RecommendViewHolder();
                holder.ivRecommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
                holder.tvRecommendTitle = (TextView) convertView.findViewById(R.id.tv_recommend_title);
                holder.tvRecommendPrice = (TextView) convertView.findViewById(R.id.tv_recommend_price);
                convertView.setTag(holder);
            } else {
                holder = (RecommendViewHolder) convertView.getTag();
            }

            holder.tvRecommendTitle.setText("哈哈哈哈哈哈哈哈哈哈");
            holder.tvRecommendPrice.setText("￥ 3456");
            holder.ivRecommend.setImageResource(R.drawable.image1);

            return convertView;
        }
    }

    class RecommendViewHolder{
        TextView tvRecommendTitle, tvRecommendPrice;
        ImageView ivRecommend;
    }
}
