package com.gwm.sweethouse.fragment.saled;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.adapter.MyBaseAdapter;
import com.gwm.sweethouse.bean.Product;
import com.gwm.sweethouse.bean.Saled;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.manager.ThreadManager;
import com.gwm.sweethouse.protocol.GoodsProtocol;
import com.gwm.sweethouse.protocol.SaledProtocol;
import com.gwm.sweethouse.utils.FilesUtils;
import com.gwm.sweethouse.utils.LogUtils;
import com.gwm.sweethouse.utils.UiUtils;
import com.gwm.sweethouse.view.GridViewWithHeaderAndFooter;
import com.gwm.sweethouse.view.RefreshLayout;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
public class DiscountFragment extends Fragment {

    private View view;
    private View headerView;
    private RefreshLayout mRefreshLayout;
    private GridViewWithHeaderAndFooter gvGoods;
    private DiscountGridAdapter mAdapter;
    private ArrayList<Product> goods;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private String url = GlobalContacts.GOODS_DISCOUNT_URL;
    private String dirTime;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        httpUtils = new HttpUtils();
        bitmapUtils = new BitmapUtils(inflater.getContext());
        view = inflater.inflate(R.layout.fragment_discount, null);
        headerView = inflater.inflate(R.layout.fragment_discount_header, null);
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Gson gson = new Gson();
                goods = gson.fromJson(result, new TypeToken<ArrayList<Product>>() {
                }.getType());
                LogUtils.e(goods.toString());
                mAdapter = new DiscountGridAdapter(goods, inflater.getContext());
                gvGoods.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout = (RefreshLayout) view.findViewById(R.id.swipe_container);
        gvGoods = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_goods);
        gvGoods.addHeaderView(headerView);
        mRefreshLayout.setChildView(gvGoods);
        gvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);

        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    private void refreshData() {
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final String dirTime1 = System.currentTimeMillis() + "";
                GoodsProtocol protocol = new GoodsProtocol(url, dirTime1);
                final ArrayList<Product> goods_refresh = protocol.loadData();
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (goods_refresh != null) {
                            File dir = FilesUtils.getCacheDri();
                            if (dirTime != null) {
                                File file = new File(dir, dirTime);
                                if (file.isFile() && file.exists()) {
                                    file.delete();
                                }
                                dirTime = dirTime1;
                            }
                            goods.clear();
                            goods.addAll(goods_refresh);
                            LogUtils.d("aaaaa" + goods);
                            mAdapter.notifyDataSetChanged();
                        }
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "刷新成功!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    class DiscountGridAdapter extends MyBaseAdapter<Product>{


        public DiscountGridAdapter(ArrayList<Product> datas, Context context) {
            super(datas, context);
        }

        @Override
        protected View getViews(int position, View convertView, ViewGroup parent) {
            DiscountViewHolder holder;
            if (convertView == null){
                convertView = View.inflate(getActivity(), R.layout.item_discount_list, null);
                holder = new DiscountViewHolder();
                holder.ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                holder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
                convertView.setTag(holder);
            } else {
                holder = (DiscountViewHolder) convertView.getTag();
            }
            Product good = datas.get(position);
            bitmapUtils.display(holder.ivImg, GlobalContacts.SERVER_URL + good.getProduct_photo());
            holder.tvTitle.setText(good.getProduct_name() + " " + good.getProduct_desc());
            String format = new DecimalFormat(".0").format(good.getProduct_discount() * good.getProduct_price());
            holder.tvPrice.setText("￥" + format);
            holder.tvDiscount.setText((new Float(good.getProduct_discount() * 10)).intValue() + "折");
            return convertView;
        }
    }

    class DiscountViewHolder{
        private ImageView ivImg;
        TextView tvTitle, tvPrice, tvDiscount;
    }
}
