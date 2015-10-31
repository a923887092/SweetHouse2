package com.gwm.sweethouse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gwm.sweethouse.adapter.OrderConfirmationAdapter;
import com.gwm.sweethouse.bean.AdressBean;
import com.gwm.sweethouse.bean.CartBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;


public class OrderConfirmationActivity extends Activity {
    List<CartBean> list;
    OrderConfirmationAdapter adapter;
    LayoutInflater inflater;
    AdressBean adressBean=new AdressBean();
    TextView usernametv;
    TextView userphone;
    TextView user_address;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    updateLocation();
            }
        }
    };
    private RelativeLayout confirmbackt;

    private void updateLocation() {
        usernametv.setText("收货人："+adressBean.getadd_name());
        userphone.setText(adressBean.getAdd_phone());
        user_address.setText("收货地址：" + adressBean.getAdd_address());
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
        ListView orderconfirmList= (ListView) findViewById(R.id.confirmList);
        orderconfirmList.setAdapter(adapter);
        inflater=LayoutInflater.from(OrderConfirmationActivity.this);
        View locationView = inflater.inflate(R.layout.part_location_orderconfirmation,null);
        orderconfirmList.addHeaderView(locationView);
        TextView confirmsumationtv= (TextView) findViewById(R.id.confirmsummation);
        confirmsumationtv.setText("¥" + allsum());


        //收货地址中的view creat
        usernametv= (TextView) locationView.findViewById(R.id.username);

        userphone= (TextView) locationView.findViewById(R.id.userphone);

        user_address= (TextView) locationView.findViewById(R.id.user_address);

        confirmbackt = (RelativeLayout) findViewById(R.id.confirmbacktocart);
        confirmbackt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              OrderConfirmationActivity.this.finish();
            }
        });

    }

    private int allsum() {
        int summation=0;
        for (int i=0;i<list.size();i++){
            summation+=list.get(i).getGoods_amount()*list.get(i).getPrice();
        }
        return summation;
    }

    private void initData() {
        Bundle bundle=getIntent().getExtras();
        list= (List<CartBean>) bundle.getSerializable("cart");
        adapter=new OrderConfirmationAdapter(list,OrderConfirmationActivity.this);
        getLocationData();
    }

    private void getLocationData() {
        HttpUtils httpUtils=new HttpUtils();
        String url= GlobalContacts.ADDRESS_URL;
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                Gson gson=new Gson();
                adressBean=gson.fromJson(result,AdressBean.class);
                Message message=new Message();
                message.what=1;

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_confirmation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
