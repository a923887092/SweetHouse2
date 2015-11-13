package com.gwm.sweethouse.fragment.order;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.OrderDetailActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.OrderListAdapter;
import com.gwm.sweethouse.base.BaseApplication;
import com.gwm.sweethouse.bean.OrderListBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Order4Fragment extends Fragment {

    private GridViewWithHeaderAndFooter listView;
    private OrderListBean orderListBean;
    List<OrderListBean> list;
    private OrderListAdapter adapter;
    HttpUtils httpUtils;
    TextView textView;
    ProgressBar progressBar;
    // Button btn_reload;
    TextView textMore;
    ProgressBar mProgressBar;
    private RefreshLayout mRefreshLayout;
    View footerView;
    private int order_state = 3;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = inflater.getContext();
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order4, container, false);
        initViews(view);
        list = new ArrayList<OrderListBean>();
        adapter = new OrderListAdapter(context,list);
        listView.setAdapter(adapter);
        initData();
        return view;
    }



    private void initViews(View view) {
        footerView = View.inflate(context, R.layout.listview_footer, null);
        textMore = (TextView) footerView.findViewById(R.id.text_more);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);


        textView = (TextView) view.findViewById(R.id.tv_failed4);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        // btn_reload = (Button) view.findViewById(R.id.btn_reload);
        //  btn_reload.setVisibility(View.INVISIBLE);




        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container4);
        listView = (GridViewWithHeaderAndFooter) view.findViewById(R.id.lv_order4);

        listView.addFooterView(footerView);
        mRefreshLayout.setChildView(listView);

        listView.setOnScrollListener(new PauseOnScrollListener(
                BaseApplication.bitmapUtils,
                false,//正常滑动，true表示暂停加载，false继续加载
                true //飞速滑动，true表示暂停加载，false继续加载
        ));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //携带数据跳转到orderDetailActivity
                Toast.makeText(context, "详情页111带数据跳转", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, OrderDetailActivity.class);
                OrderListBean orderItem =list.get(i);
                Log.e("item", orderItem.toString());
                Log.e("item",orderItem.getOrder_price()+"");
                intent.putExtra("Product_name", orderItem.getProduct_name());
                intent.putExtra("Order_state",orderItem.getOrder_state());
                intent.putExtra("product_photo",orderItem.getProduct_Photo());
                intent.putExtra("product_desc",orderItem.getProduct_desc());
                intent.putExtra("Order_price",orderItem.getOrder_price());
                intent.putExtra("buy_count", orderItem.getBuy_count());
                intent.putExtra("order_id",orderItem.getOrder_id());
                intent.putExtra("pay_id",orderItem.getPay_id());
                intent.putExtra("add_id",orderItem.getAdd_id());
                startActivity(intent);
            }
        });

        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                initData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
//                        mAdapter.notifyDataSetChanged();
                        textMore.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Refresh Finished!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Load Finished!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
      /*  //当访问不到数据的时候，规避界面为空
        //使用emptyView，必须确保这个视图和listview在同一个视图层级中
        TextView textView = (TextView) view.findViewById(R.id.tv_emptyView);
        listView.setEmptyView(textView);*/

    }



    private void initData() {
        //通过user_id和order_state = 1，查询符合条件的订单
        //通过gson转化成对象，加入list

        httpUtils = new HttpUtils();

        httpUtils.configCurrentHttpCacheExpiry(5*1000);
        // 设置超时时间
        httpUtils.configTimeout(5*1000);
        httpUtils.configSoTimeout(5*1000);
        // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
        httpUtils.configCurrentHttpCacheExpiry(5*1000);

        String url = GlobalContacts.VISON_URL+"/OrderServlet?method=getOrdersByState&user_id=123458&order_state="+order_state;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url, new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        //  listView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        Type typeOfT = new TypeToken<List<OrderListBean>>(){}.getType();
                        List<OrderListBean> resultList= gson.fromJson(result, typeOfT);
                        list.addAll(resultList);

                        Log.e("gson转化后的list", list.toString());
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                        textView.setText("加载失败 %>_<%");
                        // btn_reload.setVisibility(View.VISIBLE);

                       /* btn_reload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              adapter.notifyDataSetChanged();
                            }
                        });*/
                    }
                });

    }
}
