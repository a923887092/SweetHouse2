package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.adapter.AddressAdapter;
import com.gwm.sweethouse.bean.Address;
import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends Activity {
    ImageButton ibtn_return;
    ListView listview;
    Button btn_newAddress;
    TextView tv_noOrders;
    AddressAdapter adapter;
    List<Address> list ;
    ProgressBar progressBar;
    HttpUtils httpUtils;
    private int user_id;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initViews();
        intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        initData();
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
*/
    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_addressToMy);
        listview = (ListView) findViewById(R.id.lv_address);
        listview.setVerticalScrollBarEnabled(true);
        btn_newAddress = (Button) findViewById(R.id.btn_newAddr);
        tv_noOrders = (TextView) findViewById(R.id.tv_noOrders);
        tv_noOrders.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.pb_loadAddr);
        progressBar.setVisibility(View.VISIBLE);
        ibtn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    private void initData() {
        list = new ArrayList<Address>();
        adapter = new AddressAdapter(MyAddressActivity.this,list);
        listview.setAdapter(adapter);

        httpUtils = new HttpUtils();
        String url = GlobalContacts.VISON_URL+"/AddressServlet?method=getAllAddress&user_id="+user_id;
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        String result = responseInfo.result;
                        if (result == null){
                            tv_noOrders.setVisibility(View.VISIBLE);
                        }
                        Log.e("result", result);
                        Gson gson = new Gson();
                        Type typeOfT = new TypeToken<List<Address>>() {
                        }.getType();
                        List<Address> resultList = gson.fromJson(result, typeOfT);
                        Log.e("resultList", resultList.toString());
                        list.addAll(resultList);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("onFailure...", "onFailure...");
                        Toast.makeText(MyAddressActivity.this,"加载失败，请重试",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
