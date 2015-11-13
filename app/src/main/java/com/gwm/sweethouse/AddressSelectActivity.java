package com.gwm.sweethouse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.adapter.AddressSelectAdapter;
import com.gwm.sweethouse.bean.AdressBean;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.view.LoaidingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddressSelectActivity extends Activity {
    List<AdressBean> addresslist = new ArrayList<AdressBean>();
    Gson gson=new Gson();
    AddressSelectAdapter addressSelectAdapter;
    ListView listView;
    Dialog dialog=null;
    SharedPreferences preferences;
    int user_id;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    addressSelectAdapter=new AddressSelectAdapter(addresslist,AddressSelectActivity.this);
                    listView.setAdapter(addressSelectAdapter);
                    addressSelectAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    LoaidingDialog loaidingDialog=new LoaidingDialog();
                    dialog=loaidingDialog.createLoadingDialog(AddressSelectActivity.this, "数据加载中");
                    dialog.show();
                    break;
                case 3:
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        initView();
        initData();
    }

    private void initView() {
        listView= (ListView) findViewById(R.id.address_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<AdressBean> bundlelist = new ArrayList<AdressBean>();
                bundlelist.add(addresslist.get(position));
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("adressBean", (Serializable) bundlelist);
                intent.putExtras(bundle);
                intent.setClass(AddressSelectActivity.this, OrderConfirmationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RelativeLayout backtoorder= (RelativeLayout) findViewById(R.id.backtoorder);
        backtoorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressSelectActivity.this.finish();
            }
        });
        Button address_manage= (Button) findViewById(R.id.address_manage);
        address_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(AddressSelectActivity.this,MyAddressActivity.class);
                startActivity(intent);

            }
        });

    }

    private void initData() {
        preferences = AddressSelectActivity.this.getSharedPreferences("login", LoginActivity.MODE_PRIVATE);
        user_id=preferences.getInt("user_id", 0);
        Message message=new Message();
        message.what=2;
        handler.sendMessage(message);
        HttpUtils httpUtils=new HttpUtils();
        Date date=new Date();
        String time= String.valueOf(date.getTime());
        String url= GlobalContacts.ADDRESS_URL+"?user_id="+user_id+"&allselected=true"+"&time="+time;
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            Message message=new Message();
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                addresslist = gson.fromJson(result, new TypeToken<List<AdressBean>>() {
                }.getType());

                Message message1=new Message();

                message.what=1;
                handler.sendMessage(message);
                message1.what=3;
                handler.sendMessage(message1);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                message.what=3;
                handler.sendMessage(message);
            }
        });

    }

}
