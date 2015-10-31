package com.gwm.sweethouse.fragment.groupbuy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.ActDetailsActivity;
import com.gwm.sweethouse.GroupBuyActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.MyActivity;
import com.gwm.sweethouse.fragment.BaseFragment;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.interfaces.FragmentCallBackString;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.GroupBuyProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/22.
 */
public class GroupBuyFragment extends BaseFragment {

    private ImageView ivBack;
    private FragmentCallBackString callBack;
    private ArrayList<MyActivity> myActivities;
    private GridViewWithHeaderAndFooter gvAct;
    private RefreshLayout mRefreshLayout;
    private TextView textMore;
    private ProgressBar mProgressBar;
    private ActGridViewAdapter mAdapter;
    private int pageNo = 1;
    private String dirTime, onloadDir;
    private String area;

    public GroupBuyFragment() {
        super(R.layout.pager_group_buy);
    }

    @Override
    protected void initFragment(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_goods_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (GroupBuyActivity)activity;
    }

    @Override
    protected LoadResult load() {
        area = callBack.callback(null);
        GroupBuyProtocol protocol = new GroupBuyProtocol(GlobalContacts.ACTIVITY_URL + Uri.encode(area) + "&pageNo=" + pageNo,"activities_info");
        myActivities = new ArrayList<>();
        myActivities = protocol.loadData();
        if (myActivities == null){
            return LoadResult.error;
        } else {
            if (myActivities.size() == 0){
                return LoadResult.empty;
            } else {
                return LoadResult.success;
            }
        }
    }

    @Override
    protected View createSuccessView() {
        mAdapter = new ActGridViewAdapter(myActivities, getActivity());
        View view = View.inflate(getActivity(), R.layout.fragment_group_buy, null);
        View footerView = View.inflate(getActivity(), R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        gvAct = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_act);
        gvAct.addFooterView(footerView);
        mRefreshLayout.setChildView(gvAct);
//        if (recommends.size() != 0){
        gvAct.setAdapter(mAdapter);

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
                textMore.setText("上拉加载更多");
                mRefreshLayout.setLoading(false);
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        pageNo = 1;
                        final String dirTime1 = System.currentTimeMillis() + "";
                        GroupBuyProtocol protocol = new GroupBuyProtocol(GlobalContacts.ACTIVITY_URL + Uri.encode(area) + "&pageNo=" + pageNo, dirTime1);
                        final ArrayList<MyActivity> activities_refresh = protocol.loadData();
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (activities_refresh != null) {
                                    File dir = FilesUtils.getCacheDri();
                                    if (dirTime != null) {
                                        File file = new File(dir, dirTime);
                                        if (file.isFile() && file.exists()) {
                                            file.delete();
                                        }
                                        dirTime = dirTime1;
                                    }
                                    myActivities.clear();
                                    myActivities.addAll(activities_refresh);
                                    LogUtils.d("aaaaa" + myActivities);
                                    mAdapter.notifyDataSetChanged();
                                }
                                mRefreshLayout.setRefreshing(false);

                                textMore.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        //使用自定义的RefreshLayout加载更多监听
        //use customed RefreshLayout OnLoadListener
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                LogUtils.d("上拉啦！！！！！");
                textMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                ThreadManager.getInstance().createLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        final String dirTime1 = System.currentTimeMillis() + "";
                        int i = myActivities.size() / (pageNo * GlobalContacts.PAGE_SIZE);
                        ArrayList<MyActivity> activities_onload = null;
                        if (i == 1) {
                            pageNo++;
                            GroupBuyProtocol protocol = new GroupBuyProtocol(GlobalContacts.ACTIVITY_URL + Uri.encode(area) + "&pageNo=" + pageNo, dirTime1);
                            activities_onload = protocol.loadData();
                        }
                        final ArrayList<MyActivity> finalActivities_onload = activities_onload;
                        UiUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (finalActivities_onload == null) {
                                    textMore.setText("已加载全部数据");
                                    textMore.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                } else {
                                    if (finalActivities_onload.size() != 0) {
                                        File dir = FilesUtils.getCacheDri();
                                        if (onloadDir != null) {
                                            File file = new File(dir, onloadDir);
                                            if (file.isFile() && file.exists()) {
                                                file.delete();
                                            }
                                            onloadDir = dirTime1;
                                        }
                                        myActivities.addAll(finalActivities_onload);
                                        LogUtils.d("aaaaa" + myActivities);
                                        mRefreshLayout.setLoading(false);
                                        ;
                                        mAdapter.notifyDataSetChanged();

                                        textMore.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                    } else {
                                        textMore.setText("已加载全部数据");
                                        textMore.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                }
//                                Toast.makeText(getActivity(), "加载完成!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        gvAct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "进入活动详情页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ActDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("act", myActivities.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;

    }

    class ActGridViewAdapter extends MyBaseAdapter<MyActivity> {
        public ActGridViewAdapter(ArrayList<MyActivity> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            ActViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_list_act, null);
                holder = new ActViewHolder();
                holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img );
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tvPeopleNum = (TextView) convertView.findViewById(R.id.tv_people_num);
                convertView.setTag(holder);
            } else {
                holder = (ActViewHolder) convertView.getTag();
            }
            MyActivity act = datas.get(position);
            utils.display(holder.ivImg, GlobalContacts.SERVER_URL + act.getAct_img());
            holder.tvTitle.setText(act.getAct_title());
            holder.tvDate.setText("时间");
            holder.tvPeopleNum.setText(act.getAct_people_num() + "人已报名");
            return convertView;
        }
    }

    class ActViewHolder {
        ImageView ivImg;
        TextView tvTitle, tvDate, tvPeopleNum;
    }
}
