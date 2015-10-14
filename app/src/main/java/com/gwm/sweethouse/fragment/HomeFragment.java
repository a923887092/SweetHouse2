package com.gwm.sweethouse.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.MallActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.HomePagerContentAdapter;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Recommend;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.protocol.HomeProtocol;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/28.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final int[] images = new int[]{R.mipmap.top1, R.mipmap.top2, R.mipmap.top1};
    private Handler handler;


    private CirclePageIndicator mIndicator;
    private ViewPager vpTop;
    private GridViewWithHeaderAndFooter lvRecommend;
    private RefreshLayout mRefreshLayout;
    private TextView textMore;
    private ProgressBar mProgressBar;
    private RadioButton btnMall;
    private static ArrayList<Recommend> recommends;

    public LoadResult load() {
        HomeProtocol protocol = new HomeProtocol(GlobalContacts.RECOMMEND_URL);
        recommends = new ArrayList<>();
        recommends = protocol.loadData();
        if (recommends == null){
            return LoadResult.error;
        } else {
            if (recommends.size() == 0){
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }
    public View createSuccessView() {
        View headerView = View.inflate(getActivity(), R.layout.pager_home_content_header, null);
        View view = View.inflate(getActivity(), R.layout.pager_home_content, null);
        View footerView = View.inflate(getActivity(), R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);
        btnMall = (RadioButton) headerView.findViewById(R.id.shangcheng);
        btnMall.setOnClickListener(this);
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

        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        lvRecommend = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_recommend);
        lvRecommend.addHeaderView(headerView);
        lvRecommend.addFooterView(footerView);
        mRefreshLayout.setChildView(lvRecommend);
//        if (recommends.size() != 0){
            lvRecommend.setAdapter(new GridViewAdapter());
//        }
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
//                        mAdapter.notifyDataSetChanged();
                        textMore.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Refresh Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                textMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        getNewBottomData();
                        mRefreshLayout.setLoading(false);
//                        mAdapter.notifyDataSetChanged();
                        textMore.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Load Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        vpTop = (ViewPager) headerView.findViewById(R.id.vp_top);
        vpTop.setAdapter(new HomePagerContentAdapter(images, getActivity()));
        mIndicator = (CirclePageIndicator)headerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(vpTop);
        System.out.println("成功界面创建了");
        return view;
    }
    class GridViewAdapter extends MyBaseAdapter<Recommend> {
        BitmapUtils utils;
        public GridViewAdapter() {
            super(recommends);
            utils = new BitmapUtils(getActivity());
            utils.configDefaultLoadingImage(R.drawable.image1);
        }
        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
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
            Recommend recommend = recommends.get(position);
            utils.display(holder.ivRecommend, GlobalContacts.SERVER_URL + recommend.getProduct_photo());
            holder.tvRecommendTitle.setText(recommend.getProduct_name() + "[" +
                    recommend.getProduct_desc() + "]");
            holder.tvRecommendPrice.setText("￥ " + recommend.getProduct_price());
            return convertView;
        }
    }
    class RecommendViewHolder{
        TextView tvRecommendTitle, tvRecommendPrice;
        ImageView ivRecommend;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shangcheng:
                startActivity(new Intent(getActivity(), MallActivity.class));
                break;
        }
    }
}
