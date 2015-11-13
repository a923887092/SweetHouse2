package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.adapter.OrderConfirmationAdapter;
import com.gwm.sweethouse.bean.AdressBean;
import com.gwm.sweethouse.bean.CartBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.CacheUtils;
import com.gwm.sweethouse.view.LoaidingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.proguard.P;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class OrderConfirmationActivity extends Activity {
    static int CONFIRM_PRESSED=0;
    List<CartBean> list;
    OrderConfirmationAdapter adapter;
    LayoutInflater inflater;
    List<AdressBean> addresslist = new ArrayList<AdressBean>();
    Gson gson=new Gson();
    TextView usernametv;
    TextView userphone;
    TextView user_address;
    TextView confirmtv;
    ImageView canclepay_iv;
    SharedPreferences preferences;
    int user_id;
    TextView restmoneytv;
    TextView phonenumbertv;
    TextView needpaytv;
    Dialog dialog=null;
    RelativeLayout paylayout;
    View locationView;
    static DecimalFormat df= new DecimalFormat("######0.0");
    float restmoney;
    Button ensuretopay;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    if (addresslist.size() != 0) {
                        updateLocation();
                    }
                    break;
                case 2:
                    LoaidingDialog loaidingDialog=new LoaidingDialog();
                    dialog=loaidingDialog.createLoadingDialog(OrderConfirmationActivity.this, "数据加载中");
                    dialog.show();
                    break;
                case 3:
                    dialog.dismiss();
                    break;
                case 4:
                    dialog.dismiss();
                    paylayout.setVisibility(View.VISIBLE);
                    locationView.setClickable(false);
                    confirmbackt.setClickable(false);
                    break;
                case 5:
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    Intent intent=new Intent();
                    intent.putExtra("orderstate",0);
                    intent.setClass(OrderConfirmationActivity.this, MyOrderActivity.class);
                    startActivity(intent);
                    OrderConfirmationActivity.this.finish();
                    break;
            }
        }
    };



    private RelativeLayout confirmbackt;

    private void updateLocation() {
        usernametv.setText("收货人：" + addresslist.get(0).getadd_name());
        userphone.setText(addresslist.get(0).getAdd_phone());
        user_address.setText("收货地址：" + addresslist.get(0).getAdd_address());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        initData();
        initViews();
    }

    private void initViews() {
        //将listview与adapter绑定
        final ListView orderconfirmList= (ListView) findViewById(R.id.confirmList);
        inflater=LayoutInflater.from(OrderConfirmationActivity.this);
        locationView = inflater.inflate(R.layout.part_location_orderconfirmation, null);
        orderconfirmList.addHeaderView(locationView);
        orderconfirmList.setAdapter(adapter);
        TextView confirmsumationtv= (TextView) findViewById(R.id.confirmsummation);
        confirmsumationtv.setText("¥" + allsum());

        paylayout= (RelativeLayout) findViewById(R.id.paylayout);

        //收货地址中的view creat
        usernametv= (TextView) locationView.findViewById(R.id.username);

        userphone= (TextView) locationView.findViewById(R.id.userphone);

        user_address= (TextView) locationView.findViewById(R.id.user_address);
        phonenumbertv= (TextView) findViewById(R.id.select_acount);
        restmoneytv= (TextView) findViewById(R.id.rest_money);
        needpaytv= (TextView) findViewById(R.id.need_pay);
        ImageView cancle_pay= (ImageView) findViewById(R.id.cancla_pay);
        cancle_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CONFIRM_PRESSED=0;
                paylayout.setVisibility(View.GONE);
                locationView.setClickable(true);
                confirmbackt.setClickable(true);
            }
        });
        confirmtv= (TextView) findViewById(R.id.confirm);
        confirmtv.setOnClickListener(new View.OnClickListener() {
            String phonenumber;

            @Override
            public void onClick(View v) {
                CONFIRM_PRESSED=1;
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
                initmoneydata();
            }



            private void initmoneydata() {
                HttpUtils httpUtils=new HttpUtils();

                String url=GlobalContacts.VISON_URL+"/Wallet?user_id="+user_id;
                httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                    Message message=new Message();
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        JSONTokener jsonTokener = new JSONTokener(result);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) jsonTokener.nextValue();
                            phonenumber = jsonObject.getString("phonenumber");

                            restmoney = Float.parseFloat(jsonObject.getString("restmoney"));
                            phonenumbertv.setText(phonenumber);
                            restmoneytv.setText(restmoney+"");
                            //if (restmoney<)
                            if (restmoney<allsum()){
                                restmoneytv.setText("(余额不足)"+restmoney);
                                restmoneytv.setTextColor(Color.parseColor("#d62d20"));
                                ensuretopay.setClickable(false);
                                Toast.makeText(OrderConfirmationActivity.this,"余额不足，无法支付",Toast.LENGTH_SHORT).show();
                            }else {
                                restmoneytv.setText(restmoney+"");
                            }
                            needpaytv.setText(allsum()+"元");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        message.what=4;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        message.what=4;
                        handler.sendMessage(message);
                    }
                });
            }
        });

        canclepay_iv= (ImageView) findViewById(R.id.canclepay);
        canclepay_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert();
            }

            public  void alert() {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirmationActivity.this);
                builder.setTitle("确认要放弃付款？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendnotpayorders(false);

                    }

                });

                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });
        ensuretopay= (Button) findViewById(R.id.ensuretopay);
        ensuretopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
                sendnotpayorders(true);
            }
        });
        //返回键
        confirmbackt = (RelativeLayout) findViewById(R.id.confirmbacktocart);
        confirmbackt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderConfirmationActivity.this.finish();
            }
        });
        locationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(OrderConfirmationActivity.this, AddressSelectActivity.class);
                startActivity(intent);
            }
        });

    }

    public void sendnotpayorders(boolean pay) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params=new RequestParams();
            /*params.addQueryStringParameter("ispay", String.valueOf(pay));
            params.addq*/
        String url=null;

