


package com.gwm.sweethouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.EvaluateOrderActivity;
import com.gwm.sweethouse.OrderLogisticActivity;
import com.gwm.sweethouse.PayOrderActivity;
import com.gwm.sweethouse.R;
import com.gwm.sweethouse.base.BaseApplication;
import com.gwm.sweethouse.bean.OrderListBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class OrderListAdapter extends BaseAdapter{
    List<OrderListBean> list=new ArrayList<OrderListBean>();
    Context context;
    LayoutInflater inflater;
    viewHolder vHolder;
    private Button button1, button2;

    public OrderListAdapter(Context context, List<OrderListBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view==null){
            view=inflater.inflate(R.layout.item_list_order,null);
            vHolder=new viewHolder();
            vHolder.tv_productName= (TextView) view.findViewById(R.id.tv_productName);
            vHolder.tv_productDesc= (TextView) view.findViewById(R.id.tv_productDesc);
            vHolder.tv_orderPrice= (TextView) view.findViewById(R.id.tv_productPrice);
            vHolder.buy_count= (TextView) view.findViewById(R.id.tv_buyCount);
            vHolder.order_State = (TextView) view.findViewById(R.id.tv_orderState);
            vHolder.iv_productImage = (ImageView) view.findViewById(R.id.iv_productImage);
            vHolder.button1 = (Button) view.findViewById(R.id.btn_1);
            vHolder.button2 = (Button) view.findViewById(R.id.btn_2);
            view.setTag(vHolder);
        }else {
            vHolder= (viewHolder) view.getTag();
        }

        int  order_state = list.get(position).getOrder_state();
        //取出url，settag添加标识
        String uri=list.get(position).getProduct_Photo();
        BaseApplication.bitmapUtils.display(vHolder.iv_productImage, GlobalContacts.VISON_URL + uri);
        //根据订单状态确定button的text及点击事件
        switch (order_state){
            case 1:
                vHolder.order_State.setText("待支付");
                vHolder.button1.setText("支付订单");
                vHolder.button2.setText("取消订单");
                vHolder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PayOrderActivity.class);
                        //需要携带数据，传递到PayOrderActivity
                        intent.putExtra("order_id",list.get(position).getOrder_id());
                        intent.putExtra("order_price",list.get(position).getOrder_price());
                        intent.putExtra("product_id",list.get(position).getProduct_id());
                        intent.putExtra("buy_count", list.get(position).getBuy_count());
                        intent.putExtra("product_name",list.get(position).getProduct_name());
                        context.startActivity(intent);

                    }
                });
                vHolder.button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        releaseOrder(position);
                    }
                });
                break;
            case 2:
                vHolder.order_State.setText("待发货");
                vHolder.button1.setText("取消订单");
                vHolder.button2.setText("提醒发货");
                vHolder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "退货退款", Toast.LENGTH_SHORT).show();
                        releaseOrder(position);
                        returnMoney(position);
                        addBill(position);
                    }
                });
                vHolder.button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "提醒发货成功", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:
                vHolder.order_State.setText("待收货");
                vHolder.button1.setText("确认收货");
                vHolder.button2.setText("查看物流");
                vHolder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT).show();
                        confirmRece(position);

                    }
                });
                vHolder.button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "查看物流", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, OrderLogisticActivity.class);
                        //intent.putExtra("order_id",list.get(position).getOrder_id());
                        context.startActivity(intent);
                    }
                });
                break;
            case 4:
                vHolder.order_State.setText("待评论");
                vHolder.button1.setText("评价商品");
                vHolder.button2.setVisibility(View.GONE);
                vHolder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, EvaluateOrderActivity.class);
                        intent.putExtra("uri",list.get(position).getProduct_Photo());
                        intent.putExtra("order_id", list.get(position).getOrder_id());
                        intent.putExtra("product_id",list.get(position).getProduct_id());
                        Log.e("evaluateIntent",list.get(position).getProduct_Photo()+list.get(position).getOrder_id());
                        context.startActivity(intent);
                    }
                });

                break;
            case 5:
                vHolder.order_State.setText("已完成");
                vHolder.button1.setText("删除订单");
                vHolder.button1.setTextColor(Color.DKGRAY);
                vHolder.button2.setVisibility(View.GONE);
                vHolder.button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        releaseOrder(position);
                    }
                });
                break;
        }
        vHolder.tv_productName.setText(list.get(position).getProduct_name());
        vHolder.tv_orderPrice.setText(list.get(position).getOrder_price()+"");
        vHolder.tv_productDesc.setText(list.get(position).getProduct_desc());
        vHolder.buy_count.setText("共" + list.get(position).getBuy_count() + "件商品(含运费￥0.00)");
        return view;
    }


    private class viewHolder{
        private TextView tv_productName,tv_orderPrice,tv_productDesc,buy_count,order_State;
        private ImageView iv_productImage;
        private Button button1, button2;
    }

    //取消订单，删除订单
    public void releaseOrder(int position){
        Log.e("position",position+"");
        Log.e(" list.get(position)", list.get(position).toString());
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/OrderServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","releaseOrder");
        params.addQueryStringParameter("user_id", LoginUtils.getUser_id(context)+"");
        params.addQueryStringParameter("order_id", list.get(position).getOrder_id()+"");

        httpUtils.send(HttpRequest.HttpMethod.GET, url,params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(context, "取消成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "取消失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //确认收货
    private void confirmRece(int position) {
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/OrderServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "confirmRece");
        //只需要一个order_id
        params.addQueryStringParameter("order_id", list.get(position).getOrder_id() + "");
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(context, "确认成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "确认收货失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //退款到个人钱包
    private void returnMoney(int position) {
        int user_id = LoginUtils.getUser_id(context);
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/MyWalletServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "returnMoney");
        //只需要order_id和user_id
        params.addQueryStringParameter("order_price", list.get(position).getOrder_price()+"");
        params.addQueryStringParameter("user_id", user_id+"");
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(context, "退款成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "退款失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //添加退款记录到我的账单
    private void addBill(int position) {
        int user_id = LoginUtils.getUser_id(context);
        HttpUtils httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/BillServlet";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method", "addBill");
        params.addQueryStringParameter("product_name", list.get(position).getProduct_name());
        params.addQueryStringParameter("order_price", list.get(position).getOrder_price()+"");
        params.addQueryStringParameter("bill_state",3+"");
        params.addQueryStringParameter("user_id", user_id+"");

        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(context, "账单添加成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "账单添加失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

