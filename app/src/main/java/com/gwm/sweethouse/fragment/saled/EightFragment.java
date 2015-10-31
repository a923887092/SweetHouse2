package com.gwm.sweethouse.fragment.saled;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvTrackInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.DetailsActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Saled;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.CustomDigitalClock;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
public class EightFragment extends Fragment {

    private static final int STATE_START = 0;
    private static final int STATE_END = 1;
    private int state = STATE_END;

    private GridViewWithHeaderAndFooter gvSaled;
    private View headerView, view;
    private CustomDigitalClock remainTime;
    private TextView tvTitle;
    private RefreshLayout mRefreshLayout;
    private ArrayList<Saled> saleds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_item_eight, null);
        headerView = View.inflate(getActivity(), R.layout.fragment_header_sale_eight, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        saleds = (ArrayList<Saled>) bundle.getSerializable("saleds");
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        gvSaled = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_saled);
        gvSaled.addHeaderView(headerView);
        mRefreshLayout.setChildView(gvSaled);
        gvSaled.setAdapter(new AdapterSaleItem(saleds, getActivity()));
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("gwm", "onfresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
//                        mAdapter.notifyDataSetChanged();
//                        textMore.setVisibility(View.VISIBLE);
//                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Refresh Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        //上拉加载中
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
//                textMore.setVisibility(View.GONE);
//                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        getNewBottomData();
                        mRefreshLayout.setLoading(false);
//                        mAdapter.notifyDataSetChanged();
//                        textMore.setVisibility(View.VISIBLE);
//                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Load Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        remainTime = (CustomDigitalClock) headerView.findViewById(R.id.remainTime);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        gvSaled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("state", state);
                intent.putExtra("goodsId", saleds.get(position).getProduct_id());
                startActivity(intent);
            }
        });
        startTime();
    }

    class AdapterSaleItem extends MyBaseAdapter<Saled> {

        private Context mContext;
        private List<Saled> mItems;
        private LayoutInflater inflater;

        public AdapterSaleItem(ArrayList<Saled> datas, Context context) {
            super(datas, context);
            this.inflater = LayoutInflater.from(context);
        }


        class SaledViewHolder {
            ImageView ivImg;
            TextView tvTitle, tvPrice;
        }
        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            SaledViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_saled_goods, null);
                holder = new SaledViewHolder();
                holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                convertView.setTag(holder);
            } else {
                holder = (SaledViewHolder) convertView.getTag();
            }
            Saled saled = datas.get(position);
            utils.display(holder.ivImg, GlobalContacts.SERVER_URL + saled.getProduct_photo());
            holder.tvTitle.setText(saled.getProduct_name() + " " + saled.getProduct_desc());
            holder.tvPrice.setText("￥" + saled.getProduct_price() * saled.getProduct_discount());
            return convertView;
        }

    }


    private void startTime() {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, GlobalContacts.SERVER_URL +
                "/saledServlet?method=getTime", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                long time = Long.parseLong(result);
                long nowTime = System.currentTimeMillis();
                if (time > nowTime){
                    state = STATE_END;
                    remainTime.setEndTime(Long.parseLong(result));
                    remainTime.setClockListener(new CustomDigitalClock.ClockListener() { // register the clock's listener

                        @Override
                        public void timeEnd() {
                            // The clock time is ended.
                            Toast.makeText(getActivity(), "8888888888888", Toast.LENGTH_SHORT).show();
                            remainTime.setVisibility(View.INVISIBLE);
                            tvTitle.setText("特卖开始了，速度抢购吧");
                            state = STATE_START;
                            endTime(3600000);
                        }

                        @Override
                        public void remainFiveMinutes() {
                            // The clock time is remain five minutes.
                        }
                    });
                } else {
                    state = STATE_END;
                    remainTime.setVisibility(View.INVISIBLE);
                    long disTime = nowTime - time;
                    if (disTime > 3600000){
                        tvTitle.setText("特卖已经结束，明日再来吧！");
                        endTime2(14402000 - disTime);
                    } else {
                        tvTitle.setText("特卖开始了，速度抢购吧");
                        endTime(3600000 - disTime);
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void endTime(long l) {
        new CountDownTimer(l, 1000) {//总时间， 间隔时间

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                tvTitle.setText("特卖已经结束，明日再来吧！");
                endTime2(10800000);
            }
        }.start();
    }

    private void endTime2(long l) {
        new CountDownTimer(l, 1000) {//总时间， 间隔时间

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                tvTitle.setText("离特卖开始还有：");
                remainTime.setVisibility(View.VISIBLE);
                startTime();
            }
        }.start();
    }
}