/*            params.setBodyEntity(new StringEntity(gson.toJson(list),"utf-8"));
            params.setContentType("application/json");*/

        //params.addQueryStringParameter("ispay", String.valueOf(pay));
       // params.addQueryStringParameter("user_id", String.valueOf(user_id));


       // params.addQueryStringParameter("add_id", String.valueOf(addresslist.get(0).getAdd_id()));
       // params.addQueryStringParameter("order_state", String.valueOf(1));
        Date date=new Date();
        String time= String.valueOf(date.getTime());

        StringBuffer url_add=null;
        Log.e("addresslist",addresslist.size()+"");

        if (pay) {
            float aftermoney=restmoney-allsum();
            if (addresslist.size()>0) {
                int add_id= addresslist.get(0).getAdd_id();
                url_add = new StringBuffer(GlobalContacts.VISON_URL +"/Pay"+"?time="+time+"&user_id="+user_id+"&add_id="+add_id+
                        "&order_state="+2+"&aftermoney="+aftermoney+"&ispay="+pay);

            }
            for (int i=0;i<list.size();i++){
                int product_id=list.get(i).getProduct_id();
                float product_price=Float.parseFloat(df.format(list.get(i).getPrice() * list.get(i).getProduct_discount()));
                int Cart_id=list.get(i).getCart_id();
                int Goods_amount=list.get(i).getGoods_amount();
                url="&Cart_id"+i+"="+Cart_id+"&Goods_amount"+i+"="+Goods_amount+"&product_price"+i+"="+product_price
                        +"&product_id"+i+"="+product_id;
                url_add.append(url);
            }

            }else {
                if (addresslist.size()>0) {
                int add_id= addresslist.get(0).getAdd_id();
                url_add = new StringBuffer(GlobalContacts.VISON_URL +"/Pay"+"?time="+time+"&user_id="+user_id+"&add_id="+add_id+"&order_state="+1+"&ispay="+pay);

                }
                for (int i=0;i<list.size();i++){
                    int product_id=list.get(i).getProduct_id();
                    float product_price=Float.parseFloat(df.format(list.get(i).getPrice() * list.get(i).getProduct_discount()));
                    int Cart_id=list.get(i).getCart_id();
                    int Goods_amount=list.get(i).getGoods_amount();
                    url="&Cart_id"+i+"="+Cart_id+"&Goods_amount"+i+"="+Goods_amount+"&product_price"+i+"="+product_price
                            +"&product_id"+i+"="+product_id;
                    url_add.append(url);
                }
            }
        url=url_add.toString();
        Log.e("url",url);
            httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (responseInfo.statusCode == 200) {
                        CacheUtils.setCache(GlobalContacts.CART_URL + user_id, null,OrderConfirmationActivity.this);
                        Message message=new Message();
                        message.what=5;
                        handler.sendMessage(message);

                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });



    }

    private float allsum() {
        float summation=0;
        for (int i=0;i<list.size();i++){
            float temp=list.get(i).getGoods_amount()*Float.parseFloat(df.format(list.get(i).getPrice() * list.get(i).getProduct_discount()));
            summation+=temp;
        }
        return summation;
    }

    private void initData() {
        preferences = OrderConfirmationActivity.this.getSharedPreferences("login", LoginActivity.MODE_PRIVATE);
        user_id=preferences.getInt("user_id", 0);
        Message message=new Message();
        message.what=2;
        handler.sendMessage(message);
        Bundle bundle=getIntent().getExtras();
        list= (List<CartBean>) bundle.getSerializable("cart");
        adapter=new OrderConfirmationAdapter(list,OrderConfirmationActivity.this);
        getLocationData();
    }

    private void getLocationData() {
        final Message message = new Message();
        HttpUtils httpUtils=new HttpUtils();
        String url= GlobalContacts.ADDRESS_URL+"?user_id="+user_id;
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                addresslist = gson.fromJson(result, new TypeToken<List<AdressBean>>() {
                }.getType());
                Log.e("addresslist",addresslist.get(0).getadd_name());
                message.what = 3;
                handler.sendMessage(message);
                Message message1=new Message();
                message1.what = 1;
                handler.sendMessage(message1);


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(OrderConfirmationActivity.this, "数据加载失败",Toast.LENGTH_SHORT).show();
                message.what=3;
                handler.sendMessage(message);
                OrderConfirmationActivity.this.finish();
            }
        });





    }

    //刷新intent因为是以singletask来进行加载的 intent中所存的还是旧的数据
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (CONFIRM_PRESSED==1&&keyCode==KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirmationActivity.this);
            builder.setTitle("确认要放弃付款？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendnotpayorders(false);
                }

            });

            builder.setNegativeButton("取消", null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
         List<AdressBean> bundlelist = new ArrayList<AdressBean>();
        Bundle bundle = this.getIntent().getExtras();
        addresslist = (List<AdressBean>) bundle.getSerializable("adressBean");
        //防止由于返回键导致的空指针
        if (addresslist!=null){
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
        }
    }
}
